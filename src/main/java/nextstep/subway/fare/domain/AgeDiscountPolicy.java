package nextstep.subway.fare.domain;

import nextstep.member.domain.Member;

public abstract class AgeDiscountPolicy implements DiscountPolicy {
  private final double percent;
  private final long deductionAmount;

  protected AgeDiscountPolicy(double percent, long deductionAmount) {
    this.percent = percent;
    this.deductionAmount = deductionAmount;
  }

  public static AgeDiscountPolicy from(Member member) {
    int age = member.getAge();
    if (age >= 6 && age < 13) {
      return new ChildDiscountPolicy();
    }
    if (age >= 13 && age < 19) {
      return new YouthDiscountPolicy();
    }
    return new DefaultDiscountPolicy();
  }

  @Override
  public long calculateDiscount(long fare) {
    return (long) ((fare - deductionAmount) * percent);
  }
}
