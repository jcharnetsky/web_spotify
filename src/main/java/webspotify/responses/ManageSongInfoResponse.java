package webspotify.responses;

import webspotify.models.media.Song;

public class ManageSongInfoResponse {

  private Integer id;
  private String title;
  private Integer monthListens;
  private Integer allListens;
  private Double monthRoyalties;
  private Double allRoyalties;

  public ManageSongInfoResponse(Song song) {
    this.id = song.getId();
    this.title = song.getTitle();
    this.monthListens = song.getMontlyListens();
    this.allListens = song.getTotalListens();
    this.monthRoyalties = 100.00;
    this.allRoyalties = 100.00;
  }

  public ManageSongInfoResponse(Song song, Double monthRoyalties, Double allRoyalties) {
    this(song);
    this.setAllRoyalties(allRoyalties);
    this.setMonthRoyalties(monthRoyalties);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getMonthListens() {
    return monthListens;
  }

  public void setMonthListens(Integer monthListens) {
    this.monthListens = monthListens;
  }

  public Integer getAllListens() {
    return allListens;
  }

  public void setAllListens(Integer allListens) {
    this.allListens = allListens;
  }

  public Double getMonthRoyalties() {
    return monthRoyalties;
  }

  public void setMonthRoyalties(Double monthRoyalties) {
    this.monthRoyalties = monthRoyalties;
  }

  public Double getAllRoyalties() {
    return allRoyalties;
  }

  public void setAllRoyalties(Double allRoyalties) {
    this.allRoyalties = allRoyalties;
  }
}
