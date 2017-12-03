package webspotify.models.media;

import java.io.Serializable;
import javax.persistence.*;
import webspotify.interfaces.Viewable;
import webspotify.models.users.Artist;
import webspotify.types.GenreType;

/**
 * @author Cardinals
 */
@Entity
@Table(name = "Songs")
public class Song implements Serializable, Viewable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID", nullable = false)
  private Integer id;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "trackLength", nullable = false)
  private Integer trackLength;
  @Column(name = "isBanned", nullable = false)
  private Boolean isBanned;
  @Column(name = "isPublic", nullable = false)
  private Boolean isPublic;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "genre", nullable = false)
  private GenreType genre;
  @Column(name = "TotalListens", nullable = false)
  private Integer totalListens;
  @Column(name = "MonthlyListens", nullable = false)
  private Integer monthlyListens;
  @Column(name = "hasAudio", nullable = false)
  private Boolean hasAudio;
  @Column(name = "lyrics", nullable = false)
  private String lyrics;
  @ManyToOne
  @JoinColumn(name = "artistID", nullable = false)
  private Artist owner;
  @ManyToOne
  @JoinColumn(name = "albumID", nullable = false)
  private Album album;

  public Song() {
    super();
    this.totalListens = 0;
    this.monthlyListens = 0;
    this.hasAudio = false;
  }

  public void incrementListens() {
    this.totalListens++;
    this.monthlyListens++;
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

  public Integer getTrackLength() {
    return trackLength;
  }

  public void setTrackLength(Integer trackLength) {
    this.trackLength = trackLength;
  }

  public Boolean getIsBanned() {
    return isBanned;
  }

  public void setIsBanned(Boolean isBanned) {
    this.isBanned = isBanned;
  }

  public Boolean getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(Boolean isPublic) {
    this.isPublic = isPublic;
  }

  public Artist getOwner() {
    return owner;
  }

  public void setOwner(Artist owner) {
    this.owner = owner;
  }

  public GenreType getGenre() {
    return genre;
  }

  public void setGenre(GenreType genre) {
    this.genre = genre;
  }

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }

  public Integer getTotalListens() {
    return totalListens;
  }

  public void setTotalListens(Integer totalListens) {
    this.totalListens = totalListens;
  }

  public Integer getMontlyListens() {
    return monthlyListens;
  }

  public void setMonthlyListeners(Integer monthlyListeners) {
    this.monthlyListens = monthlyListeners;
  }

  public Boolean getHasAudio() {
    return hasAudio;
  }

  public void setHasAudio(Boolean hasAudio) {
    this.hasAudio = hasAudio;
  }

  public Integer getMonthlyListens() {
    return monthlyListens;
  }

  public void setMonthlyListens(Integer monthlyListens) {
    this.monthlyListens = monthlyListens;
  }

  public String getLyrics() {
    return lyrics;
  }

  public void setLyrics(String lyrics) {
    this.lyrics = lyrics;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Song other = (Song) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isBanned() {
    return this.getIsBanned();
  }

  @Override
  public boolean isPublic() {
    return this.getIsPublic();
  }

  @Override
  public int ownedBy() {
    return this.getOwner().getId();
  }

  @Override
  public void setBanned(boolean value) {
    this.setIsBanned(value);
  }

  @Override
  public void setPublic(boolean value) {
    this.setIsPublic(value);
  }

}
