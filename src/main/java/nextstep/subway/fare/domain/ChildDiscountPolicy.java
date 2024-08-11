package nextstep.subway.fare.domain;

public class ChildDiscountPolicy extends AgeDiscountPolicy {
  public static final long DEDUCTION_AMOUNT = 350L;
  public static final double CHILD_DISCOUNT = 0.5;

  public ChildDiscountPolicy() {
    super(CHILD_DISCOUNT, DEDUCTION_AMOUNT);
  }
}
