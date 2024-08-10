package nextstep.subway.fare.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.fare.application.dto.SurchargeResponse2;
import nextstep.subway.fare.domain.Surcharge;
import nextstep.subway.line.application.LineService2;
import nextstep.subway.line.domain.Line2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurchargeMapper2 {
  private final LineService2 lineService;

  public SurchargeResponse2 mapToResponse(Surcharge surcharge) {
    Line2 line = lineService.findLineById(surcharge.getLineId());
    return SurchargeResponse2.of(surcharge, line);
  }
}
