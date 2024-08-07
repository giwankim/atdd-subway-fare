package nextstep.subway.surcharge.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.surcharge.application.dto.SurchargeRequest;
import nextstep.subway.surcharge.application.dto.SurchargeResponse;
import nextstep.subway.surcharge.domain.Surcharge;
import nextstep.subway.surcharge.domain.SurchargeRepository;
import nextstep.subway.surcharge.exception.SurchargeNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurchargeService {
  private final SurchargeRepository surchargeRepository;
  private final SurchargeMapper surchargeMapper;

  public SurchargeResponse save(SurchargeRequest request) {
    Surcharge surcharge = new Surcharge(request.getLineId(), request.getSurcharge());
    Surcharge savedSurcharge = surchargeRepository.save(surcharge);
    return surchargeMapper.mapToResponse(savedSurcharge);
  }

  public SurchargeResponse findById(Long id) {
    Surcharge surcharge =
        surchargeRepository.findById(id).orElseThrow(() -> new SurchargeNotFoundException(id));
    return surchargeMapper.mapToResponse(surcharge);
  }
}
