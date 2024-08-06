package nextstep.subway.path.domain;

import nextstep.member.domain.Member;

public interface DiscountCondition {
  boolean isSatisfiedBy(Member member);
}
