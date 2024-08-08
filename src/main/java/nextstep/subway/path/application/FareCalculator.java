package nextstep.subway.path.application;

import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.subway.path.domain.*;
import nextstep.subway.surcharge.application.SurchargePolicyService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FareCalculator {
  private static final long BASE_FARE = 1250L;
  public static final long DEDUCTION_AMOUNT = 350L;

  private final SurchargePolicyService surchargePolicyService;

  private final DiscountPolicy discountPolicy =
      new OverlappedDiscountPolicy(
          new PercentDiscountPolicy(0.2, DEDUCTION_AMOUNT, new AgeCondition(13, 19)),
          new PercentDiscountPolicy(0.5, DEDUCTION_AMOUNT, new AgeCondition(6, 13)));

  public long calculateFare(Path path, Member member) {
    SurchargePolicy surchargePolicy = surchargePolicyService.loadPolicy();
    long fare = BASE_FARE + surchargePolicy.calculateSurcharge(path);
    return fare - discountPolicy.calculateDiscount(fare, member);
  }
}
