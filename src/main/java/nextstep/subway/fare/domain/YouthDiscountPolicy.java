package nextstep.subway.fare.domain;

public class YouthDiscountPolicy extends AgeDiscountPolicy {
  private static final long DEDUCTION_AMOUNT = 350L;
  private static final double YOUTH_DISCOUNT_RATE = 0.2;

  public YouthDiscountPolicy() {
    super(YOUTH_DISCOUNT_RATE, DEDUCTION_AMOUNT);
  }
}
