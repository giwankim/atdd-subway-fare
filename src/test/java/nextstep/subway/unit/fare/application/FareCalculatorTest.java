package nextstep.subway.unit.fare.application;

import static nextstep.Fixtures.*;
import static nextstep.subway.fare.application.SurchargePolicyService.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import nextstep.member.domain.Member;
import nextstep.subway.fare.application.FareCalculator;
import nextstep.subway.fare.application.SurchargePolicyService;
import nextstep.subway.fare.domain.DistanceSurchargePolicy;
import nextstep.subway.fare.domain.LineSurchargePolicy;
import nextstep.subway.fare.domain.OverlappedSurchargePolicy;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("요금 계산기 단위 테스트")
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class FareCalculatorTest {
  @Mock private SurchargePolicyService surchargePolicyService;
  @InjectMocks private FareCalculator fareCalculator;

  private final Station 교대역 = 교대역();
  private final Station 강남역 = 강남역();
  private final Station 양재역 = 양재역();
  private final Line 이호선 = 이호선();
  private final Line 신분당선 = 신분당선();

  @DisplayName("요금 계산")
  @ParameterizedTest
  @MethodSource
  void calculateFare(int distance, int expectedFare) {
    Member adult = aMember().build();
    List<LineSectionEdge> edges =
        List.of(
            LineSectionEdge.of(LineSection.of(교대역, 강남역, 0, 0), 이호선),
            LineSectionEdge.of(LineSection.of(강남역, 양재역, distance, 10), 신분당선));
    Path path = Path.of(List.of(교대역, 강남역, 양재역), edges);
    given(surchargePolicyService.loadPolicy())
        .willReturn(
            new OverlappedSurchargePolicy(
                new DistanceSurchargePolicy(10L, 50L, OVERCHARGE_PER_5KM, DISTANCE_5KM),
                new DistanceSurchargePolicy(
                    50L, Long.MAX_VALUE, OVERCHARGE_PER_8KM, DISTANCE_8KM)));

    long fare = fareCalculator.calculateFare(path, adult);

    assertThat(fare).isEqualTo(expectedFare);
  }

  private static Stream<Arguments> calculateFare() {
    return Stream.of(
        Arguments.of(0, 1250),
        Arguments.of(9, 1250),
        Arguments.of(10, 1250),
        Arguments.of(11, 1350),
        Arguments.of(14, 1350),
        Arguments.of(15, 1350),
        Arguments.of(16, 1450),
        Arguments.of(20, 1450),
        Arguments.of(50, 2050),
        Arguments.of(51, 2150),
        Arguments.of(58, 2150),
        Arguments.of(59, 2250),
        Arguments.of(66, 2250),
        Arguments.of(67, 2350));
  }

  @DisplayName("요금 계산 - 청소년")
  @ParameterizedTest
  @MethodSource
  void calculateFareYouth(int distance, int expectedFare) {
    Member youth = aMember().age(13).build();
    List<LineSectionEdge> edges =
        List.of(
            LineSectionEdge.of(LineSection.of(교대역, 강남역, 0, 0), 이호선),
            LineSectionEdge.of(LineSection.of(강남역, 양재역, distance, 10), 신분당선));
    Path path = Path.of(List.of(교대역, 강남역, 양재역), edges);
    given(surchargePolicyService.loadPolicy())
        .willReturn(
            new OverlappedSurchargePolicy(
                new DistanceSurchargePolicy(10L, 50L, OVERCHARGE_PER_5KM, 5L),
                new DistanceSurchargePolicy(50L, Long.MAX_VALUE, OVERCHARGE_PER_8KM, 8L)));

    long fare = fareCalculator.calculateFare(path, youth);

    assertThat(fare).isEqualTo(expectedFare);
  }

  private static Stream<Arguments> calculateFareYouth() {
    return Stream.of(
        Arguments.of(0, 1070),
        Arguments.of(9, 1070),
        Arguments.of(10, 1070),
        Arguments.of(11, 1150),
        Arguments.of(14, 1150),
        Arguments.of(15, 1150),
        Arguments.of(16, 1230),
        Arguments.of(20, 1230),
        Arguments.of(50, 1710),
        Arguments.of(51, 1790),
        Arguments.of(58, 1790),
        Arguments.of(59, 1870),
        Arguments.of(66, 1870),
        Arguments.of(67, 1950));
  }

  @DisplayName("요금 계산 - 어린이")
  @ParameterizedTest
  @MethodSource
  void calculateFareChild(int distance, int expectedFare) {
    Member child = aMember().age(6).build();
    List<LineSectionEdge> edges =
        List.of(
            LineSectionEdge.of(LineSection.of(교대역, 강남역, 0, 0), 이호선),
            LineSectionEdge.of(LineSection.of(강남역, 양재역, distance, 10), 신분당선));
    Path path = Path.of(List.of(교대역, 강남역, 양재역), edges);
    given(surchargePolicyService.loadPolicy())
        .willReturn(
            new OverlappedSurchargePolicy(
                new DistanceSurchargePolicy(10L, 50L, OVERCHARGE_PER_5KM, 5L),
                new DistanceSurchargePolicy(50L, Long.MAX_VALUE, OVERCHARGE_PER_8KM, 8L)));

    long fare = fareCalculator.calculateFare(path, child);

    assertThat(fare).isEqualTo(expectedFare);
  }

  private static Stream<Arguments> calculateFareChild() {
    return Stream.of(
        Arguments.of(0, 800),
        Arguments.of(9, 800),
        Arguments.of(10, 800),
        Arguments.of(11, 850),
        Arguments.of(14, 850),
        Arguments.of(15, 850),
        Arguments.of(16, 900),
        Arguments.of(20, 900),
        Arguments.of(50, 1200),
        Arguments.of(51, 1250),
        Arguments.of(58, 1250),
        Arguments.of(59, 1300),
        Arguments.of(66, 1300),
        Arguments.of(67, 1350));
  }

  @DisplayName("요금 계산 - 노선별 추가 요금")
  @Test
  void calculateFareLineSurcharge() {
    Map<Long, Integer> lineIdToSurcharge =
        Map.of(
            1L, 900,
            2L, 1000);
    given(surchargePolicyService.loadPolicy())
        .willReturn(new LineSurchargePolicy(lineIdToSurcharge));

    Line line1 = aLine().id(1L).build();
    Line line2 = aLine().id(2L).build();
    List<LineSectionEdge> edges =
        List.of(
            LineSectionEdge.of(LineSection.of(교대역, 강남역, 5, 5), line1),
            LineSectionEdge.of(LineSection.of(강남역, 양재역, 5, 5), line2));
    Path path = Path.of(List.of(교대역, 강남역, 양재역), edges);

    Member adult = aMember().build();

    long fare = fareCalculator.calculateFare(path, adult);

    assertThat(fare).isEqualTo(2250L);
  }
}
