# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

## 가장 빠른 도착 경로 조회 기능

### 제약 사항

가장 빠른 경로 조회 시 디폴트로 최대 `1000`개의 경로에 대한 도착 시간 계산을 수행합니다.

```java
public class SubwayGraph {
  public static final int MAX_PATH_COUNT = 1000;
  // 생략
}
```
