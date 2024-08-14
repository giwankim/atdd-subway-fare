package nextstep.subway.acceptance.line.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nextstep.subway.line.application.dto.*;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.line.domain.LineSection2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class LineAcceptanceSteps2 {
  private LineAcceptanceSteps2() {}

  public static ExtractableResponse<Response> 지하철_노선_생성_요청2(Line2 line) {
    LineSection2 section = line.getLineSections().getFirst();

    Map<String, String> params = new HashMap<>();
    params.put("name", line.getName());
    params.put("color", line.getColor());
    params.put("surcharge", String.valueOf(line.getSurcharge()));
    params.put("upStationId", section.getUpStation().getId().toString());
    params.put("downStationId", section.getDownStation().getId().toString());
    params.put("distance", String.valueOf(section.getDistance()));
    params.put("duration", String.valueOf(section.getDuration()));
    params.put("startTime", line.getStartTime().toString());
    params.put("endTime", line.getEndTime().toString());
    params.put("intervalTime", String.valueOf(line.getIntervalTime()));

    return RestAssured.given()
        .log()
        .all()
        .body(params)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post("/new/lines")
        .then()
        .log()
        .all()
        .extract();
  }

  public static void 지하철_노선_생성됨2(ExtractableResponse<Response> response) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank();
  }

  public static ExtractableResponse<Response> 지하철_노선_목록_조회_요청2() {
    return RestAssured.given().log().all().when().get("/new/lines").then().log().all().extract();
  }

  public static void 지하철_노선_목록에_포함됨2(
      ExtractableResponse<Response> response, List<ExtractableResponse<Response>> createResponses) {
    List<LineResponse2> actualLines = response.jsonPath().getList(".", LineResponse2.class);
    List<LineResponse2> expectedLines =
        createResponses.stream().map(it -> it.as(LineResponse2.class)).collect(Collectors.toList());
    assertThat(actualLines).containsExactlyInAnyOrderElementsOf(expectedLines);
  }

  public static ExtractableResponse<Response> 지하철_노선_조회_요청2(String uri) {
    return RestAssured.given().log().all().when().get(uri).then().log().all().extract();
  }

  public static void 지하철_노선_조회됨2(ExtractableResponse<Response> response, Line2 line) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.as(LineResponse2.class)).isEqualTo(LineResponse2.from(line));
  }

  public static ExtractableResponse<Response> 지하철_노선_수정_요청2(String uri, String name, String color) {
    return RestAssured.given()
        .log()
        .all()
        .body(new UpdateLineRequest(name, color))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .put(uri)
        .then()
        .log()
        .all()
        .extract();
  }

  public static void 지하철_노선_수정됨2(String uri, String newName, String newColor) {
    LineResponse2 updatedLine = 지하철_노선_조회_요청2(uri).as(LineResponse2.class);
    assertThat(updatedLine.getName()).isEqualTo(newName);
    assertThat(updatedLine.getColor()).isEqualTo(newColor);
  }

  public static ExtractableResponse<Response> 지하철_삭제_요청2(String uri) {
    return RestAssured.given().log().all().when().delete(uri).then().log().all().extract();
  }

  public static void 지하철_노선_삭제됨2(String uri, ExtractableResponse<Response> response) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    var getResponse = RestAssured.given().log().all().when().get(uri).then().log().all().extract();
    assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }
}
