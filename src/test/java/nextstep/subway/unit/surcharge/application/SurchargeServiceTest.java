package nextstep.subway.unit.surcharge.application;

import static nextstep.Fixtures.신분당선;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import nextstep.subway.line.application.LineService;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.exception.LineNotFoundException;
import nextstep.subway.surcharge.application.SurchargeService;
import nextstep.subway.surcharge.application.dto.SurchargeRequest;
import nextstep.subway.surcharge.domain.Surcharge;
import nextstep.subway.surcharge.domain.SurchargeRepository;
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
class SurchargeServiceTest {
  @Mock private SurchargeRepository surchargeRepository;
  @Mock private LineService lineService;
  @InjectMocks private SurchargeService surchargeService;

  @DisplayName("요금 정보를 저장한다.")
  @Test
  void save() {
    long amount = 900L;
    Line line = 신분당선();
    SurchargeRequest request = new SurchargeRequest(line.getId(), amount);
    given(lineService.findLineById(request.getLineId())).willReturn(line);

    surchargeService.save(request);

    ArgumentCaptor<Surcharge> surchargeCaptor = ArgumentCaptor.forClass(Surcharge.class);
    then(surchargeRepository).should(times(1)).save(surchargeCaptor.capture());

    Surcharge savedSurcharge = surchargeCaptor.getValue();
    assertThat(savedSurcharge.getLineId()).isEqualTo(line.getId());
    assertThat(savedSurcharge.getAmount()).isEqualTo(amount);
  }

  @DisplayName("노선을 찾을 수 없을 때 예외를 발생시킨다.")
  @Test
  void saveLineNotFound() {
    SurchargeRequest request = new SurchargeRequest(99L, 900L);
    given(lineService.findLineById(99L)).willThrow(LineNotFoundException.class);
    assertThatExceptionOfType(LineNotFoundException.class)
        .isThrownBy(() -> surchargeService.save(request));
  }
}
