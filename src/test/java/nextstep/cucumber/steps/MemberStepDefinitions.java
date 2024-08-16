package nextstep.cucumber.steps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import java.util.List;
import nextstep.member.application.dto.MemberRequest;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class MemberStepDefinitions {

  @Given("사용자들을 생셩하고")
  public void 사용자들을_생셩하고(List<MemberRequest> memberRequests) {
    memberRequests.forEach(
        request ->
            RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/members")
                .then()
                .log()
                .all()
                .extract());
  }
}
