package nextstep.subway.fare.domain;

import java.util.List;
import nextstep.subway.path.domain.Path2;

public abstract class SurchargePolicy2 {
  private final List<SurchargeCondition2> conditions;

  protected SurchargePolicy2(SurchargeCondition2... conditions) {
    this.conditions = List.of(conditions);
  }

  public long calculateSurcharge(Path2 path) {
    return conditions.stream()
        .filter(condition -> condition.isSatisfiedBy(path))
        .findFirst()
        .map(condition -> getSurchargeAmount(path))
        .orElse(0L);
  }

  protected abstract long getSurchargeAmount(Path2 path);
}
