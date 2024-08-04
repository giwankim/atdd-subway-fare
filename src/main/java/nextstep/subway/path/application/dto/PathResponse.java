package nextstep.subway.path.application.dto;

import java.util.List;
import lombok.Getter;
import nextstep.subway.path.domain.Path;
import nextstep.subway.station.application.dto.StationResponse;

@Getter
public class PathResponse {
  private final List<StationResponse> stations;
  private final long distance;
  private final long duration;
  private final long fare;

  private PathResponse(List<StationResponse> stations, long distance, long duration, long fare) {
    this.stations = stations;
    this.distance = distance;
    this.duration = duration;
    this.fare = fare;
  }

  public static PathResponse of(Path path, long fare) {
    return new PathResponse(
        StationResponse.listOf(path.getStations()),
        path.getTotalDistance(),
        path.getTotalDuration(),
        fare);
  }
}
