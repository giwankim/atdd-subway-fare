package nextstep.subway.fare.domain;

public interface DiscountPolicy {
  long calculateDiscount(long fare);
}
