package nextstep.subway.unit.path.application.dto;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.domain.Path2;
import nextstep.subway.station.application.dto.StationResponse;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("경로 응답 단위 테스트")
class PathResponseTest {
  @DisplayName("경로로부터 응답을 생성한다.")
  @Test
  void of() {
    Station 교대역 = 교대역();
    Station 남부터미널역 = 남부터미널역();
    Station 양재역 = 양재역();
    Path2 path = Path2.of(Arrays.asList(교대역, 남부터미널역, 양재역), List.of(삼호선(), 이호선()), 5, 10);
    long fare = 1250;

    PathResponse response = PathResponse.of(path, fare);

    assertThat(response.getStations())
        .containsExactly(
            StationResponse.from(교대역), StationResponse.from(남부터미널역), StationResponse.from(양재역));
    assertThat(response.getDistance()).isEqualTo(5);
    assertThat(response.getDuration()).isEqualTo(10);
    assertThat(response.getFare()).isEqualTo(1250);
  }
}
