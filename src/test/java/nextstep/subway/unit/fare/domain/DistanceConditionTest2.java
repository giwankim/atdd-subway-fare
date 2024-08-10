package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import nextstep.subway.fare.domain.DistanceCondition2;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.path.domain.Path2;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("거리 기반 할인 조건 단위 테스트")
class DistanceConditionTest2 {
  @DisplayName("조건을 만족하는지 확인")
  @Test
  void isSatisfiedBy() {
    List<Station> stations = Arrays.asList(교대역(), 강남역());
    List<Line2> lines = List.of(이호선2(), 신분당선2());
    DistanceCondition2 condition = new DistanceCondition2(10);
    assertThat(condition.isSatisfiedBy(Path2.of(stations, lines, 9, 10))).isFalse();
    assertThat(condition.isSatisfiedBy(Path2.of(stations, lines, 10, 10))).isTrue();
    assertThat(condition.isSatisfiedBy(Path2.of(stations, lines, 11, 10))).isTrue();
  }
}
