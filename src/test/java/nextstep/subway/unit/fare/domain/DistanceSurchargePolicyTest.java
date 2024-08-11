package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import nextstep.subway.fare.domain.DistanceSurchargePolicy;
import nextstep.subway.fare.domain.SurchargePolicy;
import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("거리 기반 요금 정책 단위 테스트")
class DistanceSurchargePolicyTest {
  @DisplayName("경로의 추가 요금을 계산한다.")
  @ParameterizedTest
  @MethodSource
  void calculateSurcharge(long distance, long expectedFare) {
    List<Station> stations = Arrays.asList(교대역(), 강남역());
    List<Line> lines = List.of(이호선2());
    SurchargePolicy policy = new DistanceSurchargePolicy(10, 50, 100, 5);
    assertThat(policy.calculateSurcharge(Path.of(stations, lines, distance, 10)))
        .isEqualTo(expectedFare);
  }

  private static Stream<Arguments> calculateSurcharge() {
    return Stream.of(
        Arguments.of(10L, 0L),
        Arguments.of(11L, 100L),
        Arguments.of(15L, 100L),
        Arguments.of(16L, 200L),
        Arguments.of(20L, 200L),
        Arguments.of(21L, 300L));
  }
}
