package nextstep.subway.path.domain;

import java.util.Arrays;
import java.util.List;

public class OverlappedOverFarePolicy extends OverFarePolicy {
  private final List<OverFarePolicy> policies;

  public OverlappedOverFarePolicy(OverFarePolicy... policies) {
    super(path -> true);
    this.policies = Arrays.asList(policies);
  }

  @Override
  protected long getOverFareAmount(Path path) {
    long overFareAmount = 0L;

    for (OverFarePolicy policy : policies) {
      overFareAmount += policy.calculateOverFare(path);
    }

    return overFareAmount;
  }
}
