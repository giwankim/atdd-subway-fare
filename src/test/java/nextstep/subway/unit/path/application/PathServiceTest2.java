package nextstep.subway.unit.path.application;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.stream.Stream;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.line.domain.LineSections;
import nextstep.subway.path.application.GraphService2;
import nextstep.subway.path.application.PathService2;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.domain.*;
import nextstep.subway.station.application.StationReader;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayName("경로 조회 서비스 단위 테스트")
class PathServiceTest2 {
  @Mock GraphService2 graphService;
  @Mock StationReader stationReader;
  @InjectMocks PathService2 pathService;

  @DisplayName("최적 경로를 조회한다.")
  @ParameterizedTest
  @MethodSource
  void findPathDistance(
      PathType type, List<Station> expectedStations, int expectedDistance, int expectedDuration) {
    Station 교대역 = 교대역();
    Station 강남역 = 강남역();
    Station 남부터미널역 = 남부터미널역();
    Station 양재역 = 양재역();
    Line2 이호선 =
        aLine2()
            .name("2호선")
            .lineSections(new LineSections(LineSection.of(교대역, 강남역, 10, 2)))
            .build();
    Line2 삼호선 =
        aLine2()
            .name("3호선")
            .lineSections(
                new LineSections(
                    LineSection.of(교대역, 남부터미널역, 2, 10), LineSection.of(남부터미널역, 양재역, 3, 10)))
            .build();
    Line2 신분당선 =
        aLine2()
            .name("신분당선")
            .lineSections(new LineSections(LineSection.of(강남역, 양재역, 10, 3)))
            .build();
    SubwayGraph2 graph = new SubwayGraph2(type);
    graph.addLine(이호선);
    graph.addLine(삼호선);
    graph.addLine(신분당선);

    given(graphService.loadGraph(type)).willReturn(graph);
    given(stationReader.readById(교대역.getId())).willReturn(교대역);
    given(stationReader.readById(양재역.getId())).willReturn(양재역);

    Path2 path = pathService.findPath(PathRequest.of(교대역.getId(), 양재역.getId(), type));

    verify(graphService, times(1)).loadGraph(type);

    assertThat(path.getStations()).containsExactlyElementsOf(expectedStations);
    assertThat(path.getTotalDistance()).isEqualTo(expectedDistance);
    assertThat(path.getTotalDuration()).isEqualTo(expectedDuration);
  }

  private static Stream<Arguments> findPathDistance() {
    return Stream.of(
        Arguments.of(PathType.DISTANCE, List.of(교대역(), 남부터미널역(), 양재역()), 5, 20),
        Arguments.of(PathType.DURATION, List.of(교대역(), 강남역(), 양재역()), 20, 5));
  }
}
