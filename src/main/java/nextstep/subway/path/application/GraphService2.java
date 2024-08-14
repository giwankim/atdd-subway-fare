package nextstep.subway.path.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.subway.line.application.LineService2;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.path.domain.PathType2;
import nextstep.subway.path.domain.SubwayGraph2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphService2 {
  private final LineService2 lineService;

  public SubwayGraph2 loadGraph(PathType2 type) {
    SubwayGraph2 graph = new SubwayGraph2(type);
    List<Line2> lines = lineService.findAllLines();
    lines.forEach(graph::addLine);
    return graph;
  }
}
