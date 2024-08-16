package nextstep.subway.path.application;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.member.application.MemberService;
import nextstep.member.domain.Member;
import nextstep.subway.fare.application.FareCalculator;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.domain.Path;
import nextstep.subway.path.domain.PathType;
import nextstep.subway.path.domain.Paths;
import nextstep.subway.path.domain.SubwayGraph;
import nextstep.subway.station.application.StationReader;
import nextstep.subway.station.domain.Station;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathService {
  private final GraphService graphService;
  private final StationReader stationReader;
  private final MemberService memberService;
  private final FareCalculator fareCalculator;

  public PathResponse findPath(PathRequest request, LoginMember loginMember) {
    Member member = memberService.findMemberByEmail(loginMember.getEmail());
    Station source = stationReader.readById(request.getSource());
    Station target = stationReader.readById(request.getTarget());

    SubwayGraph graph = graphService.loadGraph(request.getType());

    Path path = getPathByType(graph, source, target, request.getType(), request.getTime());

    long fare = fareCalculator.calculateFare(path, member);

    if (request.getType() == PathType.ARRIVAL_TIME) {
      LocalDateTime arrivalTime = path.getArrivalTime(request.getTime());
      return PathResponse.of(path, fare, arrivalTime);
    }
    return PathResponse.of(path, fare);
  }

  private static Path getPathByType(
      SubwayGraph graph,
      Station source,
      Station target,
      PathType type,
      LocalDateTime departureTime) {
    if (type == PathType.ARRIVAL_TIME) {
      Paths paths = graph.getAllPaths(source, target);
      return paths.getEarliestArrivalPath(departureTime);
    }
    return graph.getShortestPath(source, target);
  }
}
