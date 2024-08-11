package nextstep.subway.path.ui;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.auth.ui.AuthenticationPrincipal;
import nextstep.subway.path.application.PathService;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PathController2 {
  private final PathService pathService;

  @GetMapping("/new/paths")
  public ResponseEntity<PathResponse> findPath(
      @ModelAttribute PathRequest request, @AuthenticationPrincipal LoginMember loginMember) {
    PathResponse response = pathService.findPath(request, loginMember);
    return ResponseEntity.ok().body(response);
  }
}
