package nextstep.subway.path.application.dto;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nextstep.subway.path.domain.PathType2;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@EqualsAndHashCode(of = {"source", "target", "type", "time"})
public class PathRequest2 {
  private final Long source;
  private final Long target;
  private final PathType2 type;

  @DateTimeFormat(pattern = "yyyyMMddHHmm")
  private final LocalDateTime time;

  private PathRequest2(Long source, Long target, PathType2 type, LocalDateTime time) {
    this.source = source;
    this.target = target;
    this.type = type;
    this.time = time;
  }

  public static PathRequest2 of(Long source, Long target, PathType2 type, LocalDateTime time) {
    return new PathRequest2(source, target, type, time);
  }
}
