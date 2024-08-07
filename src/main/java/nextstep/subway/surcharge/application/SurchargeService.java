package nextstep.subway.surcharge.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.line.application.LineService;
import nextstep.subway.line.domain.Line;
import nextstep.subway.surcharge.application.dto.SurchargeRequest;
import nextstep.subway.surcharge.domain.Surcharge;
import nextstep.subway.surcharge.domain.SurchargeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurchargeService {
  private final SurchargeRepository surchargeRepository;
  private final LineService lineService;

  public void save(SurchargeRequest request) {
    Line line = lineService.findLineById(request.getLineId());
    Surcharge surcharge = new Surcharge(line.getId(), request.getSurcharge());
    surchargeRepository.save(surcharge);
  }
}
