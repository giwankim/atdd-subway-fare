package nextstep.subway.fare.domain;

import java.util.Arrays;
import java.util.List;
import nextstep.subway.path.domain.Path;

public class OverlappedSurchargePolicy extends SurchargePolicy {
  private final List<SurchargePolicy> policies;

  public OverlappedSurchargePolicy(SurchargePolicy... policies) {
    super(path -> true);
    this.policies = Arrays.asList(policies);
  }

  @Override
  protected long getSurchargeAmount(Path path) {
    long overFareAmount = 0L;

    for (SurchargePolicy policy : policies) {
      overFareAmount += policy.calculateSurcharge(path);
    }

    return overFareAmount;
  }
}
