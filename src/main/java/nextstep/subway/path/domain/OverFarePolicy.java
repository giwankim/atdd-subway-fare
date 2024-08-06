package nextstep.subway.path.domain;

import java.util.List;

public abstract class OverFarePolicy {
  private final List<OverFareCondition> conditions;

  protected OverFarePolicy(OverFareCondition... conditions) {
    this.conditions = List.of(conditions);
  }

  public long calculateOverFare(Path path) {
    return conditions.stream()
        .filter(condition -> condition.isSatisfiedBy(path))
        .findFirst()
        .map(condition -> getOverFareAmount(path))
        .orElse(0L);
  }

  protected abstract long getOverFareAmount(Path path);
}
