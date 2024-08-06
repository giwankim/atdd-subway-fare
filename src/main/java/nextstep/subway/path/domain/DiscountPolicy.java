package nextstep.subway.path.domain;

import java.util.List;
import nextstep.member.domain.Member;

public abstract class DiscountPolicy {
  private final List<DiscountCondition> conditions;

  protected DiscountPolicy(DiscountCondition... conditions) {
    this.conditions = List.of(conditions);
  }

  public long calculateDiscount(long fare, Member member) {
    for (DiscountCondition condition : conditions) {
      if (condition.isSatisfiedBy(member)) {
        return getDiscountAmount(fare, member);
      }
    }
    return 0L;
  }

  protected abstract long getDiscountAmount(long fare, Member member);
}
