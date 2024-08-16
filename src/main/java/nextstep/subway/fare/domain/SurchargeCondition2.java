package nextstep.subway.fare.domain;

import nextstep.subway.path.domain.Path;

public interface SurchargeCondition2 {
  boolean isSatisfiedBy(Path path);
}
