package nextstep.subway.path.application;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.member.application.MemberService;
import nextstep.member.domain.Member;
import nextstep.subway.fare.application.FareCalculator2;
import nextstep.subway.path.application.dto.PathRequest2;
import nextstep.subway.path.application.dto.PathResponse2;
import nextstep.subway.path.domain.Path2;
import nextstep.subway.path.domain.PathType2;
import nextstep.subway.path.domain.Paths;
import nextstep.subway.path.domain.SubwayGraph2;
import nextstep.subway.station.application.StationReader;
import nextstep.subway.station.domain.Station;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathService2 {
  private final GraphService2 graphService;
  private final StationReader stationReader;
  private final MemberService memberService;
  private final FareCalculator2 fareCalculator;

  public PathResponse2 findPath(PathRequest2 request, LoginMember loginMember) {
    Member member = memberService.findMemberByEmail(loginMember.getEmail());
    Station source = stationReader.readById(request.getSource());
    Station target = stationReader.readById(request.getTarget());

    SubwayGraph2 graph = graphService.loadGraph(request.getType());

    Path2 path = getPathByType(graph, source, target, request.getType(), request.getTime());

    long fare = fareCalculator.calculateFare(path, member);

    if (request.getType() == PathType2.ARRIVAL_TIME) {
      LocalDateTime arrivalTime = path.getArrivalTime(request.getTime());
      return PathResponse2.of(path, fare, arrivalTime);
    }
    return PathResponse2.of(path, fare);
  }

  private static Path2 getPathByType(
      SubwayGraph2 graph,
      Station source,
      Station target,
      PathType2 type,
      LocalDateTime departureTime) {
    if (type == PathType2.ARRIVAL_TIME) {
      Paths paths = graph.getAllPaths(source, target);
      return paths.getEarliestArrivalPath(departureTime);
    }
    return graph.getShortestPath(source, target);
  }
}
