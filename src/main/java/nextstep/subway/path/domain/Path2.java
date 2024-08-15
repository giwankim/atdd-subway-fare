package nextstep.subway.path.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.station.domain.Station;

public class Path2 {
  private final List<Station> stations;
  private final List<LineSectionEdge2> edges;

  private Path2(List<Station> stations, List<LineSectionEdge2> edges) {
    this.stations = stations;
    this.edges = edges;
  }

  public static Path2 empty() {
    return new Path2(Collections.emptyList(), Collections.emptyList());
  }

  public static Path2 of(List<Station> stations, List<LineSectionEdge2> edges) {
    return new Path2(stations, edges);
  }

  public List<Station> getStations() {
    return Collections.unmodifiableList(stations);
  }

  public List<Line2> getLines() {
    return edges.stream().map(LineSectionEdge2::getLine).collect(Collectors.toUnmodifiableList());
  }

  public long getTotalDistance() {
    return edges.stream().mapToLong(e -> e.getLineSection().getDistance()).sum();
  }

  public long getTotalDuration() {
    return edges.stream().mapToLong(e -> e.getLineSection().getDuration()).sum();
  }

  public LocalDateTime getArrivalTime(LocalDateTime departureTime) {
    LocalDateTime arrivalTime = departureTime;
    for (LineSectionEdge2 edge : edges) {
      arrivalTime = edge.getArrivalTime(arrivalTime);
    }
    return arrivalTime;
  }
}
