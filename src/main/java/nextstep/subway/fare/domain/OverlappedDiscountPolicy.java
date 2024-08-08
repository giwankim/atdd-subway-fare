package nextstep.subway.fare.domain;

import java.util.Arrays;
import java.util.List;
import nextstep.member.domain.Member;

public class OverlappedDiscountPolicy extends DiscountPolicy {
  private final List<DiscountPolicy> policies;

  public OverlappedDiscountPolicy(DiscountPolicy... policies) {
    super(member -> true);
    this.policies = Arrays.asList(policies);
  }

  @Override
  protected long getDiscountAmount(long fare, Member member) {
    long discountAmount = 0L;

    for (DiscountPolicy policy : policies) {
      discountAmount += policy.calculateDiscount(fare, member);
    }

    return discountAmount;
  }
}
