package nextstep.subway.line.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.subway.station.domain.Station;
import org.hibernate.proxy.HibernateProxy;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Line {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String color;

  @Embedded private final LineSections lineSections = new LineSections();

  private int surcharge;
  private LocalTime startTime;
  private LocalTime endTime;
  private int intervalTime;

  @Builder
  public Line(
      Long id,
      String name,
      String color,
      int surcharge,
      LocalTime startTime,
      LocalTime endTime,
      int intervalTime,
      LineSections lineSections) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.surcharge = surcharge;
    this.startTime = startTime;
    this.endTime = endTime;
    this.intervalTime = intervalTime;
    this.lineSections.addAll(lineSections);
  }

  public Line(
      String name,
      String color,
      int surcharge,
      LocalTime startTime,
      LocalTime endTime,
      int intervalTime,
      LineSection... lineSections) {
    this(
        null,
        name,
        color,
        surcharge,
        startTime,
        endTime,
        intervalTime,
        new LineSections(Arrays.asList(lineSections)));
  }

  public Line(
      String name,
      String color,
      int surcharge,
      LocalTime startTime,
      LocalTime endTime,
      int intervalTime) {
    this(null, name, color, surcharge, startTime, endTime, intervalTime, new LineSections());
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void changeColor(String color) {
    this.color = color;
  }

  public void addLineSection(LineSection lineSection) {
    lineSections.add(lineSection);
  }

  public List<Station> getStations() {
    return lineSections.getStations();
  }

  public void remove(Station station) {
    lineSections.remove(station);
  }

  public LocalDateTime getArrivalTime(LineSection section, LocalDateTime now) {
    LocalDate today = now.toLocalDate();

    long timeTo = lineSections.getTimeTo(section);

    LocalDateTime departureTime = startTime.atDate(today).plusMinutes(timeTo);
    while (departureTime.isBefore(now)) {
      departureTime = getNextDepartureTime(departureTime, timeTo, today);
    }
    return departureTime.plusMinutes(section.getDuration());
  }

  private LocalDateTime getNextDepartureTime(LocalDateTime now, long timeTo, LocalDate today) {
    now = now.plusMinutes(intervalTime);
    if (isLastTrain(now, timeTo)) {
      now = startTime.atDate(today.plusDays(1));
      now = now.plusMinutes(timeTo);
    }
    return now;
  }

  private boolean isLastTrain(LocalDateTime departureTime, long timeToSection) {
    return departureTime.minusMinutes(timeToSection).toLocalTime().isAfter(endTime);
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Line line = (Line) o;
    return getId() != null && Objects.equals(getId(), line.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
