package nextstep.subway.path.domain;

import java.util.function.Function;
import nextstep.subway.line.domain.LineSection;

public enum PathType2 {
  DISTANCE(LineSection::getDistance),
  DURATION(LineSection::getDuration),
  ARRIVAL_TIME(LineSection::getDuration);

  private final Function<LineSection, Integer> edgeWeightFunction;

  PathType2(Function<LineSection, Integer> edgeWeightFunction) {
    this.edgeWeightFunction = edgeWeightFunction;
  }

  public int getEdgeWeight(LineSection lineSection) {
    return edgeWeightFunction.apply(lineSection);
  }
}
