package nextstep.subway.fare.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import nextstep.subway.fare.application.SurchargeService;
import nextstep.subway.fare.application.dto.SurchargeRequest;
import nextstep.subway.fare.application.dto.SurchargeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/surcharges")
@RequiredArgsConstructor
public class SurchargeController {
  private final SurchargeService surchargeService;

  @PostMapping
  public ResponseEntity<SurchargeResponse> create(@RequestBody SurchargeRequest request) {
    SurchargeResponse surcharge = surchargeService.save(request);
    return ResponseEntity.created(URI.create("/surcharges/" + surcharge.getId())).body(surcharge);
  }
}
