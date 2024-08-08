package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.aMember;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import nextstep.member.domain.Member;
import nextstep.subway.fare.domain.AgeCondition;
import nextstep.subway.fare.domain.OverlappedDiscountPolicy;
import nextstep.subway.fare.domain.PercentDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("OverlappedOverFarePolicy 클래스 단위 테스트")
class OverlappedDiscountPolicyTest {
  @DisplayName("할인 계산")
  @ParameterizedTest
  @MethodSource
  void calculateDiscount(int age, long expectedDiscount) {
    Member member = aMember().age(age).build();
    OverlappedDiscountPolicy overlappedDiscountPolicy =
        new OverlappedDiscountPolicy(
            new PercentDiscountPolicy(0.2, 350, new AgeCondition(13, 19)),
            new PercentDiscountPolicy(0.5, 350, new AgeCondition(6, 13)));

    long discount = overlappedDiscountPolicy.calculateDiscount(1250, member);

    assertThat(discount).isEqualTo(expectedDiscount);
  }

  private static Stream<Arguments> calculateDiscount() {
    return Stream.of(
        Arguments.of(5, 0L),
        Arguments.of(6, 450L),
        Arguments.of(7, 450L),
        Arguments.of(12, 450L),
        Arguments.of(13, 180L),
        Arguments.of(14, 180L),
        Arguments.of(18, 180L),
        Arguments.of(19, 0L),
        Arguments.of(21, 0L));
  }
}
