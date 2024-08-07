package nextstep.subway.surcharge.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.line.application.LineService;
import nextstep.subway.line.domain.Line;
import nextstep.subway.surcharge.application.dto.SurchargeResponse;
import nextstep.subway.surcharge.domain.Surcharge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurchargeMapper {
  private final LineService lineService;

  public SurchargeResponse mapToResponse(Surcharge surcharge) {
    Line line = lineService.findLineById(surcharge.getLineId());
    return SurchargeResponse.of(surcharge, line);
  }
}
