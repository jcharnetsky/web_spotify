package webspotify.models.media;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import webspotify.models.users.Artist;
import webspotify.types.GenreType;

/**
 * @author Cardinals
 */
@Entity
@Table(name = "Songs")
public class Song implements Serializable {
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
  @ManyToOne
  @JoinColumn(name = "artistID", nullable = false)
  private Artist owner;
  @Lob
  @Column(name = "songAudio", length = 100000)
  private byte[] audio;

  public Song() {
    super();
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

  public byte[] getAudio() {
    return audio;
  }

  public void setAudio(byte[] audio) {
    this.audio = audio;
  }
}
