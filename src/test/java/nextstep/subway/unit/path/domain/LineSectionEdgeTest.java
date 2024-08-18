package nextstep.subway.unit.path.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.line.domain.LineSections;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("경로 그래프 간선 단위 테스트")
class LineSectionEdgeTest {
  private final Station 교대역 = 교대역();
  private final Station 강남역 = 강남역();
  private final Station 역삼역 = 역삼역();

  @DisplayName("가장 빠른 출발 시간을 구한다.")
  @Test
  void getArrivalTime() {
    LineSection section = LineSection.of(교대역, 강남역, 10, 3);
    Line line =
        aLine()
            .id(1L)
            .name("이호선")
            .lineSections(new LineSections(section))
            .surcharge(0)
            .startTime(LocalTime.of(5, 0))
            .endTime(LocalTime.of(23, 0))
            .intervalTime(10)
            .build();
    LineSectionEdge edge = LineSectionEdge.of(section, line);

    LocalDateTime arrivalTime = edge.getArrivalTime(LocalDateTime.of(2024, 8, 12, 10, 0));

    assertThat(arrivalTime).isEqualTo(LocalDateTime.of(2024, 8, 12, 10, 3));
  }

  @DisplayName("출발 시간을 구한다 - 노선 주기가 짧은 경우")
  @Test
  void getArrivalTimeFrequentInterval() {
    LineSection section1 = LineSection.of(교대역, 강남역, 10, 10);
    LineSection section2 = LineSection.of(강남역, 역삼역, 15, 15);
    Line line =
        aLine()
            .id(1L)
            .name("이호선")
            .lineSections(new LineSections(section1, section2))
            .surcharge(0)
            .startTime(LocalTime.of(5, 0))
            .endTime(LocalTime.of(23, 0))
            .intervalTime(3)
            .build();
    LineSectionEdge edge = LineSectionEdge.of(section2, line);

    LocalDateTime arrivalTime = edge.getArrivalTime(LocalDateTime.of(2024, 8, 12, 6, 0));

    assertThat(arrivalTime).isEqualTo(LocalDateTime.of(2024, 8, 12, 6, 16));
  }

  @DisplayName("막차 시간을 넘기면 다음날 출발 시간을 구한다.")
  @Test
  void getArrivalTimeNextDayAfterEndTime() {
    LineSection section = LineSection.of(교대역, 강남역, 10, 3);
    Line line =
        aLine()
            .id(1L)
            .name("이호선")
            .lineSections(new LineSections(section))
            .surcharge(0)
            .startTime(LocalTime.of(5, 0))
            .endTime(LocalTime.of(23, 0))
            .intervalTime(10)
            .build();
    LineSectionEdge edge = LineSectionEdge.of(section, line);

    LocalDateTime arrivalTime = edge.getArrivalTime(LocalDateTime.of(2024, 8, 12, 23, 30));

    assertThat(arrivalTime).isEqualTo(LocalDateTime.of(2024, 8, 13, 5, 3));
  }
}
