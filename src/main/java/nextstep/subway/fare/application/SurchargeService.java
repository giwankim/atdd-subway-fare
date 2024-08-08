package nextstep.subway.fare.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.subway.fare.application.dto.SurchargeRequest;
import nextstep.subway.fare.application.dto.SurchargeResponse;
import nextstep.subway.fare.domain.Surcharge;
import nextstep.subway.fare.domain.SurchargeRepository;
import nextstep.subway.fare.exception.SurchargeNotFoundException;
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

  public List<SurchargeResponse> findAll() {
    List<Surcharge> surcharges = surchargeRepository.findAll();
    return surcharges.stream().map(surchargeMapper::mapToResponse).collect(Collectors.toList());
  }
}
