package nextstep.subway.unit.path.domain;

import static nextstep.Fixtures.강남역;
import static nextstep.Fixtures.교대역;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import nextstep.subway.path.domain.DistanceCondition;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("거리 기반 할인 조건 단위 테스트")
class DistanceConditionTest {
  @DisplayName("조건을 만족하는지 확인")
  @Test
  void isSatisfiedBy() {
    List<Station> stations = Arrays.asList(교대역(), 강남역());
    DistanceCondition condition = new DistanceCondition(10);
    assertThat(condition.isSatisfiedBy(Path.of(stations, 9, 10))).isFalse();
    assertThat(condition.isSatisfiedBy(Path.of(stations, 10, 10))).isTrue();
    assertThat(condition.isSatisfiedBy(Path.of(stations, 11, 10))).isTrue();
  }
}
