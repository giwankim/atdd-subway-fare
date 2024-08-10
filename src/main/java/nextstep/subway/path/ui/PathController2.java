package nextstep.subway.path.ui;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.auth.ui.AuthenticationPrincipal;
import nextstep.member.application.MemberService;
import nextstep.member.domain.Member;
import nextstep.subway.fare.application.FareCalculator2;
import nextstep.subway.path.application.PathService2;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse2;
import nextstep.subway.path.domain.Path2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PathController2 {
  private final PathService2 pathService;
  private final FareCalculator2 fareCalculator;
  private final MemberService memberService;

  @GetMapping("/new/paths")
  public ResponseEntity<PathResponse2> findPath(
      @ModelAttribute PathRequest request, @AuthenticationPrincipal LoginMember loginMember) {
    Member member = memberService.findMemberByEmail(loginMember.getEmail());
    Path2 path = pathService.findPath(request);
    long fare = fareCalculator.calculateFare(path, member);
    return ResponseEntity.ok(PathResponse2.of(path, fare));
  }
}
