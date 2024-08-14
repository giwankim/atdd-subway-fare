package nextstep.subway.path.application;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.member.application.MemberService;
import nextstep.member.domain.Member;
import nextstep.subway.fare.application.FareCalculator;
import nextstep.subway.path.application.dto.PathRequest2;
import nextstep.subway.path.application.dto.PathResponse2;
import nextstep.subway.path.domain.Path;
import nextstep.subway.path.domain.SubwayGraph;
import nextstep.subway.station.application.StationReader;
import nextstep.subway.station.domain.Station;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathService2 {
  private final GraphService graphService;
  private final StationReader stationReader;
  private final MemberService memberService;
  private final FareCalculator fareCalculator;

  public PathResponse2 findPath(PathRequest2 request, LoginMember loginMember) {
    Member member = memberService.findMemberByEmail(loginMember.getEmail());

    Station source = stationReader.readById(request.getSource());
    Station target = stationReader.readById(request.getTarget());

    SubwayGraph graph = graphService.loadGraph(request.getType());

    Path path = graph.getShortestPath(source, target);

    long fare = fareCalculator.calculateFare(path, member);

    return PathResponse2.of(path, fare);
  }
}
