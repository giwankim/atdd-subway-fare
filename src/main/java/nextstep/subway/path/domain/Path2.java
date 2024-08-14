package nextstep.subway.path.domain;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.station.domain.Station;

public class Path2 {
  private final List<Station> stations;
  private final List<Line2> lines;
  @Getter private final long totalDistance;
  @Getter private final long totalDuration;

  private Path2(List<Station> stations, List<Line2> lines, long totalDistance, long totalDuration) {
    this.stations = stations;
    this.lines = lines;
    this.totalDistance = totalDistance;
    this.totalDuration = totalDuration;
  }

  public static Path2 empty() {
    return new Path2(Collections.emptyList(), Collections.emptyList(), 0, 0);
  }

  public static Path2 of(
      List<Station> stations, List<Line2> lines, long totalDistance, long totalDuration) {
    return new Path2(stations, lines, totalDistance, totalDuration);
  }

  public List<Station> getStations() {
    return Collections.unmodifiableList(stations);
  }

  public List<Line2> getLines() {
    return Collections.unmodifiableList(lines);
  }
}
