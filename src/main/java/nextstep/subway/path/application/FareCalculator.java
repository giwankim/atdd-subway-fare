package nextstep.subway.path.application;

import nextstep.subway.path.domain.Path;
import org.springframework.stereotype.Service;

@Service
public class FareCalculator {
  private static final long BASE_FARE = 1250L;
  private static final long ZERO = 0L;

  public long calculateFare(Path path) {
    return BASE_FARE + calculateOverFare(path.getTotalDistance());
  }

  private long calculateOverFare(long distance) {
    if (distance <= 10) {
      return ZERO;
    }
    if (distance <= 50) {
      return (long) Math.ceil((double) (distance - 10) / 5) * 100L;
    }
    return 800L + (long) Math.ceil((double) (distance - 50) / 8) * 100L;
  }
}
