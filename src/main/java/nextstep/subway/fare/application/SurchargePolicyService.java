package nextstep.subway.fare.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.subway.fare.application.dto.SurchargeResponse;
import nextstep.subway.fare.domain.DistanceSurchargePolicy;
import nextstep.subway.fare.domain.LineSurchargePolicy;
import nextstep.subway.fare.domain.OverlappedSurchargePolicy;
import nextstep.subway.fare.domain.SurchargePolicy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurchargePolicyService {
  public static final long OVERCHARGE = 100L;

  private final SurchargeService surchargeService;

  public SurchargePolicy loadPolicy() {
    List<SurchargeResponse> surcharges = surchargeService.findAll();
    Map<Long, Long> lineIdToSurcharge =
        surcharges.stream()
            .collect(
                Collectors.toMap(
                    surcharge -> surcharge.getLine().getId(), SurchargeResponse::getSurcharge));

    return new OverlappedSurchargePolicy(
        new DistanceSurchargePolicy(10L, 50L, OVERCHARGE, 5L),
        new DistanceSurchargePolicy(50L, Long.MAX_VALUE, OVERCHARGE, 8L),
        new LineSurchargePolicy(lineIdToSurcharge));
  }
}
