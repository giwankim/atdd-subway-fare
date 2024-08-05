package nextstep.subway.path.domain;

public class DistanceCondition implements OverFareCondition {
  private final long lowerBound;

  public DistanceCondition(long lowerBound) {
    this.lowerBound = lowerBound;
  }

  @Override
  public boolean isSatisfiedBy(Path path) {
    return path.getTotalDistance() >= lowerBound;
  }
}
