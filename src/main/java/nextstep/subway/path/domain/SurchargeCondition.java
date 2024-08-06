package nextstep.subway.path.domain;

public interface SurchargeCondition {
  boolean isSatisfiedBy(Path path);
}
