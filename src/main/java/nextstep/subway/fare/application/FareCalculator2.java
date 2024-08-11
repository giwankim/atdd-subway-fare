package nextstep.subway.fare.application;

import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.subway.fare.domain.*;
import nextstep.subway.path.domain.Path2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FareCalculator2 {
  private static final long BASE_FARE = 1250L;
  public static final long DEDUCTION_AMOUNT = 350L;

  private final SurchargePolicyService2 surchargePolicyService;

  private final DiscountPolicy discountPolicy =
      new OverlappedDiscountPolicy(
          new PercentDiscountPolicy(0.2, DEDUCTION_AMOUNT, new AgeCondition(13, 19)),
          new PercentDiscountPolicy(0.5, DEDUCTION_AMOUNT, new AgeCondition(6, 13)));

  public long calculateFare(Path2 path, Member member) {
    SurchargePolicy2 surchargePolicy = surchargePolicyService.loadPolicy();
    long fare = BASE_FARE + surchargePolicy.calculateSurcharge(path);

    return fare - discountPolicy.calculateDiscount(fare, member);
  }
}
