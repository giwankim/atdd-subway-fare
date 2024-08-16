package nextstep.cucumber.steps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import nextstep.auth.application.dto.TokenRequest;
import nextstep.cucumber.support.AcceptanceContext2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class AuthStepDefinitions2 {
  @Autowired private AcceptanceContext2 context;

  @Given("{string}과 {string}으로 로그인을 하고2")
  public void x_과_x_으로_로그인을_하고2(String email, String password) {
    TokenRequest request = new TokenRequest(email, password);
    var response =
        RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when()
            .post("/login/token")
            .then()
            .log()
            .all()
            .statusCode(HttpStatus.OK.value())
            .extract();
    String accessToken = response.jsonPath().getString("accessToken");
    context.store.put("accessToken", accessToken);
  }
}
