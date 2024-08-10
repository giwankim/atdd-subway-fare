package nextstep.subway.fare.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.subway.fare.application.dto.SurchargeRequest;
import nextstep.subway.fare.application.dto.SurchargeResponse2;
import nextstep.subway.fare.domain.Surcharge;
import nextstep.subway.fare.domain.SurchargeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurchargeService2 {
  private final SurchargeRepository surchargeRepository;
  private final SurchargeMapper2 surchargeMapper;

  public SurchargeResponse2 save(SurchargeRequest request) {
    Surcharge surcharge = new Surcharge(request.getLineId(), request.getSurcharge());
    Surcharge savedSurcharge = surchargeRepository.save(surcharge);
    return surchargeMapper.mapToResponse(savedSurcharge);
  }

  public List<SurchargeResponse2> findAll() {
    List<Surcharge> surcharges = surchargeRepository.findAll();
    return surcharges.stream().map(surchargeMapper::mapToResponse).collect(Collectors.toList());
  }
}
