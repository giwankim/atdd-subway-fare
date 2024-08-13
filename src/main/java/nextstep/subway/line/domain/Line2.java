package nextstep.subway.line.domain;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.subway.station.domain.Station;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Line2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String color;

  @Embedded private final LineSections2 lineSections = new LineSections2();

  private int surcharge;
  private LocalTime startTime;
  private LocalTime endTime;
  private int intervalTime;

  @Builder
  public Line2(
      Long id,
      String name,
      String color,
      int surcharge,
      LocalTime startTime,
      LocalTime endTime,
      int intervalTime,
      LineSections2 lineSections) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.surcharge = surcharge;
    this.startTime = startTime;
    this.endTime = endTime;
    this.intervalTime = intervalTime;
    this.lineSections.addAll(lineSections);
  }

  public Line2(
      String name,
      String color,
      int surcharge,
      LocalTime startTime,
      LocalTime endTime,
      int intervalTime,
      LineSection2... lineSections) {
    this(
        null,
        name,
        color,
        surcharge,
        startTime,
        endTime,
        intervalTime,
        new LineSections2(Arrays.asList(lineSections)));
  }

  public Line2(
      String name,
      String color,
      int surcharge,
      LocalTime startTime,
      LocalTime endTime,
      int intervalTime) {
    this(null, name, color, surcharge, startTime, endTime, intervalTime, new LineSections2());
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void changeColor(String color) {
    this.color = color;
  }

  public void addLineSection(LineSection2 lineSection) {
    lineSections.add(lineSection);
  }

  public List<Station> getStations() {
    return lineSections.getStations();
  }

  public void remove(Station station) {
    lineSections.remove(station);
  }
}
