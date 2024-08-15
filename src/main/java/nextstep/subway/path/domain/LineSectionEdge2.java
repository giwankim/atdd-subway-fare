package nextstep.subway.path.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.line.domain.LineSection2;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
public class LineSectionEdge2 extends DefaultWeightedEdge {
  private final LineSection2 lineSection;
  private final Line2 line;

  private LineSectionEdge2(LineSection2 lineSection, Line2 line) {
    this.lineSection = lineSection;
    this.line = line;
  }

  public static LineSectionEdge2 of(LineSection2 lineSection, Line2 line) {
    return new LineSectionEdge2(lineSection, line);
  }

  public LocalDateTime getArrivalTime(LocalDateTime departureTime) {
    return line.getArrivalTime(lineSection, departureTime);
  }
}
