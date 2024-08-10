package nextstep.subway.fare.application.dto;

import lombok.Getter;
import nextstep.subway.fare.domain.Surcharge;
import nextstep.subway.line.application.dto.LineResponse2;
import nextstep.subway.line.domain.Line2;

@Getter
public class SurchargeResponse2 {
  private final Long id;
  private final LineResponse2 line;
  private final long surcharge;

  private SurchargeResponse2(Long id, LineResponse2 line, long surcharge) {
    this.id = id;
    this.line = line;
    this.surcharge = surcharge;
  }

  public static SurchargeResponse2 of(Surcharge surcharge, Line2 line) {
    return new SurchargeResponse2(
        surcharge.getId(), LineResponse2.from(line), surcharge.getAmount());
  }
}
