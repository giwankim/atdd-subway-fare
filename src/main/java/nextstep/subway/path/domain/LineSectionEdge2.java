package nextstep.subway.path.domain;

import lombok.Getter;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.line.domain.LineSection;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
public class LineSectionEdge2 extends DefaultWeightedEdge {
  private final LineSection lineSection;
  private final Line2 line;

  private LineSectionEdge2(LineSection lineSection, Line2 line) {
    this.lineSection = lineSection;
    this.line = line;
  }

  public static LineSectionEdge2 of(LineSection lineSection, Line2 line) {
    return new LineSectionEdge2(lineSection, line);
  }
}
