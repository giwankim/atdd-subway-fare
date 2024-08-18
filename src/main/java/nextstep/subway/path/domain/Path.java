package nextstep.subway.path.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.line.domain.Line;
import nextstep.subway.station.domain.Station;

public class Path {
  private final List<Station> stations;
  private final List<LineSectionEdge> edges;

  private Path(List<Station> stations, List<LineSectionEdge> edges) {
    this.stations = stations;
    this.edges = edges;
  }

  public static Path empty() {
    return new Path(Collections.emptyList(), Collections.emptyList());
  }

  public static Path of(List<Station> stations, List<LineSectionEdge> edges) {
    return new Path(stations, edges);
  }

  public List<Station> getStations() {
    return Collections.unmodifiableList(stations);
  }

  public List<Line> getLines() {
    return edges.stream().map(LineSectionEdge::getLine).collect(Collectors.toUnmodifiableList());
  }

  public long getTotalDistance() {
    return edges.stream().mapToLong(e -> e.getLineSection().getDistance()).sum();
  }

  public long getTotalDuration() {
    return edges.stream().mapToLong(e -> e.getLineSection().getDuration()).sum();
  }

  public LocalDateTime getArrivalTime(LocalDateTime departureTime) {
    LocalDateTime arrivalTime = departureTime;
    for (LineSectionEdge edge : edges) {
      arrivalTime = edge.getArrivalTime(arrivalTime);
    }
    return arrivalTime;
  }
}
