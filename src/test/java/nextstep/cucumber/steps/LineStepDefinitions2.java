package nextstep.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import nextstep.cucumber.support.AcceptanceContext2;
import nextstep.subway.line.application.dto.*;
import nextstep.subway.station.application.dto.StationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class LineStepDefinitions2 {
  @Autowired private AcceptanceContext2 context;

  @Given("구간들을 등록하고2")
  public void 구간들을_등록하고2(List<Map<String, String>> rows) {
    rows.forEach(
        it -> {
          Long upStationId = ((StationResponse) context.store.get(it.get("upStation"))).getId();
          Long downStationId = ((StationResponse) context.store.get(it.get("downStation"))).getId();
          LineSectionRequest request =
              new LineSectionRequest(
                  upStationId,
                  downStationId,
                  Integer.parseInt(it.get("distance")),
                  Integer.parseInt(it.get("duration")));
          LineResponse2 line = (LineResponse2) context.store.get(it.get("line"));
          RestAssured.given()
              .log()
              .all()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .body(request)
              .when()
              .post("/new/lines/" + line.getId() + "/sections")
              .then()
              .log()
              .all()
              .extract();
        });
  }

  @Given("노선들을 생성하고2")
  public void 노선들을_생성하고2(List<Map<String, String>> rows) {
    rows.forEach(
        row -> {
          Long upStationId = ((StationResponse) context.store.get(row.get("upStation"))).getId();
          Long downStationId =
              ((StationResponse) context.store.get(row.get("downStation"))).getId();
          LineRequest2 request =
              LineRequest2.builder()
                  .name(row.get("name"))
                  .color(row.get("color"))
                  .upStationId(upStationId)
                  .downStationId(downStationId)
                  .distance(Integer.parseInt(row.get("distance")))
                  .duration(Integer.parseInt(row.get("duration")))
                  .surcharge(Integer.parseInt(row.get("surcharge")))
                  .startTime(LocalTime.parse(row.get("startTime")))
                  .endTime(LocalTime.parse(row.get("endTime")))
                  .intervalTime(Integer.parseInt(row.get("intervalTime")))
                  .build();
          var response =
              RestAssured.given()
                  .log()
                  .all()
                  .body(request)
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .when()
                  .post("/new/lines")
                  .then()
                  .log()
                  .all()
                  .extract();
          context.store.put(request.getName(), response.as(LineResponse2.class));
        });
  }

  @Then("지하철 노선 목록 조회 시 {string}을 찾을 수 있다2")
  public void 지하철_노선_목록_조회_시_생성한_노선을_찾을_수_있다2(String line) {
    var response =
        RestAssured.given().log().all().when().get("/new/lines").then().log().all().extract();
    List<LineResponse2> actualLines = response.jsonPath().getList(".", LineResponse2.class);
    LineResponse2 expectedLine = (LineResponse2) context.store.get(line);
    assertThat(actualLines).contains(expectedLine);
  }
}
