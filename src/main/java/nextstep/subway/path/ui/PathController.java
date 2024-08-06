package nextstep.subway.path.ui;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.auth.ui.AuthenticationPrincipal;
import nextstep.subway.path.application.FareCalculator;
import nextstep.subway.path.application.PathService;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.domain.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PathController {
  private final PathService pathService;
  private final FareCalculator fareCalculator;

  @GetMapping("/paths")
  public ResponseEntity<PathResponse> findPath(
      @ModelAttribute PathRequest request, @AuthenticationPrincipal LoginMember loginMember) {
    Path path = pathService.findPath(request);
    long fare = fareCalculator.calculateFare(path);
    return ResponseEntity.ok(PathResponse.of(path, fare));
  }
}
