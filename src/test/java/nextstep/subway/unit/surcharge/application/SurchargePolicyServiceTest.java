package nextstep.subway.unit.surcharge.application;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.Path;
import nextstep.subway.path.domain.SurchargePolicy;
import nextstep.subway.surcharge.application.SurchargePolicyService;
import nextstep.subway.surcharge.application.SurchargeService;
import nextstep.subway.surcharge.application.dto.SurchargeResponse;
import nextstep.subway.surcharge.domain.Surcharge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("요금 정책 서비스 단위 테스트")
@ExtendWith(MockitoExtension.class)
class SurchargePolicyServiceTest {
  @Mock private SurchargeService surchargeService;
  @InjectMocks private SurchargePolicyService surchargePolicyService;

  @DisplayName("요금 정책을 불러온다.")
  @Test
  void loadPolicy() {
    Line line = aLine().id(1L).build();
    given(surchargeService.findAll())
        .willReturn(List.of(SurchargeResponse.of(new Surcharge(1L, 900L), line)));

    SurchargePolicy policy = surchargePolicyService.loadPolicy();

    then(surchargeService).should(times(1)).findAll();

    Path path = Path.of(List.of(교대역(), 강남역()), List.of(line), 3L, 10L);
    assertThat(policy.calculateSurcharge(path)).isEqualTo(900L);
  }
}
