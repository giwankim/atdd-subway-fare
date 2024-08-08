package nextstep.subway.surcharge.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.subway.path.domain.DistanceSurchargePolicy;
import nextstep.subway.path.domain.LineSurchargePolicy;
import nextstep.subway.path.domain.OverlappedSurchargePolicy;
import nextstep.subway.path.domain.SurchargePolicy;
import nextstep.subway.surcharge.application.dto.SurchargeResponse;
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
