package nextstep.subway.fare.domain;

import nextstep.subway.path.domain.Path2;

public class DistanceCondition2 implements SurchargeCondition2 {
  private final long lowerBound;

  public DistanceCondition2(long lowerBound) {
    this.lowerBound = lowerBound;
  }

  @Override
  public boolean isSatisfiedBy(Path2 path) {
    return path.getTotalDistance() >= lowerBound;
  }
}
