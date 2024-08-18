package nextstep.subway.path.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.application.dto.StationResponse;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PathResponse {
  private final List<StationResponse> stations;
  private final long distance;
  private final long duration;
  private final long fare;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmm")
  private final LocalDateTime arrivalTime;

  private PathResponse(
      List<StationResponse> stations,
      long distance,
      long duration,
      long fare,
      LocalDateTime arrivalTime) {
    this.stations = stations;
    this.distance = distance;
    this.duration = duration;
    this.fare = fare;
    this.arrivalTime = arrivalTime;
  }

  private PathResponse(List<StationResponse> stations, long distance, long duration, long fare) {
    this(stations, distance, duration, fare, null);
  }

  public static PathResponse of(Path path, long fare) {
    return new PathResponse(
        StationResponse.listOf(path.getStations()),
        path.getTotalDistance(),
        path.getTotalDuration(),
        fare);
  }

  public static PathResponse of(Path path, long fare, LocalDateTime arrivalTime) {
    return new PathResponse(
        StationResponse.listOf(path.getStations()),
        path.getTotalDistance(),
        path.getTotalDuration(),
        fare,
        arrivalTime);
  }
}
