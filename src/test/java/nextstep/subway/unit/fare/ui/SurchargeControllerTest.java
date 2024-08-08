package nextstep.subway.unit.fare.ui;

import static nextstep.Fixtures.신분당선;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nextstep.auth.application.JwtTokenProvider;
import nextstep.subway.fare.application.SurchargeService;
import nextstep.subway.fare.application.dto.SurchargeRequest;
import nextstep.subway.fare.application.dto.SurchargeResponse;
import nextstep.subway.fare.domain.Surcharge;
import nextstep.subway.fare.ui.SurchargeController;
import nextstep.subway.line.domain.Line;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("추가 요금 컨트롤러 단위 테스트")
@WebMvcTest(controllers = SurchargeController.class)
@Import({JwtTokenProvider.class})
class SurchargeControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private SurchargeService surchargeService;

  @DisplayName("추가 요금을 생성한다.")
  @Test
  void create() throws Exception {
    long surcharge = 900L;
    Line line = 신분당선();
    SurchargeResponse response = SurchargeResponse.of(new Surcharge(line.getId(), surcharge), line);
    given(surchargeService.save(any(SurchargeRequest.class))).willReturn(response);

    mockMvc
        .perform(
            post("/surcharges")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lineId\": " + line.getId() + ", \"surcharge\": " + surcharge + "}"))
        .andExpect(status().isCreated());
  }
}
