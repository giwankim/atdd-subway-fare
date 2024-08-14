package nextstep.subway.path.domain;

import java.time.Duration;
import java.time.LocalDate;
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

  public LocalDateTime getArrivalTime(LocalDateTime time) {
    Duration interval = Duration.ofMinutes(line.getIntervalTime());
    LocalDateTime departureTime = getDepartureTime(time, interval);
    return departureTime.plusMinutes(lineSection.getDuration());
  }

  private LocalDateTime getDepartureTime(LocalDateTime time, Duration interval) {
    LocalDateTime departureTime = line.getStartTime().atDate(time.toLocalDate());
    while (departureTime.isBefore(time)) {
      departureTime = getNextDepartureTime(departureTime, interval);
    }
    return departureTime;
  }

  private LocalDateTime getNextDepartureTime(LocalDateTime departureTime, Duration interval) {
    LocalDateTime nextDepartureTime = departureTime.plus(interval);
    if (isAfterEndTime(nextDepartureTime)) {
      LocalDate tomorrow = nextDepartureTime.toLocalDate().plusDays(1);
      nextDepartureTime = line.getStartTime().atDate(tomorrow);
    }
    return nextDepartureTime;
  }

  private boolean isAfterEndTime(LocalDateTime departureTime) {
    return departureTime.toLocalTime().isAfter(line.getEndTime());
  }
}
