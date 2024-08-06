package nextstep.subway.path.domain;

import java.util.List;
import nextstep.member.domain.Member;

public abstract class DiscountPolicy {
  private final List<DiscountCondition> conditions;

  protected DiscountPolicy(DiscountCondition... conditions) {
    this.conditions = List.of(conditions);
  }

  public long calculateDiscount(long fare, Member member) {
    return conditions.stream()
        .filter(condition -> condition.isSatisfiedBy(member))
        .findFirst()
        .map(condition -> getDiscountAmount(fare, member))
        .orElse(0L);
  }

  protected abstract long getDiscountAmount(long fare, Member member);
}
