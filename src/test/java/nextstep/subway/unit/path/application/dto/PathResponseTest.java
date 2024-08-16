package nextstep.subway.unit.path.application.dto;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.application.dto.StationResponse;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("경로 응답 단위 테스트")
class PathResponseTest {
  private final Station 교대역 = 교대역();
  private final Station 남부터미널역 = 남부터미널역();
  private final Station 양재역 = 양재역();
  private final Line 삼호선 = 삼호선();

  @DisplayName("경로로부터 응답을 생성한다.")
  @Test
  void of() {
    LineSectionEdge edge1 = LineSectionEdge.of(LineSection.of(교대역, 남부터미널역, 2, 10), 삼호선);
    LineSectionEdge edge2 = LineSectionEdge.of(LineSection.of(남부터미널역, 양재역, 3, 10), 삼호선);
    Path path = Path.of(List.of(교대역, 남부터미널역, 양재역), List.of(edge1, edge2));
    long fare = 1250;
    LocalDateTime arrivalTime = LocalDateTime.of(2024, 8, 12, 10, 0);

    PathResponse response = PathResponse.of(path, fare, arrivalTime);

    assertThat(response.getStations())
        .containsExactly(
            StationResponse.from(교대역), StationResponse.from(남부터미널역), StationResponse.from(양재역));
    assertThat(response.getDistance()).isEqualTo(5L);
    assertThat(response.getDuration()).isEqualTo(20L);
    assertThat(response.getFare()).isEqualTo(1250L);
    assertThat(response.getArrivalTime()).isEqualTo(arrivalTime);
  }
}
