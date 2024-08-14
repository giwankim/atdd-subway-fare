package nextstep.subway.unit.path.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.line.domain.LineSection2;
import nextstep.subway.line.domain.LineSections2;
import nextstep.subway.path.domain.LineSectionEdge2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("경로 그래프 간선 단위 테스트")
class LineSectionEdgeTest2 {
  @DisplayName("가장 빠른 출발 시간을 구한다.")
  @Test
  void getArrivalTime() {
    LineSection2 section = LineSection2.of(교대역(), 강남역(), 10, 3);
    Line2 line =
        aLine2()
            .id(1L)
            .name("이호선")
            .lineSections(new LineSections2(section))
            .surcharge(0)
            .startTime(LocalTime.of(5, 0))
            .endTime(LocalTime.of(23, 0))
            .intervalTime(10)
            .build();
    LineSectionEdge2 edge = LineSectionEdge2.of(section, line);

    LocalDateTime arrivalTime = edge.getArrivalTime(LocalDateTime.of(2024, 8, 12, 10, 0));

    assertThat(arrivalTime).isEqualTo(LocalDateTime.of(2024, 8, 12, 10, 3));
  }

  @DisplayName("막차 시간을 넘기면 다음날 출발 시간을 구한다.")
  @Test
  void getArrivalTimeNextDayAfterEndTime() {
    LineSection2 section = LineSection2.of(교대역(), 강남역(), 10, 3);
    Line2 line =
        aLine2()
            .id(1L)
            .name("이호선")
            .lineSections(new LineSections2(section))
            .surcharge(0)
            .startTime(LocalTime.of(5, 0))
            .endTime(LocalTime.of(23, 0))
            .intervalTime(10)
            .build();
    LineSectionEdge2 edge = LineSectionEdge2.of(section, line);

    LocalDateTime arrivalTime = edge.getArrivalTime(LocalDateTime.of(2024, 8, 12, 23, 30));

    assertThat(arrivalTime).isEqualTo(LocalDateTime.of(2024, 8, 13, 5, 3));
  }
}
