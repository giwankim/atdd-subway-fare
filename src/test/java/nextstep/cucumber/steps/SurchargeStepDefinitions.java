package nextstep.cucumber.steps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Map;
import nextstep.cucumber.support.AcceptanceContext;
import nextstep.subway.fare.application.dto.SurchargeRequest;
import nextstep.subway.line.application.dto.LineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class SurchargeStepDefinitions {
  @Autowired private AcceptanceContext context;

  @Given("노선별 추가 요금을 등록하고")
  public void 노선별_추가_요금을_등록하고(List<Map<String, String>> rows) {
    rows.forEach(
        it -> {
          Long lineId = ((LineResponse) context.store.get(it.get("line"))).getId();
          Long surcharge = Long.parseLong(it.get("surcharge"));
          SurchargeRequest request = new SurchargeRequest(lineId, surcharge);
          RestAssured.given()
              .log()
              .all()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .body(request)
              .when()
              .post("/surcharges")
              .then()
              .log()
              .all()
              .extract();
        });
  }
}
