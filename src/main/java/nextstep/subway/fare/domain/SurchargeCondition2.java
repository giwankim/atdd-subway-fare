package nextstep.subway.fare.domain;

import nextstep.subway.path.domain.Path2;

public interface SurchargeCondition2 {
  boolean isSatisfiedBy(Path2 path);
}
