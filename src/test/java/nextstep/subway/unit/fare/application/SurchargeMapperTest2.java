package nextstep.subway.unit.fare.application;

import static nextstep.Fixtures.신분당선2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import nextstep.subway.fare.application.SurchargeMapper2;
import nextstep.subway.fare.application.dto.SurchargeResponse2;
import nextstep.subway.fare.domain.Surcharge;
import nextstep.subway.line.application.LineService2;
import nextstep.subway.line.domain.Line2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("추가 요금 매퍼 단위 테스트")
@ExtendWith(MockitoExtension.class)
class SurchargeMapperTest2 {
  @Mock private LineService2 lineService;
  @InjectMocks private SurchargeMapper2 surchargeMapper;

  @DisplayName("응답 객체로 변환한다.")
  @Test
  void mapToResponse() {
    long surchargeId = 1L;
    Line2 line = 신분당선2();
    given(lineService.findLineById(line.getId())).willReturn(line);
    Surcharge surcharge = new Surcharge(surchargeId, line.getId(), line.getSurcharge());

    SurchargeResponse2 surchargeResponse = surchargeMapper.mapToResponse(surcharge);

    assertThat(surchargeResponse.getId()).isEqualTo(surchargeId);
    assertThat(surchargeResponse.getLine().getId()).isEqualTo(line.getId());
    assertThat(surchargeResponse.getSurcharge()).isEqualTo(line.getSurcharge());
  }
}
