package nextstep.subway.path.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.WeightedMultigraph;

public class SubwayGraph {
  private static final String STATION_NOT_FOUND = "구간의 상/하행역이 존재하지 않습니다.";
  private static final double EPSILON = 10e-7;
  public static final int MAX_PATH_COUNT = 1000;

  private final WeightedMultigraph<Station, LineSectionEdge> graph;
  private final PathType type;

  private SubwayGraph(WeightedMultigraph<Station, LineSectionEdge> graph, PathType type) {
    this.graph = graph;
    this.type = type;
  }

  public SubwayGraph(PathType type) {
    this(new WeightedMultigraph<>(LineSectionEdge.class), type);
  }

  public void addLine(Line line) {
    line.getStations().forEach(this::addStation);
    line.getLineSections().getSections().forEach(section -> addLineSection(section, line));
  }

  private void addStation(Station station) {
    graph.addVertex(station);
  }

  private void addLineSection(LineSection lineSection, Line line) {
    LineSectionEdge edge = LineSectionEdge.of(lineSection, line);
    graph.addEdge(lineSection.getUpStation(), lineSection.getDownStation(), edge);
    graph.setEdgeWeight(edge, type.getEdgeWeight(lineSection));
  }

  public Path getShortestPath(Station source, Station target) {
    validate(source, target);

    DijkstraShortestPath<Station, LineSectionEdge> shortestPath = new DijkstraShortestPath<>(graph);

    GraphPath<Station, LineSectionEdge> path = shortestPath.getPath(source, target);

    if (path == null) {
      return Path.empty();
    }
    return mapToPath(path);
  }

  public Paths getAllPaths(Station source, Station target) {
    if (source.isSame(target)) {
      return new Paths(Collections.singletonList(getShortestPath(source, target)));
    }

    validate(source, target);

    List<GraphPath<Station, LineSectionEdge>> graphPaths =
        new KShortestPaths<>(graph, MAX_PATH_COUNT).getPaths(source, target);

    return new Paths(graphPaths.stream().map(SubwayGraph::mapToPath).collect(Collectors.toList()));
  }

  private void validate(Station source, Station target) {
    if (!graph.containsVertex(source) || !graph.containsVertex(target)) {
      throw new IllegalArgumentException(STATION_NOT_FOUND);
    }
  }

  private static Path mapToPath(GraphPath<Station, LineSectionEdge> path) {
    return Path.of(path.getVertexList(), path.getEdgeList());
  }

  public boolean isSame(SubwayGraph that) {
    if (type != that.type) {
      return false;
    }
    if (!graph.vertexSet().equals(that.graph.vertexSet())) {
      return false;
    }
    if (graph.edgeSet().size() != that.graph.edgeSet().size()) {
      return false;
    }
    for (LineSectionEdge edge : graph.edgeSet()) {
      Station source = graph.getEdgeSource(edge);
      Station target = graph.getEdgeTarget(edge);
      LineSectionEdge thatEdge = that.graph.getEdge(source, target);
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
