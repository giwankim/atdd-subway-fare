package nextstep.subway.fare.domain;

import nextstep.member.domain.Member;

public class PercentDiscountPolicy extends DiscountPolicy {
  private final double percent;
  private final long deductionAmount;

  public PercentDiscountPolicy(
      double percent, long deductionAmount, DiscountCondition... conditions) {
    super(conditions);
    this.percent = percent;
    this.deductionAmount = deductionAmount;
  }

  @Override
  protected long getDiscountAmount(long fare, Member member) {
    return (long) ((fare - deductionAmount) * percent);
  }
}
