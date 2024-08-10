package nextstep.subway.unit.path.application;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import nextstep.subway.line.application.LineService2;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.path.application.GraphService2;
import nextstep.subway.path.domain.PathType;
import nextstep.subway.path.domain.SubwayGraph2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("경로 그래프 서비스 단위 테스트")
@SuppressWarnings("NonAsciiCharacters")
class GraphServiceTest2 {
  @Mock private LineService2 lineService;
  @InjectMocks private GraphService2 graphService;

  @DisplayName("노선 정보 바탕으로 그래프를 불러온다.")
  @ParameterizedTest
  @EnumSource(PathType.class)
  void loadGraph(PathType type) {
    Line2 이호선 = 이호선2();
    Line2 신분당선 = 신분당선2();
    given(lineService.findAllLines()).willReturn(List.of(이호선, 신분당선));

    SubwayGraph2 graph = graphService.loadGraph(type);

    verify(lineService, times(1)).findAllLines();

    SubwayGraph2 expectedGraph = new SubwayGraph2(type);
    expectedGraph.addLine(이호선);
    expectedGraph.addLine(신분당선);

    assertThat(graph.isSame(expectedGraph)).isTrue();
  }
}
