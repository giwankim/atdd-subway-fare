package nextstep.subway.fare.domain;

public class DefaultDiscountPolicy extends AgeDiscountPolicy {
  public DefaultDiscountPolicy() {
    super(0.0, 0L);
  }
}
