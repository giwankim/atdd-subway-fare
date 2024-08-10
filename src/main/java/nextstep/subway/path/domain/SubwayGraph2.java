package nextstep.subway.path.domain;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

public class SubwayGraph2 {
  private static final String STATION_NOT_FOUND = "구간의 상/하행역이 존재하지 않습니다.";
  private static final double EPSILON = 10e-7;

  private final WeightedMultigraph<Station, LineSectionEdge2> graph;
  private final PathType type;

  private SubwayGraph2(WeightedMultigraph<Station, LineSectionEdge2> graph, PathType type) {
    this.graph = graph;
    this.type = type;
  }

  public SubwayGraph2(PathType type) {
    this(new WeightedMultigraph<>(LineSectionEdge2.class), type);
  }

  public void addLine(Line2 line) {
    line.getStations().forEach(this::addStation);
    line.getLineSections().getSections().forEach(section -> addLineSection(section, line));
  }

  private void addStation(Station station) {
    graph.addVertex(station);
  }

  private void addLineSection(LineSection lineSection, Line2 line) {
    LineSectionEdge2 edge = LineSectionEdge2.of(lineSection, line);
    graph.addEdge(lineSection.getUpStation(), lineSection.getDownStation(), edge);
    graph.setEdgeWeight(edge, type.getEdgeWeight(lineSection));
  }

  public Path2 getShortestPath(Station source, Station target) {
    validate(source, target);

    DijkstraShortestPath<Station, LineSectionEdge2> shortestPath =
        new DijkstraShortestPath<>(graph);

    GraphPath<Station, LineSectionEdge2> path = shortestPath.getPath(source, target);
    if (path == null) {
      return Path2.empty();
    }

    List<LineSectionEdge2> edges = path.getEdgeList();
    long totalDistance = edges.stream().mapToLong(e -> e.getLineSection().getDistance()).sum();
    long totalDuration = edges.stream().mapToLong(e -> e.getLineSection().getDuration()).sum();
    List<Line2> lines = edges.stream().map(LineSectionEdge2::getLine).collect(Collectors.toList());
    return Path2.of(path.getVertexList(), lines, totalDistance, totalDuration);
  }

  private void validate(Station source, Station target) {
    if (!graph.containsVertex(source) || !graph.containsVertex(target)) {
      throw new IllegalArgumentException(STATION_NOT_FOUND);
    }
  }

  public boolean isSame(SubwayGraph2 that) {
    if (type != that.type) {
      return false;
    }
    if (!graph.vertexSet().equals(that.graph.vertexSet())) {
      return false;
    }
    if (graph.edgeSet().size() != that.graph.edgeSet().size()) {
      return false;
    }
    for (LineSectionEdge2 edge : graph.edgeSet()) {
      Station source = graph.getEdgeSource(edge);
      Station target = graph.getEdgeTarget(edge);
      LineSectionEdge2 thatEdge = that.graph.getEdge(source, target);
      if (thatEdge == null) {
        return false;
      }
      if (Math.abs(graph.getEdgeWeight(edge) - that.graph.getEdgeWeight(thatEdge)) > EPSILON) {
        return false;
      }
    }
    return true;
  }
}
