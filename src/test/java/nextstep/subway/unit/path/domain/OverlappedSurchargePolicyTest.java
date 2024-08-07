package nextstep.subway.unit.path.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import nextstep.subway.path.domain.*;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("OverlappedOverFarePolicy 클래스 단위 테스트")
class OverlappedSurchargePolicyTest {
  @DisplayName("할증 금액을 계산한다.")
  @ParameterizedTest
  @MethodSource
  void calculateSurcharge(int distance, int expectedFare) {
    List<Station> stations = Arrays.asList(교대역(), 강남역());
    Path path = Path.of(stations, List.of(이호선()), distance, 10);
    OverlappedSurchargePolicy policy =
        new OverlappedSurchargePolicy(
            new DistanceSurchargePolicy(10, 50, 100, 5),
            new DistanceSurchargePolicy(50, Long.MAX_VALUE, 100, 8));
    assertThat(policy.calculateSurcharge(path)).isEqualTo(expectedFare);
  }

  private static Stream<Arguments> calculateSurcharge() {
    return Stream.of(
        Arguments.of(0, 0),
        Arguments.of(9, 0),
        Arguments.of(10, 0),
        Arguments.of(11, 100),
        Arguments.of(14, 100),
        Arguments.of(15, 100),
        Arguments.of(16, 200),
        Arguments.of(20, 200),
        Arguments.of(50, 800),
        Arguments.of(51, 900),
        Arguments.of(58, 900),
        Arguments.of(59, 1000),
        Arguments.of(66, 1000),
        Arguments.of(67, 1100));
  }
}
