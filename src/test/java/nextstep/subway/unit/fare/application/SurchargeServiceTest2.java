package nextstep.subway.unit.fare.application;

import static nextstep.Fixtures.신분당선;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import nextstep.subway.fare.application.SurchargeMapper2;
import nextstep.subway.fare.application.SurchargeService2;
import nextstep.subway.fare.application.dto.SurchargeRequest;
import nextstep.subway.fare.domain.Surcharge;
import nextstep.subway.fare.domain.SurchargeRepository;
import nextstep.subway.line.domain.Line;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("요금 서비스 단위 테스트")
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class SurchargeServiceTest2 {
  @Mock private SurchargeRepository surchargeRepository;
  @Mock private SurchargeMapper2 surchargeMapper;
  @InjectMocks private SurchargeService2 surchargeService;

  @DisplayName("요금 정보를 저장한다.")
  @Test
  void save() {
    long amount = 900L;
    Line line = 신분당선();
    SurchargeRequest request = new SurchargeRequest(line.getId(), amount);
    given(surchargeRepository.save(any(Surcharge.class)))
        .willReturn(new Surcharge(1L, line.getId(), amount));

    surchargeService.save(request);

    ArgumentCaptor<Surcharge> surchargeCaptor = ArgumentCaptor.forClass(Surcharge.class);
    then(surchargeRepository).should(times(1)).save(surchargeCaptor.capture());

    Surcharge savedSurcharge = surchargeCaptor.getValue();
    assertThat(savedSurcharge.getLineId()).isEqualTo(line.getId());
    assertThat(savedSurcharge.getAmount()).isEqualTo(amount);
  }

  @DisplayName("모든 요금 정보를 조회한다.")
  @Test
  void findAll() {
    surchargeService.findAll();

    then(surchargeRepository).should(times(1)).findAll();
  }
}
