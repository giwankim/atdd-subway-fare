Feature: 지하철 경로 조회 기능

  /**
  * 교대역    --- *2호선* ---   강남역
  * |                        |
  * *3호선*                   *신분당선*
  * |                        |
  * 남부터미널역  --- *3호선* ---   양재역
  */

  Background: 지하철 노선도 준비
    Given 지하철역들을 생성하고
      | name   |
      | 교대역    |
      | 강남역    |
      | 남부터미널역 |
      | 양재역    |
    And 노선들을 생성하고
      | name | color  | upStation | downStation | distance | duration | surcharge |
      | 2호선  | green  | 교대역       | 강남역         | 20       | 2        | 0         |
      | 신분당선 | red    | 강남역       | 양재역         | 30       | 3        | 0         |
      | 3호선  | orange | 교대역       | 남부터미널역      | 2        | 10       | 0         |
    And 구간들을 등록하고
      | line | upStation | downStation | distance | duration |
      | 3호선  | 남부터미널역    | 양재역         | 3        | 10       |
    And 사용자들을 생셩하고
      | email             | password | age |
      | child@example.com | secret   | 6   |
      | youth@example.com | secret   | 13  |
      | adult@example.com | secret   | 19  |

  Scenario: 두 역의 최단 거리 경로를 조회
    Given "adult@example.com"과 "secret"으로 로그인을 하고
    When "교대역"에서 "양재역"까지 최단 거리 경로를 조회하면
    Then "교대역,남부터미널역,양재역" 경로가 조회된다
    And 총 거리는 5km이며 총 소요 시간은 20분이다
    And 이용 요금은 1250원이다

  Scenario: 두 역의 최소 시간 경로를 조회
    Given "adult@example.com"과 "secret"으로 로그인을 하고
    When "교대역"에서 "양재역"까지 최소 시간 경로를 조회하면
    Then "교대역,강남역,양재역" 경로가 조회된다
    And 총 거리는 50km이며 총 소요 시간은 5분이다
    And 이용 요금은 2050원이다
