package nextstep.subway.acceptance.line;

import static nextstep.Fixtures.*;
import static nextstep.subway.acceptance.line.steps.LineAcceptanceSteps2.*;

import java.util.Arrays;
import java.util.Collections;
import nextstep.subway.line.domain.Line2;
import nextstep.subway.station.domain.StationRepository;
import nextstep.support.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("지하철 노선 관리 기능 인수 테스트")
class LineAcceptanceTest2 extends AcceptanceTest {
  @Autowired private StationRepository stationRepository;

  /** Given 지하철역 생성을 하고 */
  @Override
  @BeforeEach
  protected void setUp() {
    super.setUp();
    stationRepository.save(강남역());
    stationRepository.save(역삼역());
    stationRepository.save(선릉역());
    stationRepository.save(판교역());
  }

  /** Given 새로운 지하철 노선 정보를 입력하고, When 관리자가 노선을 생성하면, Then 해당 노선이 생성되고 노선 목록에 포함된다. */
  @DisplayName("지하철 노선을 생성한다.")
  @Test
  void createLine() {
    var response = 지하철_노선_생성_요청2(이호선2());

    지하철_노선_생성됨2(response);
    지하철_노선_목록에_포함됨2(지하철_노선_목록_조회_요청2(), Collections.singletonList(response));
  }

  /** Given 여러 개의 지하철 노선이 등록되어 있고, When 관리자가 지하철 노선 목록을 조회하면, Then 모든 지하철 노선 목록이 반환된다. */
  @DisplayName("지하철 노선 목록을 조회한다.")
  @Test
  void showLines() {
    var 이호선_생성_응답 = 지하철_노선_생성_요청2(이호선2());
    var 신분당선_생성_응답 = 지하철_노선_생성_요청2(신분당선2());

    var response = 지하철_노선_목록_조회_요청2();

    지하철_노선_목록에_포함됨2(response, Arrays.asList(이호선_생성_응답, 신분당선_생성_응답));
  }

  /** Given 특정 지하철 노선이 등록되어 있고, When 관리자가 해당 노선을 조회하면, Then 해당 노선의 정보가 반환된다. */
  @DisplayName("지하철 노선을 조회한다.")
  @Test
  void showLine() {
    Line2 이호선 = 이호선2();
    var 노선_생성_응답 = 지하철_노선_생성_요청2(이호선);
    String uri = 노선_생성_응답.header(HttpHeaders.LOCATION);

    var response = 지하철_노선_조회_요청2(uri);

    지하철_노선_조회됨2(response, 이호선);
  }

  /** Given 특정 지하철 노선이 등록되어 있고, When 관리자가 해당 노선을 수정하면, Then 해당 노선의 정보가 수정된다. */
  @DisplayName("지하철 노선을 수정한다.")
  @Test
  void updateLine() {
    String newName = "다른분당선";
    String newColor = "bg-orange-600";
    var 노선_생성_응답 = 지하철_노선_생성_요청2(신분당선2());
    String uri = 노선_생성_응답.header(HttpHeaders.LOCATION);

    지하철_노선_수정_요청2(uri, newName, newColor);

    지하철_노선_수정됨2(uri, newName, newColor);
  }

  /** Given 특정 지하철 노선이 등록되어 있고, When 관리자가 해당 노선을 삭제하면, Then 해당 노선이 삭제되고 노선 목록에서 제외된다. */
  @DisplayName("지하철 노선을 삭제한다.")
  @Test
  void deleteLine() {
    var 노선_생성_응답 = 지하철_노선_생성_요청2(이호선2());
    String uri = 노선_생성_응답.header(HttpHeaders.LOCATION);

    var response = 지하철_삭제_요청2(uri);

    지하철_노선_삭제됨2(uri, response);
  }
}
