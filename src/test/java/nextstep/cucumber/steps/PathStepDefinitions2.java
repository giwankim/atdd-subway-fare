package nextstep.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import nextstep.cucumber.support.AcceptanceContext2;
import nextstep.subway.path.domain.PathType;
import nextstep.subway.station.application.dto.StationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class PathStepDefinitions2 {
  @Autowired private AcceptanceContext2 context;

  @When("{string}에서 {string}까지 최단 거리 경로를 조회하면2")
  public void 교대역_에서_강남역_까지_최단_거리_경로를_조회하면2(String source, String target) {
    doPathSearch(source, target, PathType.DISTANCE);
  }

  @When("{string}에서 {string}까지 최소 시간 경로를 조회하면2")
  public void 교대역_에서_양재역_까지_최소_시간_경로를_조회하면2(String source, String target) {
    doPathSearch(source, target, PathType.DURATION);
  }

  @When("{string}에서 {string}까지 {string}에 출발하는 가장 빠른 경로를 조회하면2")
  public void 역_에서_역_까지_시간_에_출발하는_가장_빠른_경로를_조회하면2(String source, String target, String startTime) {
    Long sourceId = ((StationResponse) context.store.get(source)).getId();
    Long targetId = ((StationResponse) context.store.get(target)).getId();
    String accessToken = (String) context.store.get("accessToken");
    context.response =
        RestAssured.given()
            .log()
            .all()
            .auth()
            .oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .queryParams(
                "source", sourceId,
                "target", targetId,
                "type", PathType.ARRIVAL_TIME.name(),
                "time", startTime)
            .when()
            .get("/new/paths")
            .then()
            .log()
            .all()
            .extract();
  }

  private void doPathSearch(String source, String target, PathType type) {
    Long sourceId = ((StationResponse) context.store.get(source)).getId();
    Long targetId = ((StationResponse) context.store.get(target)).getId();
    String accessToken = (String) context.store.get("accessToken");
    context.response =
        RestAssured.given()
            .log()
            .all()
            .auth()
            .oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .queryParams(
                "source", sourceId,
                "target", targetId,
                "type", type.name())
            .when()
            .get("/new/paths")
            .then()
            .log()
            .all()
            .extract();
  }

  @Then("{string} 경로가 조회된다2")
  public void 경로가_조회된다2(String pathString) {
    List<String> expectedNames = List.of(pathString.split(","));
    List<String> actualNames = context.response.jsonPath().getList("stations.name", String.class);
    assertThat(actualNames).containsExactlyElementsOf(expectedNames);
  }

  @Then("총 거리는 {int}km이며 총 소요 시간은 {int}분이다2")
  public void 총_거리는_x_km이며_총_소요_시간은_x_분이다2(int distance, int duration) {
    long actualDistance = context.response.jsonPath().getLong("distance");
    long actualDuration = context.response.jsonPath().getLong("duration");
    assertThat(actualDistance).isEqualTo(distance);
    assertThat(actualDuration).isEqualTo(duration);
  }

  @Then("도착 시간은 {string}이다2")
  public void 도착_시간은_x_이다2(String arrivalTimeString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    LocalDateTime arrivalTime =
        LocalDateTime.parse(context.response.jsonPath().get("arrivalTime"), formatter);
    LocalDateTime expectedTime = LocalDateTime.parse(arrivalTimeString, formatter);
    assertThat(arrivalTime).isEqualTo(expectedTime);
  }
}
