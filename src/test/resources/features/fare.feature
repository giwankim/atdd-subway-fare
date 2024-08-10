Feature: 요급 정책 기능

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
    And 노선들을 생성하고2
      | name | color  | upStation | downStation | distance | duration | surcharge |
      | 2호선  | green  | 교대역       | 강남역         | 20       | 2        | 0         |
      | 신분당선 | red    | 강남역       | 양재역         | 30       | 3        | 900       |
      | 3호선  | orange | 교대역       | 남부터미널역      | 2        | 10       | 0         |
    And 구간들을 등록하고
      | line | upStation | downStation | distance | duration |
      | 3호선  | 남부터미널역    | 양재역         | 3        | 10       |
    And 사용자들을 생셩하고
      | email             | password | age |
      | child@example.com | secret   | 6   |
      | youth@example.com | secret   | 13  |
      | adult@example.com | secret   | 19  |

  Rule: 사용자의 연령별 요금 할인

    Scenario: 청소년 운임은 350원을 공제한 금액의 20% 할인
      Given "youth@example.com"과 "secret"으로 로그인을 하고
      When "교대역"에서 "양재역"까지 최소 시간 경로를 조회하면
      Then 총 거리는 50km이며 총 소요 시간은 5분이다
      And 이용 요금은 1710원이다

    Scenario: 어린이 운임은 350원을 공제한 금액의 50% 할인
      Given "child@example.com"과 "secret"으로 로그인을 하고
      When "교대역"에서 "양재역"까지 최소 시간 경로를 조회하면
      Then 총 거리는 50km이며 총 소요 시간은 5분이다
      And 이용 요금은 1200원이다

  Rule: 노선별 추가 요금을 부가

    Scenario: 추가 요금이 있는 노선을 이용 할 경우 측정된 요금에 추가
      Given 노선별 추가 요금을 등록하고
        | line | surcharge |
        | 신분당선 | 900       |
      And "adult@example.com"과 "secret"으로 로그인을 하고
      When "교대역"에서 "양재역"까지 최소 시간 경로를 조회하면
      Then "교대역,강남역,양재역" 경로가 조회된다
      And 이용 요금은 2950원이다

    Scenario: 경로 중 추가요금이 있는 노선을 환승 하여 이용 할 경우 가장 높은 금액의 추가 요금만 적용
      Given 노선별 추가 요금을 등록하고
        | line | surcharge |
        | 신분당선 | 900       |
        | 2호선  | 1100      |
      And "adult@example.com"과 "secret"으로 로그인을 하고
      When "교대역"에서 "양재역"까지 최소 시간 경로를 조회하면
      Then "교대역,강남역,양재역" 경로가 조회된다
      And 이용 요금은 3150원이다
