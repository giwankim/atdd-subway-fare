package nextstep.subway.path.application;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.LoginMember;
import nextstep.member.application.MemberService;
import nextstep.member.domain.Member;
import nextstep.subway.fare.application.FareCalculator2;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse2;
import nextstep.subway.path.domain.Path2;
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
  private final FareCalculator2 fareCalculator2;

  public PathResponse2 findPath(PathRequest request, LoginMember loginMember) {
    Member member = memberService.findMemberByEmail(loginMember.getEmail());

    Station source = stationReader.readById(request.getSource());
    Station target = stationReader.readById(request.getTarget());

    SubwayGraph2 graph = graphService.loadGraph(request.getType());
    Path2 path = graph.getShortestPath(source, target);

    long fare = fareCalculator2.calculateFare(path, member);

    return PathResponse2.of(path, fare);
  }
}
