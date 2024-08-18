package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import nextstep.subway.fare.domain.DistanceCondition;
import nextstep.subway.fare.domain.SurchargeCondition;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("거리 기반 할인 조건 단위 테스트")
class DistanceConditionTest {
  @DisplayName("조건을 만족하는지 확인")
  @ParameterizedTest
  @MethodSource
  void isSatisfiedBy(List<Station> stations, List<LineSectionEdge> edges, boolean expected) {
    Path path = Path.of(stations, edges);
    SurchargeCondition condition = new DistanceCondition(10);
    assertThat(condition.isSatisfiedBy(path)).isEqualTo(expected);
  }

  private static Stream<Arguments> isSatisfiedBy() {
    Station 교대역 = 교대역();
    Station 강남역 = 강남역();
    Station 양재역 = 양재역();
    Line 이호선 = 이호선();
    Line 신분당선 = 신분당선();

    return Stream.of(
        Arguments.of(
            List.of(교대역, 강남역, 양재역),
            List.of(
                LineSectionEdge.of(LineSection.of(교대역, 강남역, 5, 5), 이호선),
                LineSectionEdge.of(LineSection.of(강남역, 양재역, 4, 5), 신분당선)),
            false),
        Arguments.of(
            List.of(교대역, 강남역, 양재역),
            List.of(
                LineSectionEdge.of(LineSection.of(교대역, 강남역, 5, 5), 이호선),
                LineSectionEdge.of(LineSection.of(강남역, 양재역, 5, 5), 신분당선)),
            true),
        Arguments.of(
            List.of(교대역, 강남역, 양재역),
            List.of(
                LineSectionEdge.of(LineSection.of(교대역, 강남역, 5, 5), 이호선),
                LineSectionEdge.of(LineSection.of(강남역, 양재역, 6, 5), 신분당선)),
            true));
  }
}
