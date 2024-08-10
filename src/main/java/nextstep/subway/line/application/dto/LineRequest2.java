package nextstep.subway.line.application.dto;

import lombok.Builder;
import lombok.Getter;
import nextstep.subway.line.domain.Line2;

@Getter
public class LineRequest2 {
  private final String name;
  private final String color;
  private final Integer surcharge;
  private final Long upStationId;
  private final Long downStationId;
  private final Integer distance;
  private final Integer duration;

  @Builder
  public LineRequest2(
      String name,
      String color,
      Integer surcharge,
      Long upStationId,
      Long downStationId,
      Integer distance,
      Integer duration) {
    this.name = name;
    this.color = color;
    this.surcharge = surcharge;
    this.upStationId = upStationId;
    this.downStationId = downStationId;
    this.distance = distance;
    this.duration = duration;
  }

  public Line2 toLine() {
    return new Line2(name, color, surcharge);
  }

  public LineSectionRequest toLineSection() {
    return new LineSectionRequest(upStationId, downStationId, distance, duration);
  }
}
