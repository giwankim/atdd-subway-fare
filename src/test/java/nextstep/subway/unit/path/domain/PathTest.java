package nextstep.subway.unit.path.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.line.domain.LineSections;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("경로 도메인 단위 테스트")
class PathTest {
  @DisplayName("경로의 도착 시간을 계산한다.")
  @Test
  void getArrivalTime() {
    Station 교대역 = 교대역();
    Station 강남역 = 강남역();
    Station 역삼역 = 역삼역();
    Station 선릉역 = 선릉역();
    LineSection 교대_강남_구간 = LineSection.of(교대역, 강남역, 10, 3);
    LineSection 강남_역삼_구간 = LineSection.of(강남역, 역삼역, 15, 4);
    LineSection 역삼_선릉_구간 = LineSection.of(역삼역, 선릉역, 10, 3);
    Line 이호선 =
        aLine()
            .id(1L)
            .name("이호선")
            .lineSections(new LineSections(교대_강남_구간, 강남_역삼_구간, 역삼_선릉_구간))
            .surcharge(0)
            .startTime(LocalTime.of(5, 0))
            .endTime(LocalTime.of(23, 0))
            .intervalTime(10)
            .build();
    List<Station> stations = List.of(교대역, 강남역, 역삼역, 선릉역);
    List<LineSectionEdge> edges =
        List.of(
            LineSectionEdge.of(교대_강남_구간, 이호선),
            LineSectionEdge.of(강남_역삼_구간, 이호선),
            LineSectionEdge.of(역삼_선릉_구간, 이호선));
    Path path = Path.of(stations, edges);

    LocalDateTime arrivalTime = path.getArrivalTime(LocalDateTime.of(2024, 8, 12, 10, 0));

    assertThat(arrivalTime).isEqualTo(LocalDateTime.of(2024, 8, 12, 10, 10));
  }
}
