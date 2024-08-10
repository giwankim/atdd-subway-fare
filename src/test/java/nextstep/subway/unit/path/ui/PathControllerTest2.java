package nextstep.subway.unit.path.ui;

import static nextstep.Fixtures.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import nextstep.auth.application.JwtTokenProvider;
import nextstep.auth.domain.LoginMember;
import nextstep.member.domain.Member;
import nextstep.subway.path.application.PathService2;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse2;
import nextstep.subway.path.domain.Path2;
import nextstep.subway.path.domain.PathType;
import nextstep.subway.path.ui.PathController2;
import nextstep.subway.station.domain.Station;
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

  @Test
  @DisplayName("경로를 조회 요청에 응답한다.")
  void findPath() throws Exception {
    Member member = aMember().build();
    String accessToken = jwtTokenProvider.createToken(member.getEmail());

    Station 교대역 = 교대역();
    Station 양재역 = 양재역();
    Path2 path = Path2.of(List.of(교대역, 양재역), List.of(이호선2(), 신분당선2()), 5, 10);
    given(pathService.findPath(any(PathRequest.class), any(LoginMember.class)))
        .willReturn(PathResponse2.of(path, 1250L));

    mockMvc
        .perform(
            get("/new/paths")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .param("source", String.valueOf(교대역.getId()))
                .param("target", String.valueOf(양재역.getId()))
                .param("type", PathType.DISTANCE.name()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stations", hasSize(2)))
        .andExpect(jsonPath("$.stations[0].id").value(교대역.getId()))
        .andExpect(jsonPath("$.stations[0].name").value(교대역.getName()))
        .andExpect(jsonPath("$.stations[1].id").value(양재역.getId()))
        .andExpect(jsonPath("$.stations[1].name").value(양재역.getName()))
        .andExpect(jsonPath("$.distance").value(5))
        .andExpect(jsonPath("$.duration").value(10))
        .andExpect(jsonPath("$.fare").value(1250L));
  }
}
