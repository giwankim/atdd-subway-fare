package nextstep.subway.line.domain;

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

  @Embedded private final LineSections lineSections = new LineSections();

  private int surcharge;

  @Builder
  public Line2(Long id, String name, String color, int surcharge, LineSections lineSections) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.surcharge = surcharge;
    this.lineSections.addAll(lineSections);
  }

  public Line2(String name, String color, int surcharge, LineSection... lineSections) {
    this(null, name, color, surcharge, new LineSections(Arrays.asList(lineSections)));
  }

  public Line2(String name, String color, int surcharge) {
    this(null, name, color, surcharge, new LineSections());
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
}
