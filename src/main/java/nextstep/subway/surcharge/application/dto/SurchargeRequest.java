package nextstep.subway.surcharge.application.dto;

import lombok.Getter;

@Getter
public class SurchargeRequest {
  private final Long lineId;
  private final Long surcharge;

  public SurchargeRequest(Long lineId, Long surcharge) {
    this.lineId = lineId;
    this.surcharge = surcharge;
  }
}
