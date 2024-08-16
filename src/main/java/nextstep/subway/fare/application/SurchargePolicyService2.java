package nextstep.subway.fare.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.subway.fare.domain.*;
import nextstep.subway.line.application.LineService;
import nextstep.subway.line.domain.Line;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurchargePolicyService2 {
  public static final long OVERCHARGE_PER_5KM = 100L;
  public static final long OVERCHARGE_PER_8KM = 100L;
  public static final long DISTANCE_5KM = 5L;
  public static final long DISTANCE_8KM = 8L;

  private final LineService lineService;

  public SurchargePolicy2 loadPolicy() {
    List<Line> lines = lineService.findAllLines();
    Map<Long, Integer> lineIdToSurcharge =
        lines.stream().collect(Collectors.toMap(Line::getId, Line::getSurcharge));
    return new OverlappedSurchargePolicy2(
        new DistanceSurchargePolicy2(10L, 50L, OVERCHARGE_PER_5KM, DISTANCE_5KM),
        new DistanceSurchargePolicy2(50L, Long.MAX_VALUE, OVERCHARGE_PER_8KM, DISTANCE_8KM),
        new LineSurchargePolicy2(lineIdToSurcharge));
  }
}
