package nextstep.subway.path.domain;

import lombok.Getter;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
public class LineSectionEdge extends DefaultWeightedEdge {
  private final LineSection lineSection;
  private final Line line;

  private LineSectionEdge(LineSection lineSection, Line line) {
    this.lineSection = lineSection;
    this.line = line;
  }

  public static LineSectionEdge of(LineSection lineSection, Line line) {
    return new LineSectionEdge(lineSection, line);
  }
}
