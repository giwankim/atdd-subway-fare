package nextstep.subway.fare.application;

import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.subway.fare.domain.AgeDiscountPolicy;
import nextstep.subway.fare.domain.SurchargePolicy2;
import nextstep.subway.path.domain.Path;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FareCalculator2 {
  private static final long BASE_FARE = 1250L;

  private final SurchargePolicyService2 surchargePolicyService;

  public long calculateFare(Path path, Member member) {
    SurchargePolicy2 surchargePolicy = surchargePolicyService.loadPolicy();
    AgeDiscountPolicy discountPolicy = AgeDiscountPolicy.from(member);
    long fare = BASE_FARE + surchargePolicy.calculateSurcharge(path);
    return fare - discountPolicy.calculateDiscount(fare);
  }
}
