package nextstep.subway.fare.domain;

import nextstep.subway.path.domain.Path;

public class DistanceCondition implements SurchargeCondition {
  private final long lowerBound;

  public DistanceCondition(long lowerBound) {
    this.lowerBound = lowerBound;
  }

  @Override
  public boolean isSatisfiedBy(Path path) {
    return path.getTotalDistance() >= lowerBound;
  }
}
