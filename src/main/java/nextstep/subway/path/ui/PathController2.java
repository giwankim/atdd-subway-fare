package nextstep.subway.path.ui;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.auth.ui.AuthenticationPrincipal;
import nextstep.subway.path.application.PathService2;
import nextstep.subway.path.application.dto.PathRequest2;
import nextstep.subway.path.application.dto.PathResponse2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PathController2 {
  private final PathService2 pathService;

  @GetMapping("/new/paths")
  public ResponseEntity<PathResponse2> findPath(
      @ModelAttribute PathRequest2 request, @AuthenticationPrincipal LoginMember loginMember) {
    PathResponse2 response = pathService.findPath(request, loginMember);
    return ResponseEntity.ok().body(response);
  }
}
