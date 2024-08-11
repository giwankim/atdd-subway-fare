package nextstep.subway.fare.domain;

import nextstep.subway.path.domain.Path;

public interface SurchargeCondition {
  boolean isSatisfiedBy(Path path);
}
