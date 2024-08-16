package nextstep.subway.unit.path.application;

import static nextstep.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import nextstep.auth.domain.LoginMember;
import nextstep.member.application.MemberService;
import nextstep.member.domain.Member;
import nextstep.subway.fare.application.FareCalculator2;
import nextstep.subway.line.domain.*;
import nextstep.subway.path.application.GraphService2;
import nextstep.subway.path.application.PathService2;
import nextstep.subway.path.application.dto.PathRequest2;
import nextstep.subway.path.application.dto.PathResponse2;
import nextstep.subway.path.domain.*;
import nextstep.subway.station.application.StationReader;
import nextstep.subway.station.application.dto.StationResponse;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
  @Mock private GraphService2 graphService;
  @Mock private StationReader stationReader;
  @Mock private MemberService memberService;
  @Mock private FareCalculator2 fareCalculator;
  @InjectMocks private PathService2 pathService;

  Station 교대역 = 교대역();
  Station 강남역 = 강남역();

  @DisplayName("최적 경로를 조회한다.")
  @ParameterizedTest
  @MethodSource
  void findPath(
      PathType2 type,
      List<StationResponse> expectedStations,
      int expectedDistance,
      int expectedDuration) {
    Member member = aMember().build();
    LoginMember loginMember = new LoginMember(member.getEmail());
    given(memberService.findMemberByEmail(member.getEmail())).willReturn(member);

    Station 남부터미널역 = 남부터미널역();
    Station 양재역 = 양재역();
    Line 이호선 =
        aLine().name("2호선").lineSections(new LineSections(LineSection.of(교대역, 강남역, 10, 2))).build();
    Line 삼호선 =
        aLine()
            .name("3호선")
            .lineSections(
                new LineSections(
                    LineSection.of(교대역, 남부터미널역, 2, 10), LineSection.of(남부터미널역, 양재역, 3, 10)))
            .build();
    Line 신분당선 =
        aLine()
            .name("신분당선")
            .lineSections(new LineSections(LineSection.of(강남역, 양재역, 10, 3)))
            .build();
    SubwayGraph2 graph = new SubwayGraph2(type);
    graph.addLine(이호선);
    graph.addLine(삼호선);
    graph.addLine(신분당선);

    given(stationReader.readById(교대역.getId())).willReturn(교대역);
    given(stationReader.readById(양재역.getId())).willReturn(양재역);
    given(graphService.loadGraph(type)).willReturn(graph);

    long fare = 1250L;
    given(fareCalculator.calculateFare(any(Path2.class), any(Member.class))).willReturn(fare);

    PathRequest2 request = PathRequest2.of(교대역.getId(), 양재역.getId(), type, null);
    PathResponse2 path = pathService.findPath(request, loginMember);

    verify(graphService, times(1)).loadGraph(type);
    verify(fareCalculator, times(1)).calculateFare(any(Path2.class), any(Member.class));

    assertThat(path.getStations()).containsExactlyElementsOf(expectedStations);
    assertThat(path.getDistance()).isEqualTo(expectedDistance);
    assertThat(path.getDuration()).isEqualTo(expectedDuration);
    assertThat(path.getFare()).isEqualTo(fare);
  }

  private static Stream<Arguments> findPath() {
    return Stream.of(
        Arguments.of(
            PathType2.DISTANCE,
            List.of(
                StationResponse.from(교대역()),
                StationResponse.from(남부터미널역()),
                StationResponse.from(양재역())),
            5,
            20),
        Arguments.of(
            PathType2.DURATION,
            List.of(
                StationResponse.from(교대역()),
                StationResponse.from(강남역()),
                StationResponse.from(양재역())),
            20,
            5));
  }

  @DisplayName("가장 빨리 도착하는 경로를 조회한다.")
  @Test
  void findPathByArriveTime() {
    PathType2 type = PathType2.ARRIVAL_TIME;

    Member member = aMember().build();
    LoginMember loginMember = new LoginMember(member.getEmail());
    given(memberService.findMemberByEmail(member.getEmail())).willReturn(member);

    Line 이호선 =
        aLine().name("2호선").lineSections(new LineSections(LineSection.of(교대역, 강남역, 10, 3))).build();
    SubwayGraph2 graph = new SubwayGraph2(type);
    graph.addLine(이호선);

    given(stationReader.readById(교대역.getId())).willReturn(교대역);
    given(stationReader.readById(강남역.getId())).willReturn(강남역);
    given(graphService.loadGraph(type)).willReturn(graph);

    long fare = 1250L;
    given(fareCalculator.calculateFare(any(Path2.class), any(Member.class))).willReturn(fare);

    PathRequest2 request =
        PathRequest2.of(교대역.getId(), 강남역.getId(), type, LocalDateTime.of(2024, 8, 12, 10, 0));

    PathResponse2 pathResponse = pathService.findPath(request, loginMember);

    assertThat(pathResponse.getArrivalTime()).isEqualTo(LocalDateTime.of(2024, 8, 12, 10, 3));
  }
}
