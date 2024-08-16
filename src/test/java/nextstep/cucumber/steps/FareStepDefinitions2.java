package nextstep.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import nextstep.cucumber.support.AcceptanceContext2;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
public class FareStepDefinitions2 {
  @Autowired private AcceptanceContext2 context;

  @Then("이용 요금은 {int}원이다2")
  public void 이용_요금은_x_원이다2(int fare) {
    long actualFare = context.response.jsonPath().getLong("fare");
    assertThat(actualFare).isEqualTo(fare);
  }
}
