package nextstep.subway.path.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import nextstep.subway.line.domain.Line;

public class LineSurchargePolicy extends SurchargePolicy {
  private final Map<Long, Long> lineIdToSurcharge;

  public LineSurchargePolicy(Map<Long, Long> lineIdToSurcharge) {
    super(path -> true);
    this.lineIdToSurcharge = lineIdToSurcharge;
  }

  @Override
  protected long getSurchargeAmount(Path path) {
    List<Line> lines = path.getLines();
    return lines.stream()
        .mapToLong(line -> lineIdToSurcharge.get(line.getId()))
        .filter(Objects::nonNull)
        .max()
        .orElse(0L);
  }
}
