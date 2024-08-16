package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import nextstep.subway.fare.domain.LineSurchargePolicy2;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.line.domain.LineSection2;
import nextstep.subway.path.domain.LineSectionEdge2;
import nextstep.subway.path.domain.Path2;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("노선별 요금 추가 정책 서비스 단위 테스트")
class LineSurchargePolicyTest2 {
  @DisplayName("추가 요금을 계산한다.")
  @Test
  void getSurchargeAmount() {
    Station 교대역 = 교대역();
    Station 강남역 = 강남역();
    Line2 이호선 = aLine2().id(1L).build();
    List<LineSectionEdge2> edges =
        List.of(LineSectionEdge2.of(LineSection2.of(교대역, 강남역, 10, 10), 이호선));
    Path2 path = Path2.of(List.of(교대역, 강남역), edges);
    LineSurchargePolicy2 policy = new LineSurchargePolicy2(Map.of(1L, 900));

    long surcharge = policy.calculateSurcharge(path);

    assertThat(surcharge).isEqualTo(900L);
  }

  @DisplayName("추가요금이 있는 노선을 환승하여 이용 할 경우 가장 높은 금액이 계산된다.")
  @Test
  void getSurchargeAmountMultipleLines() {
    Station 교대역 = 교대역();
    Station 강남역 = 강남역();
    Station 양재역 = 양재역();
    Station 판교역 = 판교역();
    Line2 line1 = aLine2().id(1L).build();
    Line2 line2 = aLine2().id(2L).build();
    Line2 line3 = aLine2().id(3L).build();
    LineSurchargePolicy2 policy =
        new LineSurchargePolicy2(
            Map.of(
                1L, 900,
                2L, 1000,
                3L, 1100));
    List<Station> stations = List.of(교대역, 강남역, 양재역);
    List<LineSectionEdge2> edges =
        List.of(
            LineSectionEdge2.of(LineSection2.of(교대역, 강남역, 2, 2), line1),
            LineSectionEdge2.of(LineSection2.of(강남역, 양재역, 3, 3), line2),
            LineSectionEdge2.of(LineSection2.of(양재역, 판교역, 5, 5), line3));
    Path2 path = Path2.of(stations, edges);

    long surcharge = policy.calculateSurcharge(path);

    assertThat(surcharge).isEqualTo(1100L);
  }
}
