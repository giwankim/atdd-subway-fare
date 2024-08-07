package nextstep.subway.unit.path.domain;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.line.domain.LineSections;
import nextstep.subway.path.domain.*;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
    SubwayGraph2 graph = new SubwayGraph2(PathType.DISTANCE);

    graph.addLine(이호선());

    assertThat(graph.isSame(new SubwayGraph2(PathType.DISTANCE))).isFalse();
  }

  @Nested
  @DisplayName("최단 경로 조회 단위 테스트")
  class ShortestPathTest {
    private SubwayGraph2 graph;

    @BeforeEach
    void setUp() {
      graph = new SubwayGraph2(PathType.DISTANCE);
      Line 이호선 = aLine().lineSections(LineSections.of(교대역, 강남역, 10, 2)).build();
      Line 신분당선 = aLine().lineSections(LineSections.of(강남역, 양재역, 10, 3)).build();
      Line 삼호선 =
          aLine()
              .lineSections(
                  new LineSections(
                      LineSection.of(교대역, 남부터미널역, 2, 10), LineSection.of(남부터미널역, 양재역, 3, 10)))
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
      graph = new SubwayGraph2(PathType.DURATION);
      Line 이호선 = aLine().lineSections(LineSections.of(교대역, 강남역, 10, 2)).build();
      Line 신분당선 = aLine().lineSections(LineSections.of(강남역, 양재역, 10, 3)).build();
      Line 삼호선 =
          aLine()
              .lineSections(
                  new LineSections(
                      LineSection.of(교대역, 남부터미널역, 2, 10), LineSection.of(남부터미널역, 양재역, 3, 10)))
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
      graph = new SubwayGraph2(PathType.DURATION);
      Line 이호선 = aLine().lineSections(LineSections.of(교대역, 강남역, 10, 2)).build();
      Line 삼호선 = aLine().lineSections(LineSections.of(남부터미널역, 양재역, 3, 10)).build();
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
}
