package nextstep.subway.surcharge.exception;

import nextstep.support.error.ApiException;
import nextstep.support.error.ErrorCode;

public class SurchargeByLineIdNotFoundException extends ApiException {
  public SurchargeByLineIdNotFoundException(Long lineId) {
    super(ErrorCode.NOT_FOUND, "lineId #" + lineId + "에 해당하는 추가 요금이 존재하지 않습니다.");
  }
}
