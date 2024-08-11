package nextstep.subway.unit.line.application;

import nextstep.subway.line.application.LineService2;
import nextstep.subway.line.domain.LineRepository2;
import nextstep.subway.station.domain.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LineServiceTest {
  @Autowired private StationRepository stationRepository;
  @Autowired private LineRepository2 lineRepository;

  @Autowired private LineService2 lineService;

  @Test
  void addSection() {
    // given
    // stationRepository와 lineRepository를 활용하여 초기값 셋팅

    // when
    // lineService.addSection 호출

    // then
    // line.getSections 메서드를 통해 검증
  }
}
