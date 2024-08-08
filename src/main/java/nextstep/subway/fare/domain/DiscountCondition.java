package nextstep.subway.fare.domain;

import nextstep.member.domain.Member;

public interface DiscountCondition {
  boolean isSatisfiedBy(Member member);
}
