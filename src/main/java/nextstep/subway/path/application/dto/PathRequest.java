package nextstep.subway.path.application.dto;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nextstep.subway.path.domain.PathType;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@EqualsAndHashCode(of = {"source", "target", "type", "time"})
public class PathRequest {
  private final Long source;
  private final Long target;
  private final PathType type;

  @DateTimeFormat(pattern = "yyyyMMddHHmm")
  private final LocalDateTime time;

  private PathRequest(Long source, Long target, PathType type, LocalDateTime time) {
    this.source = source;
    this.target = target;
    this.type = type;
    this.time = time;
  }

  public static PathRequest of(Long source, Long target, PathType type, LocalDateTime time) {
    return new PathRequest(source, target, type, time);
  }

  public static PathRequest of(Long source, Long target, PathType type) {
    return new PathRequest(source, target, type, null);
  }
}
