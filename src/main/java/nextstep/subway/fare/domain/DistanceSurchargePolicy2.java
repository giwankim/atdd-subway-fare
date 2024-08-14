package nextstep.subway.fare.domain;

import nextstep.subway.path.domain.Path2;

public class DistanceSurchargePolicy2 extends SurchargePolicy2 {
  private final long lowerBound;
  private final long upperBound;
  private final long farePerUnitDistance;
  private final long distanceUnit;

  public DistanceSurchargePolicy2(
      long lowerBound, long upperBound, long farePerUnitDistance, long distanceUnit) {
    super(new DistanceCondition2(lowerBound));
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.farePerUnitDistance = farePerUnitDistance;
    this.distanceUnit = distanceUnit;
  }

  @Override
  protected long getSurchargeAmount(Path2 path) {
    long distance = Math.min(path.getTotalDistance(), upperBound);
    return (long) Math.ceil((double) (distance - lowerBound) / distanceUnit) * farePerUnitDistance;
  }
}
