package nextstep.subway.line.application.dto;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import nextstep.subway.line.domain.Line;

@Getter
public class LineRequest {
  private final String name;
  private final String color;
  private final Integer surcharge;
  private final Long upStationId;
  private final Long downStationId;
  private final Integer distance;
  private final Integer duration;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final Integer intervalTime;

  @Builder
  public LineRequest(
      String name,
      String color,
      Integer surcharge,
      Long upStationId,
      Long downStationId,
      Integer distance,
      Integer duration,
      LocalTime startTime,
      LocalTime endTime,
      Integer intervalTime) {
    this.name = name;
    this.color = color;
    this.surcharge = surcharge;
    this.upStationId = upStationId;
    this.downStationId = downStationId;
    this.distance = distance;
    this.duration = duration;
    this.startTime = startTime;
    this.endTime = endTime;
    this.intervalTime = intervalTime;
  }

  public Line toLine() {
    return new Line(name, color, surcharge, startTime, endTime, intervalTime);
  }

  public LineSectionRequest toLineSection() {
    return new LineSectionRequest(upStationId, downStationId, distance, duration);
  }
}
