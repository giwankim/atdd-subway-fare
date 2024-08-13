package nextstep.subway.line.application.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.station.application.dto.StationResponse;

@Getter
@EqualsAndHashCode
@ToString
public class LineResponse2 {
  private final Long id;
  private final String name;
  private final String color;
  private final Integer surcharge;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final Integer intervalTime;
  private final List<StationResponse> stations;

  public LineResponse2(
      Long id,
      String name,
      String color,
      Integer surcharge,
      LocalTime startTime,
      LocalTime endTime,
      Integer intervalTime,
      List<StationResponse> stations) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.surcharge = surcharge;
    this.startTime = startTime;
    this.endTime = endTime;
    this.intervalTime = intervalTime;
    this.stations = stations;
  }

  public static LineResponse2 from(Line2 line) {
    return new LineResponse2(
        line.getId(),
        line.getName(),
        line.getColor(),
        line.getSurcharge(),
        line.getStartTime(),
        line.getEndTime(),
        line.getIntervalTime(),
        StationResponse.listOf(line.getStations()));
  }

  public static List<LineResponse2> listOf(List<Line2> lines) {
    return lines.stream().map(LineResponse2::from).collect(Collectors.toList());
  }
}
