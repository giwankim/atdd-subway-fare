package nextstep.subway.unit.path.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.line.domain.*;
import nextstep.subway.path.domain.*;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.*;

@DisplayName("지하철 그래프 단위 테스트")
@SuppressWarnings("NonAsciiCharacters")
class SubwayGraphTest2 {
  private final Station 교대역 = 교대역();
  private final Station 강남역 = 강남역();
  private final Station 남부터미널역 = 남부터미널역();
  private final Station 양재역 = 양재역();

  @DisplayName("노선을 추가한다.")
  @Test
  void addLine() {
    SubwayGraph2 graph = new SubwayGraph2(PathType2.DISTANCE);

    graph.addLine(이호선2());

    assertThat(graph.isSame(new SubwayGraph2(PathType2.DISTANCE))).isFalse();
  }

  @Nested
  @DisplayName("최단 경로 조회 단위 테스트")
  class ShortestPathTest {
    private SubwayGraph2 graph;

    @BeforeEach
    void setUp() {
      graph = new SubwayGraph2(PathType2.DISTANCE);
      Line2 이호선 = aLine2().lineSections(LineSections2.of(교대역, 강남역, 10, 2)).build();
      Line2 신분당선 = aLine2().lineSections(LineSections2.of(강남역, 양재역, 10, 3)).build();
      Line2 삼호선 =
          aLine2()
              .lineSections(
                  new LineSections2(
                      LineSection2.of(교대역, 남부터미널역, 2, 10), LineSection2.of(남부터미널역, 양재역, 3, 10)))
              .build();
      graph.addLine(이호선);
      graph.addLine(신분당선);
      graph.addLine(삼호선);
    }

    @DisplayName("최단 거리 경로를 조회한다.")
    @Test
    void getShortestDistancePath() {
      Path2 path = graph.getShortestPath(교대역, 양재역);
      assertThat(path.getTotalDistance()).isEqualTo(5);
      assertThat(path.getTotalDuration()).isEqualTo(20);
      assertThat(path.getStations()).containsExactly(교대역, 남부터미널역, 양재역);
    }

    @DisplayName("최단 시간 경로를 조회한다.")
    @Test
    void getShortestDurationPath() {
      graph = new SubwayGraph2(PathType2.DURATION);
      Line2 이호선 = aLine2().lineSections(LineSections2.of(교대역, 강남역, 10, 2)).build();
      Line2 신분당선 = aLine2().lineSections(LineSections2.of(강남역, 양재역, 10, 3)).build();
      Line2 삼호선 =
          aLine2()
              .lineSections(
                  new LineSections2(
                      LineSection2.of(교대역, 남부터미널역, 2, 10), LineSection2.of(남부터미널역, 양재역, 3, 10)))
              .build();
      graph.addLine(이호선);
      graph.addLine(신분당선);
      graph.addLine(삼호선);

      Path2 path = graph.getShortestPath(교대역, 양재역);

      assertThat(path.getTotalDistance()).isEqualTo(20);
      assertThat(path.getTotalDuration()).isEqualTo(5);
      assertThat(path.getStations()).containsExactly(교대역, 강남역, 양재역);
    }

    @DisplayName("출발역과 도착역이 같은 경우 역 하나만이 반환된다.")
    @Test
    void sourceAndTargetAreTheSame() {
      Path2 path = graph.getShortestPath(교대역, 교대역);
      assertThat(path.getTotalDistance()).isZero();
      assertThat(path.getStations()).containsExactly(교대역);
    }

    @DisplayName("출발역과 도착역이 연결이 되어 있지 않은 경우")
    @Test
    void sourceAndTargetDisconnected() {
      graph = new SubwayGraph2(PathType2.DURATION);
      Line2 이호선 = aLine2().lineSections(LineSections2.of(교대역, 강남역, 10, 2)).build();
      Line2 삼호선 = aLine2().lineSections(LineSections2.of(남부터미널역, 양재역, 3, 10)).build();
      graph.addLine(이호선);
      graph.addLine(삼호선);

      Path2 path = graph.getShortestPath(교대역, 양재역);

      assertThat(path.getTotalDistance()).isZero();
      assertThat(path.getStations()).isEmpty();
    }

    @DisplayName("출발역이나 도착역이 존재하지 않는 경우")
    @Test
    void sourceOrTargetNotExist() {
      Station 판교역 = 판교역();
      assertThatExceptionOfType(IllegalArgumentException.class)
          .isThrownBy(() -> graph.getShortestPath(교대역, 판교역));
    }
  }

  @DisplayName("모든 경로 조회 단위 테스트")
  @Nested
  class GetAllPathTest {
    private SubwayGraph2 graph;

    @BeforeEach
    void setUp() {
      graph = new SubwayGraph2(PathType2.ARRIVAL_TIME);
      Line2 이호선 =
          aLine2().id(1L).name("2호선").lineSections(LineSections2.of(교대역, 강남역, 10, 2)).build();
      Line2 신분당선 =
          aLine2().id(2L).name("신분당선").lineSections(LineSections2.of(강남역, 양재역, 10, 3)).build();
      Line2 삼호선 =
          aLine2()
              .id(3L)
              .name("3호선")
              .lineSections(
                  new LineSections2(
                      LineSection2.of(교대역, 남부터미널역, 2, 10), LineSection2.of(남부터미널역, 양재역, 3, 10)))
              .build();
      graph.addLine(이호선);
      graph.addLine(신분당선);
      graph.addLine(삼호선);
    }

    @DisplayName("모든 경로를 조회한다.")
    @Test
    void getAllPaths() {
      List<Path2> paths = graph.getAllPaths(교대역, 양재역);
      List<List<Station>> pathStations =
          paths.stream().map(Path2::getStations).collect(Collectors.toList());
      assertThat(paths).hasSize(2);
      assertThat(pathStations)
          .containsExactlyInAnyOrder(List.of(교대역, 강남역, 양재역), List.of(교대역, 남부터미널역, 양재역));
    }

    @DisplayName("출발역과 도착역이 같은 경우 빈 경로 목록이 반환된다.")
    @Test
    void sourceAndTargetAreTheSame() {
      List<Path2> paths = graph.getAllPaths(교대역, 교대역);
      System.out.println("paths = " + paths);
    }

    @DisplayName("출발역과 도착역이 연결이 되어 있지 않은 경우")
    @Test
    void sourceAndTargetDisconnected() {
      graph = new SubwayGraph2(PathType2.ARRIVAL_TIME);
      Line2 이호선 = aLine2().lineSections(LineSections2.of(교대역, 강남역, 10, 2)).build();
      Line2 삼호선 = aLine2().lineSections(LineSections2.of(남부터미널역, 양재역, 3, 10)).build();
      graph.addLine(이호선);
      graph.addLine(삼호선);

      List<Path2> paths = graph.getAllPaths(교대역, 양재역);

      assertThat(paths).isEmpty();
    }

    @DisplayName("출발역이나 도착역이 그래프에 없는 경우")
    @Test
    void sourceOrTargetNotInGraph() {
      Station 판교역 = 판교역();
      assertThatExceptionOfType(IllegalArgumentException.class)
          .isThrownBy(() -> graph.getAllPaths(교대역, 판교역));
    }
  }
}
