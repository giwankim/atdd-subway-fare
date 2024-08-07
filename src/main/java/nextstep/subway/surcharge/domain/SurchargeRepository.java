package nextstep.subway.surcharge.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurchargeRepository extends JpaRepository<Surcharge, Long> {
  Optional<Surcharge> findByLineId(Long lineId);
}
