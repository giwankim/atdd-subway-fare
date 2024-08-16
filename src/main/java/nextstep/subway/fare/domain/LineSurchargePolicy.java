package nextstep.subway.fare.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.Path;

public class LineSurchargePolicy extends SurchargePolicy {
  private final Map<Long, Integer> lineIdToSurcharge;

  public LineSurchargePolicy(Map<Long, Integer> lineIdToSurcharge) {
    super(path -> true);
    this.lineIdToSurcharge = lineIdToSurcharge;
  }

  @Override
  protected long getSurchargeAmount(Path path) {
    List<Line> lines = path.getLines();
    return lines.stream()
        .map(line -> lineIdToSurcharge.get(line.getId()))
        .filter(Objects::nonNull)
        .max(Integer::compareTo)
        .orElse(0);
  }
}
