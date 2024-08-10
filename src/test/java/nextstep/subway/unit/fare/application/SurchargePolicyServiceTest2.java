package nextstep.subway.unit.fare.application;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import nextstep.subway.fare.application.SurchargePolicyService2;
import nextstep.subway.fare.application.SurchargeService2;
import nextstep.subway.fare.application.dto.SurchargeResponse2;
import nextstep.subway.fare.domain.Surcharge;
import nextstep.subway.fare.domain.SurchargePolicy2;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.path.domain.Path2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("요금 정책 서비스 단위 테스트")
@ExtendWith(MockitoExtension.class)
class SurchargePolicyServiceTest2 {
  @Mock private SurchargeService2 surchargeService;
  @InjectMocks private SurchargePolicyService2 surchargePolicyService;

  @DisplayName("요금 정책을 불러온다.")
  @Test
  void loadPolicy() {
    Line2 line = aLine2().id(1L).build();
    given(surchargeService.findAll())
        .willReturn(List.of(SurchargeResponse2.of(new Surcharge(1L, 900L), line)));

    SurchargePolicy2 policy = surchargePolicyService.loadPolicy();

    then(surchargeService).should(times(1)).findAll();

    Path2 path = Path2.of(List.of(교대역(), 강남역()), List.of(line), 3L, 10L);
    assertThat(policy.calculateSurcharge(path)).isEqualTo(900L);
  }
}
