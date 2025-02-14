package nextstep.member.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.auth.ui.AuthenticationPrincipal;
import nextstep.member.application.MemberService;
import nextstep.member.application.dto.MemberRequest;
import nextstep.member.application.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @PostMapping("/members")
  public ResponseEntity<Void> createMember(@RequestBody MemberRequest request) {
    MemberResponse member = memberService.createMember(request);
    return ResponseEntity.created(URI.create("/members/" + member.getId())).build();
  }

  @GetMapping("/members/{id}")
  public ResponseEntity<MemberResponse> findMember(@PathVariable Long id) {
    MemberResponse member = memberService.findMember(id);
    return ResponseEntity.ok().body(member);
  }

  @PutMapping("/members/{id}")
  public ResponseEntity<MemberResponse> updateMember(
      @PathVariable Long id, @RequestBody MemberRequest param) {
    memberService.updateMember(id, param);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/members/{id}")
  public ResponseEntity<MemberResponse> deleteMember(@PathVariable Long id) {
    memberService.deleteMember(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/members/me")
  public ResponseEntity<MemberResponse> findMemberOfMine(
      @AuthenticationPrincipal LoginMember loginMember) {
    MemberResponse memberResponse = memberService.findMe(loginMember);
    return ResponseEntity.ok().body(memberResponse);
  }
}
