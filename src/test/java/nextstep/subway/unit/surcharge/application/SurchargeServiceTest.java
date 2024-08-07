package nextstep.subway.unit.surcharge.application;

import static nextstep.Fixtures.신분당선;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import nextstep.subway.line.domain.Line;
import nextstep.subway.surcharge.application.SurchargeMapper;
import nextstep.subway.surcharge.application.SurchargeService;
import nextstep.subway.surcharge.application.dto.SurchargeRequest;
import nextstep.subway.surcharge.domain.Surcharge;
import nextstep.subway.surcharge.domain.SurchargeRepository;
import nextstep.subway.surcharge.exception.SurchargeNotFoundException;
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
  @Mock private SurchargeMapper surchargeMapper;
  @InjectMocks private SurchargeService surchargeService;

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

  @DisplayName("요금 정보를 ID 로 조회한다.")
  @Test
  void findById() {
    given(surchargeRepository.findById(1L)).willReturn(Optional.of(new Surcharge(1L, 1L, 900L)));

    surchargeService.findById(1L);

    then(surchargeRepository).should(times(1)).findById(1L);
  }

  @DisplayName("요금 정보를 ID 로 조회할 때 요금 정보가 없으면 예외를 던진다.")
  @Test
  void findByIdThrowsException() {
    given(surchargeRepository.findById(99L)).willReturn(Optional.empty());
    assertThatExceptionOfType(SurchargeNotFoundException.class)
        .isThrownBy(() -> surchargeService.findById(99L));
  }
}
