package nextstep;

import nextstep.favorite.domain.Favorite;
import nextstep.favorite.domain.Favorite.FavoriteBuilder;
import nextstep.member.domain.Member;
import nextstep.member.domain.Member.MemberBuilder;
import nextstep.subway.line.domain.*;
import nextstep.subway.line.domain.Line.LineBuilder;
import nextstep.subway.line.domain.Line2.Line2Builder;
import nextstep.subway.station.domain.Station;

@SuppressWarnings("NonAsciiCharacters")
public class Fixtures {
  private Fixtures() {}

  public static Station 강남역() {
    return Station.builder().id(1L).name("강남역").build();
  }

  public static Station 역삼역() {
    return Station.builder().id(2L).name("역삼역").build();
  }

  public static Station 선릉역() {
    return Station.builder().id(3L).name("선릉역").build();
  }

  public static Station 판교역() {
    return Station.builder().id(4L).name("판교역").build();
  }

  public static Station 교대역() {
    return Station.builder().id(5L).name("교대역").build();
  }

  public static Station 남부터미널역() {
    return Station.builder().id(6L).name("남부터미널역").build();
  }

  public static Station 양재역() {
    return Station.builder().id(7L).name("양재역").build();
  }

  public static LineBuilder aLine() {
    return Line.builder().id(1L).name("2호선").color("bg-green-600").lineSections(new LineSections());
  }

  public static Line2Builder aLine2() {
    return Line2.builder()
        .id(1L)
        .name("2호선")
        .color("bg-green-600")
        .surcharge(0)
        .lineSections(new LineSections());
  }

  public static Line 이호선() {
    return Line.builder()
        .id(1L)
        .name("2호선")
        .color("bg-green-600")
        .lineSections(new LineSections(강남_역삼_구간()))
        .build();
  }

  public static Line 신분당선() {
    return Line.builder()
        .id(2L)
        .name("신분당선")
        .color("bg-red-600")
        .lineSections(new LineSections(강남_판교_구간()))
        .build();
  }

  public static Line 삼호선() {
    return Line.builder()
        .id(3L)
        .name("삼호선")
        .color("bg-orange-600")
        .lineSections(new LineSections(교대_남부터미널_구간(), 남부터미널_양재_구간()))
        .build();
  }

  public static Line2 이호선2() {
    return Line2.builder()
        .id(1L)
        .name("2호선")
        .color("bg-green-600")
        .lineSections(new LineSections(강남_역삼_구간()))
        .surcharge(0)
        .build();
  }

  public static Line2 신분당선2() {
    return Line2.builder()
        .id(2L)
        .name("신분당선")
        .color("bg-red-600")
        .lineSections(new LineSections(강남_판교_구간()))
        .surcharge(900)
        .build();
  }

  public static Line2 삼호선2() {
    return Line2.builder()
        .id(3L)
        .name("삼호선")
        .color("bg-orange-600")
        .lineSections(new LineSections(교대_남부터미널_구간(), 남부터미널_양재_구간()))
        .surcharge(0)
        .build();
  }

  public static LineSection 강남_역삼_구간() {
    return LineSection.builder()
        .upStation(강남역())
        .downStation(역삼역())
        .distance(10)
        .distance(1)
        .build();
  }

  public static LineSection 역삼_선릉_구간() {
    return LineSection.builder()
        .upStation(역삼역())
        .downStation(선릉역())
        .distance(20)
        .duration(2)
        .build();
  }

  public static LineSection 강남_판교_구간() {
    return LineSection.builder()
        .upStation(강남역())
        .downStation(판교역())
        .distance(20)
        .duration(2)
        .build();
  }

  public static LineSection 교대_강남_구간() {
    return LineSection.builder()
        .upStation(교대역())
        .downStation(강남역())
        .distance(10)
        .duration(2)
        .build();
  }

  public static LineSection 강남_양재_구간() {
    return LineSection.builder()
        .upStation(강남역())
        .downStation(양재역())
        .distance(10)
        .duration(3)
        .build();
  }

  public static LineSection 교대_남부터미널_구간() {
    return LineSection.builder()
        .upStation(교대역())
        .downStation(남부터미널역())
        .distance(2)
        .duration(10)
        .build();
  }

  public static LineSection 남부터미널_양재_구간() {
    return LineSection.builder()
        .upStation(남부터미널역())
        .downStation(양재역())
        .distance(3)
        .duration(10)
        .build();
  }

  public static MemberBuilder aMember() {
    return Member.builder().id(1L).email("user@example.com").password("password").age(21);
  }

  public static FavoriteBuilder aFavorite() {
    return Favorite.builder()
        .id(1L)
        .sourceStationId(교대역().getId())
        .targetStationId(양재역().getId())
        .memberId(aMember().build().getId());
  }
}
