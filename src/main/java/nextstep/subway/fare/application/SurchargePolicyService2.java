package nextstep.subway.fare.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.subway.fare.domain.*;
import nextstep.subway.line.application.LineService2;
import nextstep.subway.line.domain.Line2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurchargePolicyService2 {
  private static final long OVERCHARGE_PER_5KM = 100L;
  private static final long OVERCHARGE_PER_8KM = 100L;
  private static final long DISTANCE_5KM = 5L;
  private static final long DISTANCE_8KM = 8L;

  private final LineService2 lineService;

  public SurchargePolicy2 loadPolicy() {
    List<Line2> lines = lineService.findAllLines();
    Map<Long, Integer> lineIdToSurcharge =
        lines.stream().collect(Collectors.toMap(Line2::getId, Line2::getSurcharge));
    return new OverlappedSurchargePolicy2(
        new DistanceSurchargePolicy2(10L, 50L, OVERCHARGE_PER_5KM, DISTANCE_5KM),
        new DistanceSurchargePolicy2(50L, Long.MAX_VALUE, OVERCHARGE_PER_8KM, DISTANCE_8KM),
        new LineSurchargePolicy2(lineIdToSurcharge));
  }
}
