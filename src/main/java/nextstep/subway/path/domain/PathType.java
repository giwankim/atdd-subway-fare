package nextstep.subway.path.domain;

import java.util.function.Function;
import nextstep.subway.line.domain.LineSection;

public enum PathType {
  DISTANCE(LineSection::getDistance),
  DURATION(LineSection::getDuration),
  ARRIVAL_TIME(LineSection::getDuration);

  private final Function<LineSection, Integer> edgeWeightFunction;

  PathType(Function<LineSection, Integer> edgeWeightFunction) {
    this.edgeWeightFunction = edgeWeightFunction;
  }

  public int getEdgeWeight(LineSection lineSection) {
    return edgeWeightFunction.apply(lineSection);
  }
}
