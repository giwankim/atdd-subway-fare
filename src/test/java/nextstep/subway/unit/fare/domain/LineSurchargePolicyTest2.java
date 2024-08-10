package nextstep.subway.unit.fare.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import nextstep.subway.fare.domain.LineSurchargePolicy2;
import nextstep.subway.line.domain.Line2;
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
    LineSurchargePolicy2 policy = new LineSurchargePolicy2(Map.of(1L, 900L));
    Path2 path = Path2.of(List.of(교대역(), 강남역()), List.of(aLine2().id(1L).build()), 10L, 10L);

    long surcharge = policy.calculateSurcharge(path);

    assertThat(surcharge).isEqualTo(900L);
  }

  @DisplayName("추가요금이 있는 노선을 환승하여 이용 할 경우 가장 높은 금액이 계산된다.")
  @Test
  void getSurchargeAmountMultipleLines() {
    LineSurchargePolicy2 policy =
        new LineSurchargePolicy2(
            Map.of(
                1L, 900L,
                2L, 1000L,
                3L, 1100L));
    List<Station> stations = List.of(교대역(), 강남역(), 양재역());
    List<Line2> lines =
        List.of(aLine2().id(1L).build(), aLine2().id(2L).build(), aLine2().id(3L).build());
    Path2 path = Path2.of(stations, lines, 10L, 10L);

    long surcharge = policy.calculateSurcharge(path);

    assertThat(surcharge).isEqualTo(1100L);
  }
}
