package nextstep.subway.path.application;

import nextstep.subway.path.domain.DistanceOverFarePolicy;
import nextstep.subway.path.domain.OverFarePolicy;
import nextstep.subway.path.domain.OverlappedOverFarePolicy;
import nextstep.subway.path.domain.Path;
import org.springframework.stereotype.Service;

@Service
public class FareCalculator {
  private static final long BASE_FARE = 1250L;
  public static final long OVERCHARGE = 100L;

  private final OverFarePolicy overFarePolicy =
      new OverlappedOverFarePolicy(
          new DistanceOverFarePolicy(10L, 50L, OVERCHARGE, 5L),
          new DistanceOverFarePolicy(50L, Long.MAX_VALUE, OVERCHARGE, 8L));

  public long calculateFare(Path path) {
    return BASE_FARE + overFarePolicy.calculateOverFare(path);
  }
}
