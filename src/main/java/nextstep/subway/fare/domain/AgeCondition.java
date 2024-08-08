package nextstep.subway.fare.domain;

import nextstep.member.domain.Member;

public class AgeCondition implements DiscountCondition {
  private final int lowerBound;
  private final int upperBound;

  public AgeCondition(int lowerBound, int upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  @Override
  public boolean isSatisfiedBy(Member member) {
    return member.getAge() >= lowerBound && member.getAge() < upperBound;
  }
}
