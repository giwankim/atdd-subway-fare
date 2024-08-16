package nextstep.subway.unit.path.ui;

import static nextstep.Fixtures.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import nextstep.auth.application.JwtTokenProvider;
import nextstep.auth.domain.LoginMember;
import nextstep.member.domain.Member;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineSection;
import nextstep.subway.path.application.PathService2;
import nextstep.subway.path.application.dto.PathRequest2;
import nextstep.subway.path.application.dto.PathResponse2;
import nextstep.subway.path.domain.LineSectionEdge2;
import nextstep.subway.path.domain.Path2;
import nextstep.subway.path.domain.PathType2;
import nextstep.subway.path.ui.PathController2;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("경로 조회 컨트롤러 단위 테스트")
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(controllers = PathController2.class)
@Import({JwtTokenProvider.class})
class PathControllerTest2 {
  @Autowired private MockMvc mockMvc;
  @Autowired private JwtTokenProvider jwtTokenProvider;
  @MockBean private PathService2 pathService;

  private String acccessToken;

  @BeforeEach
  void setUp() {
    Member member = aMember().build();
    acccessToken = jwtTokenProvider.createToken(member.getEmail());
  }

  @DisplayName("경로를 조회 요청에 응답한다.")
  @Test
  void findPath() throws Exception {
    Station 강남역 = 강남역();
    Station 양재역 = 양재역();
    Line 신분당선 = 신분당선();
    LineSectionEdge2 edge = LineSectionEdge2.of(LineSection.of(강남역, 양재역, 10, 3), 신분당선);
    Path2 path = Path2.of(List.of(강남역, 양재역), Collections.singletonList(edge));
    given(pathService.findPath(any(PathRequest2.class), any(LoginMember.class)))
        .willReturn(PathResponse2.of(path, 1250L));

    mockMvc
        .perform(
            get("/new/paths")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + acccessToken)
                .param("source", String.valueOf(강남역.getId()))
                .param("target", String.valueOf(양재역.getId()))
                .param("type", PathType2.DISTANCE.name()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stations", hasSize(2)))
        .andExpect(jsonPath("$.stations[0].id").value(강남역.getId()))
        .andExpect(jsonPath("$.stations[0].name").value(강남역.getName()))
        .andExpect(jsonPath("$.stations[1].id").value(양재역.getId()))
        .andExpect(jsonPath("$.stations[1].name").value(양재역.getName()))
        .andExpect(jsonPath("$.distance").value(10))
        .andExpect(jsonPath("$.duration").value(3))
        .andExpect(jsonPath("$.fare").value(1250L));
  }

  @DisplayName("가장 빠른 도착 시간 경로를 조회한다.")
  @Test
  void findPathArrivalTime() throws Exception {
    Station 강남역 = 강남역();
    Station 양재역 = 양재역();
    Line 신분당선 = 신분당선();
    LineSectionEdge2 edge = LineSectionEdge2.of(LineSection.of(강남역, 양재역, 10, 3), 신분당선);
    Path2 path = Path2.of(List.of(강남역, 양재역), Collections.singletonList(edge));
    given(pathService.findPath(any(PathRequest2.class), any(LoginMember.class)))
        .willReturn(PathResponse2.of(path, 1250L, LocalDateTime.of(2024, 8, 12, 10, 3)));

    mockMvc
        .perform(
            get("/new/paths")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + acccessToken)
                .param("source", String.valueOf(강남역.getId()))
                .param("target", String.valueOf(양재역.getId()))
                .param("type", PathType2.ARRIVAL_TIME.name())
                .param("time", "202408121000"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stations", hasSize(2)))
        .andExpect(jsonPath("$.stations[0].id").value(강남역.getId()))
        .andExpect(jsonPath("$.stations[0].name").value(강남역.getName()))
        .andExpect(jsonPath("$.stations[1].id").value(양재역.getId()))
        .andExpect(jsonPath("$.stations[1].name").value(양재역.getName()))
        .andExpect(jsonPath("$.distance").value(10))
        .andExpect(jsonPath("$.duration").value(3))
        .andExpect(jsonPath("$.fare").value(1250L))
        .andExpect(jsonPath("$.arrivalTime").value("202408121003"));
  }
}
