package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.aMember;
import static org.assertj.core.api.Assertions.assertThat;

import nextstep.subway.fare.domain.AgeCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("나이 조건 단위 테스트")
class AgeConditionTest {
  @DisplayName("조건에 부합하는지 확인")
  @Test
  void isSatisfiedBy() {
    AgeCondition condition = new AgeCondition(13, 19);
    assertThat(condition.isSatisfiedBy(aMember().age(12).build())).isFalse();
    assertThat(condition.isSatisfiedBy(aMember().age(13).build())).isTrue();
    assertThat(condition.isSatisfiedBy(aMember().age(18).build())).isTrue();
    assertThat(condition.isSatisfiedBy(aMember().age(19).build())).isFalse();
  }
}
