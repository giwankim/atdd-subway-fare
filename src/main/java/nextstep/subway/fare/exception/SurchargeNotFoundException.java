package nextstep.subway.fare.exception;

import nextstep.support.error.ApiException;
import nextstep.support.error.ErrorCode;

public class SurchargeNotFoundException extends ApiException {
  public SurchargeNotFoundException(Long id) {
    super(ErrorCode.NOT_FOUND, "추가 요금 #" + id + "이 존재하지 않습니다.");
  }
}
