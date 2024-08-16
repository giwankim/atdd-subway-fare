package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import nextstep.subway.fare.domain.DistanceSurchargePolicy2;
import nextstep.subway.fare.domain.SurchargePolicy2;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.line.domain.LineSection2;
import nextstep.subway.path.domain.LineSectionEdge2;
import nextstep.subway.path.domain.Path2;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("거리 기반 요금 정책 단위 테스트")
class DistanceSurchargePolicyTest2 {
  @DisplayName("경로의 추가 요금을 계산한다.")
  @ParameterizedTest
  @MethodSource
  void calculateSurcharge(int distance, long expectedFare) {
    Station 교대역 = 교대역();
    Station 강남역 = 강남역();
    Line2 이호선 = 이호선2();
    List<LineSectionEdge2> edges =
        List.of(LineSectionEdge2.of(LineSection2.of(교대역, 강남역, distance, 5), 이호선));
    Path2 path = Path2.of(List.of(교대역, 강남역), edges);
    SurchargePolicy2 policy = new DistanceSurchargePolicy2(10, 50, 100, 5);
    assertThat(policy.calculateSurcharge(path)).isEqualTo(expectedFare);
  }

  private static Stream<Arguments> calculateSurcharge() {
    return Stream.of(
        Arguments.of(10, 0L),
        Arguments.of(11, 100L),
        Arguments.of(15, 100L),
        Arguments.of(16, 200L),
        Arguments.of(20, 200L),
        Arguments.of(21, 300L));
  }
}
