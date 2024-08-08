package nextstep.subway.fare.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Surcharge {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long lineId;
  private long amount;

  @Builder
  public Surcharge(Long id, Long lineId, long amount) {
    this.id = id;
    this.lineId = lineId;
    this.amount = amount;
  }

  public Surcharge(Long lineId, long amount) {
    this(null, lineId, amount);
  }
}
