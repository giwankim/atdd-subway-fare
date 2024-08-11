package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.aMember;
import static org.assertj.core.api.Assertions.assertThat;

import nextstep.member.domain.Member;
import nextstep.subway.fare.domain.AgeDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("연령별 할인 정책 단위 테스트")
class AgeDiscountPolicyTest {
  @DisplayName("어른은 할인이 없다.")
  @Test
  void calculateDiscountAdult() {
    Member adult = aMember().build();
    AgeDiscountPolicy policy = AgeDiscountPolicy.from(adult);
    long discount = policy.calculateDiscount(1350L);
    assertThat(discount).isZero();
  }

  @DisplayName("청소년은 350원을 공제한 후 20% 할인한다.")
  @Test
  void calculateDiscountYouth() {
    Member youth = aMember().age(13).build();
    AgeDiscountPolicy policy = AgeDiscountPolicy.from(youth);
    long discount = policy.calculateDiscount(1350L);
    assertThat(discount).isEqualTo(200L);
  }

  @DisplayName("어린이는 350원을 공제한 후 50% 할인한다.")
  @Test
  void calculateDiscountChild() {
    Member youth = aMember().age(6).build();
    AgeDiscountPolicy policy = AgeDiscountPolicy.from(youth);
    long discount = policy.calculateDiscount(1350L);
    assertThat(discount).isEqualTo(500L);
  }
}
