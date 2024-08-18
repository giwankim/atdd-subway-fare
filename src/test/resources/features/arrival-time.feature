Feature: 가장 빠른 도착 경로 조회

  Rule: 같은 노선
    Background: 지하철 노선도 준비
      Given 지하철역들을 생성하고
        | name |
        | 교대역  |
        | 강남역  |
        | 역삼역  |
        | 선릉역  |
      And 노선들을 생성하고
        | name | color | upStation | downStation | distance | duration | surcharge | startTime | endTime | intervalTime |
        | 2호선  | green | 교대역       | 강남역         | 10       | 3        | 0         | 05:00     | 23:00   | 10           |
      And 구간들을 등록하고
        | line | upStation | downStation | distance | duration |
        | 2호선  | 강남역       | 역삼역         | 15       | 4        |
        | 2호선  | 역삼역       | 선릉역         | 10       | 3        |
      And 사용자들을 생셩하고
        | email             | password | age |
        | adult@example.com | secret   | 19  |
      And "adult@example.com"과 "secret"으로 로그인을 하고

    Scenario: 두 역의 가장 빠른 경로를 조회
      When "교대역"에서 "선릉역"까지 "202408121000"에 출발하는 가장 빠른 경로를 조회하면
      Then "교대역,강남역,역삼역,선릉역" 경로가 조회된다
      And 도착 시간은 "202408121010"이다

    Scenario: 경로 조회 시 막차 시간을 넘기면 다음날 첫차 시간을 찾는다.
      When "교대역"에서 "선릉역"까지 "202408122330"에 출발하는 가장 빠른 경로를 조회하면
      Then "교대역,강남역,역삼역,선릉역" 경로가 조회된다
      And 도착 시간은 "202408130510"이다

  Rule: 다른 노선 (환승 포함)
    Background: 지하철 노선도 준비
      Given 지하철역들을 생성하고
        | name |
        | 교대역  |
        | 강남역  |
        | 역삼역  |
        | 선릉역  |
        | 양재역  |
      And 노선들을 생성하고
        | name | color | upStation | downStation | distance | duration | surcharge | startTime | endTime | intervalTime |
        | 2호선  | green | 교대역       | 강남역         | 10       | 3        | 0         | 05:00     | 23:00   | 10           |
        | 신분당선 | red   | 강남역       | 양재역         | 10       | 4        | 0         | 05:00     | 23:00   | 20           |
      And 구간들을 등록하고
        | line | upStation | downStation | distance | duration |
        | 2호선  | 강남역       | 역삼역         | 15       | 4        |
        | 2호선  | 역삼역       | 선릉역         | 10       | 3        |
      And 사용자들을 생셩하고
        | email             | password | age |
        | adult@example.com | secret   | 19  |
      And "adult@example.com"과 "secret"으로 로그인을 하고

      Scenario: 환승을 포함한 두 역 사이의 가장 빠른 경로를 조회
        When "교대역"에서 "양재역"까지 "202408121000"에 출발하는 가장 빠른 경로를 조회하면
        Then "교대역,강남역,양재역" 경로가 조회된다
        And 도착 시간은 "202408121024"이다
