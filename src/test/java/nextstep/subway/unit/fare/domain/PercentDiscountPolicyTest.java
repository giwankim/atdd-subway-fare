package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.aMember;
import static org.assertj.core.api.Assertions.assertThat;

import nextstep.member.domain.Member;
import nextstep.subway.fare.domain.PercentDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("퍼센트 할인 정책 단위 테스트")
class PercentDiscountPolicyTest {
  @DisplayName("할인 금액을 계산한다")
  @Test
  void calculateDiscount() {
    Member member = aMember().build();
    PercentDiscountPolicy policy = new PercentDiscountPolicy(0.1, 100L, m -> true);
    assertThat(policy.calculateDiscount(1000L, member)).isEqualTo(90L);
    assertThat(policy.calculateDiscount(100L, member)).isZero();
    assertThat(policy.calculateDiscount(150L, member)).isEqualTo(5L);
  }
}
