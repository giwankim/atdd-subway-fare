package nextstep.subway.path.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Paths {
  private final List<Path> pathList;

  public Paths(List<Path> pathList) {
    this.pathList = pathList;
  }

  public List<Path> getPaths() {
    return Collections.unmodifiableList(pathList);
  }

  public Path getEarliestArrivalPath(LocalDateTime departureTime) {
    return pathList.stream()
        .min(Comparator.comparing(it -> it.getArrivalTime(departureTime)))
        .orElseThrow(() -> new IllegalArgumentException("최단 경로가 존재하지 않습니다."));
  }
}
