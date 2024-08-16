package nextstep.subway.unit.path.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.line.domain.LineSections;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.path.domain.Path;
import nextstep.subway.path.domain.Paths;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("Paths 단위 테스트")
class PathsTest {
  @DisplayName("가장 빨리 도착하는 경로를 조회한다.")
  @Test
  void getEarliestArrivalTimePath() {
    Station 교대역 = 교대역();
    Station 강남역 = 강남역();
    Station 남부터미널역 = 남부터미널역();
    Station 양재역 = 양재역();
    LineSection 교대_강남_구간 = LineSection.of(교대역, 강남역, 10, 2);
    LineSection 강남_양재_구간 = LineSection.of(강남역, 양재역, 10, 3);
    LineSection 교대_남부터미널_구간 = LineSection.of(교대역, 남부터미널역, 2, 10);
    LineSection 남부터미널_양재_구간 = LineSection.of(남부터미널역, 양재역, 3, 10);
    Line 이호선 = aLine().lineSections(new LineSections(교대_강남_구간)).intervalTime(10).build();
    Line 신분당선 = aLine().lineSections(new LineSections(강남_양재_구간)).intervalTime(20).build();
    Line 삼호선 =
        aLine().lineSections(new LineSections(교대_남부터미널_구간, 남부터미널_양재_구간)).intervalTime(10).build();
    Paths paths =
        new Paths(
            List.of(
                Path.of(
                    List.of(교대역, 강남역, 양재역),
                    List.of(LineSectionEdge.of(교대_강남_구간, 이호선), LineSectionEdge.of(강남_양재_구간, 신분당선))),
                Path.of(
                    List.of(교대역, 남부터미널역, 양재역),
                    List.of(
                        LineSectionEdge.of(교대_남부터미널_구간, 삼호선),
                        LineSectionEdge.of(남부터미널_양재_구간, 삼호선)))));

    LocalDateTime departureTime = LocalDateTime.of(2024, 8, 13, 10, 0);

    Path path = paths.getEarliestArrivalPath(departureTime);

    assertThat(path.getArrivalTime(departureTime)).isEqualTo(LocalDateTime.of(2024, 8, 13, 10, 20));
    assertThat(path.getStations()).isEqualTo(List.of(교대역, 남부터미널역, 양재역));
    assertThat(path.getLines()).isEqualTo(List.of(삼호선, 삼호선));
  }

  @DisplayName("가장 빨리 도착하는 경로 조회 시 경로가 없을 경우 예외를 던진다.")
  @Test
  void getEarliestArrivalTimePathEmpty() {
    Paths paths = new Paths(Collections.emptyList());
    LocalDateTime departureTime = LocalDateTime.of(2024, 8, 13, 10, 0);
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> paths.getEarliestArrivalPath(departureTime));
  }
}
