package nextstep.subway.fare.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.path.domain.Path2;

public class LineSurchargePolicy2 extends SurchargePolicy2 {
  private final Map<Long, Integer> lineIdToSurcharge;

  public LineSurchargePolicy2(Map<Long, Integer> lineIdToSurcharge) {
    super(path -> true);
    this.lineIdToSurcharge = lineIdToSurcharge;
  }

  @Override
  protected long getSurchargeAmount(Path2 path) {
    List<Line2> lines = path.getLines();
    return lines.stream()
        .map(line -> lineIdToSurcharge.get(line.getId()))
        .filter(Objects::nonNull)
        .max(Integer::compareTo)
        .orElse(0);
  }
}
