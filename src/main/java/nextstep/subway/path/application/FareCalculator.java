package nextstep.subway.path.application;

import nextstep.member.domain.Member;
import nextstep.subway.path.domain.*;
import org.springframework.stereotype.Service;

@Service
public class FareCalculator {
  private static final long BASE_FARE = 1250L;
  public static final long OVERCHARGE = 100L;
  public static final long DEDUCTION_AMOUNT = 350L;

  private final SurchargePolicy surchargePolicy =
      new OverlappedSurchargePolicy(
          new DistanceSurchargePolicy(10L, 50L, OVERCHARGE, 5L),
          new DistanceSurchargePolicy(50L, Long.MAX_VALUE, OVERCHARGE, 8L));
  private final DiscountPolicy discountPolicy =
      new OverlappedDiscountPolicy(
          new PercentDiscountPolicy(0.2, DEDUCTION_AMOUNT, new AgeCondition(13, 19)),
          new PercentDiscountPolicy(0.5, DEDUCTION_AMOUNT, new AgeCondition(6, 13)));

  public long calculateFare(Path2 path, Member member) {
    long fare = BASE_FARE + surchargePolicy.calculateSurcharge(path);
    return fare - discountPolicy.calculateDiscount(fare, member);
  }
}
