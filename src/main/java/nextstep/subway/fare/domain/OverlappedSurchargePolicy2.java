package nextstep.subway.fare.domain;

import java.util.Arrays;
import java.util.List;
import nextstep.subway.path.domain.Path2;

public class OverlappedSurchargePolicy2 extends SurchargePolicy2 {
  private final List<SurchargePolicy2> policies;

  public OverlappedSurchargePolicy2(SurchargePolicy2... policies) {
    super(path -> true);
    this.policies = Arrays.asList(policies);
  }

  @Override
  protected long getSurchargeAmount(Path2 path) {
    long overFareAmount = 0L;
    for (SurchargePolicy2 policy : policies) {
      overFareAmount += policy.calculateSurcharge(path);
    }
    return overFareAmount;
  }
}
