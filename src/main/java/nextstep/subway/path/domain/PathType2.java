package nextstep.subway.path.domain;

import java.util.function.Function;
import nextstep.subway.line.domain.LineSection2;

public enum PathType2 {
  DISTANCE(LineSection2::getDistance),
  DURATION(LineSection2::getDuration),
  ARRIVAL_TIME(LineSection2::getDuration);

  private final Function<LineSection2, Integer> edgeWeightFunction;

  PathType2(Function<LineSection2, Integer> edgeWeightFunction) {
    this.edgeWeightFunction = edgeWeightFunction;
  }

  public int getEdgeWeight(LineSection2 lineSection) {
    return edgeWeightFunction.apply(lineSection);
  }
}
