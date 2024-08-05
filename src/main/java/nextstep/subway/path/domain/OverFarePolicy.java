package nextstep.subway.path.domain;

import java.util.List;

public abstract class OverFarePolicy {
  private final List<OverFareCondition> conditions;

  protected OverFarePolicy(OverFareCondition... conditions) {
    this.conditions = List.of(conditions);
  }

  public long calculateOverFare(Path path) {
    for (OverFareCondition condition : conditions) {
      if (condition.isSatisfiedBy(path)) {
        return getOverFareAmount(path);
      }
    }

    return 0L;
  }

  protected abstract long getOverFareAmount(Path path);
}
