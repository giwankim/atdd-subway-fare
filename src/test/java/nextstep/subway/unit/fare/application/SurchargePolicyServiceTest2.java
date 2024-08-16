package nextstep.subway.unit.fare.application;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import nextstep.subway.fare.application.SurchargePolicyService2;
import nextstep.subway.fare.domain.SurchargePolicy2;
import nextstep.subway.line.application.LineService;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.path.domain.LineSectionEdge;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("요금 정책 서비스 단위 테스트")
@ExtendWith(MockitoExtension.class)
class SurchargePolicyServiceTest2 {
  @Mock private LineService lineService;
  @InjectMocks private SurchargePolicyService2 surchargePolicyService;

  @DisplayName("요금 정책을 불러온다.")
  @Test
  void loadPolicy() {
    Line line = aLine().id(1L).surcharge(900).build();
    given(lineService.findAllLines()).willReturn(List.of(line));

    SurchargePolicy2 policy = surchargePolicyService.loadPolicy();

    then(lineService).should(times(1)).findAllLines();

    List<LineSectionEdge> edges =
        List.of(LineSectionEdge.of(LineSection.of(교대역(), 강남역(), 3, 10), line));
    List<Station> stations = List.of(교대역(), 강남역());
    assertThat(policy.calculateSurcharge(Path.of(stations, edges))).isEqualTo(900L);
  }
}
