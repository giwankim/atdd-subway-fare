package nextstep.subway.path.domain;

public class DistanceOverFarePolicy extends OverFarePolicy {
  private final long lowerBound;
  private final long upperBound;
  private final long farePerUnitDistance;
  private final long distanceUnit;

  public DistanceOverFarePolicy(
      long lowerBound, long upperBound, long farePerUnitDistance, long distanceUnit) {
    super(new DistanceCondition(lowerBound));
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.farePerUnitDistance = farePerUnitDistance;
    this.distanceUnit = distanceUnit;
  }

  @Override
  protected long getOverFareAmount(Path path) {
    long distance = Math.min(path.getTotalDistance(), upperBound);
    return (long) Math.ceil((double) (distance - lowerBound) / distanceUnit) * farePerUnitDistance;
  }
}
