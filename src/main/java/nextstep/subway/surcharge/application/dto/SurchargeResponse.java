package nextstep.subway.surcharge.application.dto;

import lombok.Getter;
import nextstep.subway.line.application.dto.LineResponse;
import nextstep.subway.line.domain.Line;
import nextstep.subway.surcharge.domain.Surcharge;

@Getter
public class SurchargeResponse {
  private final Long id;
  private final LineResponse line;
  private final long surcharge;

  private SurchargeResponse(Long id, LineResponse line, long surcharge) {
    this.id = id;
    this.line = line;
    this.surcharge = surcharge;
  }

  public static SurchargeResponse of(Surcharge surcharge, Line line) {
    return new SurchargeResponse(surcharge.getId(), LineResponse.from(line), surcharge.getAmount());
  }
}
