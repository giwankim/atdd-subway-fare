package nextstep.subway.fare.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.subway.fare.application.dto.SurchargeResponse2;
import nextstep.subway.fare.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurchargePolicyService2 {
  public static final long OVERCHARGE = 100L;

  private final SurchargeService2 surchargeService;

  public SurchargePolicy2 loadPolicy() {
    List<SurchargeResponse2> surcharges = surchargeService.findAll();
    Map<Long, Long> lineIdToSurcharge =
        surcharges.stream()
            .collect(
                Collectors.toMap(
                    surcharge -> surcharge.getLine().getId(), SurchargeResponse2::getSurcharge));

    return new OverlappedSurchargePolicy2(
        new DistanceSurchargePolicy2(10L, 50L, OVERCHARGE, 5L),
        new DistanceSurchargePolicy2(50L, Long.MAX_VALUE, OVERCHARGE, 8L),
        new LineSurchargePolicy2(lineIdToSurcharge));
  }
}
