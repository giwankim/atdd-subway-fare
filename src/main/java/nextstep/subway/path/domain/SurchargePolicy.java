package nextstep.subway.path.domain;

import java.util.List;

public abstract class SurchargePolicy {
  private final List<SurchargeCondition> conditions;

  protected SurchargePolicy(SurchargeCondition... conditions) {
    this.conditions = List.of(conditions);
  }

  public long calculateSurcharge(Path path) {
    return conditions.stream()
        .filter(condition -> condition.isSatisfiedBy(path))
        .findFirst()
        .map(condition -> getSurchargeAmount(path))
        .orElse(0L);
  }

  protected abstract long getSurchargeAmount(Path path);
}
