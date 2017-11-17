package webspotify.models.media;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import webspotify.interfaces.Viewable;
import webspotify.models.users.User;
import webspotify.types.GenreType;

/**
 * @author Cardinals
 */
@Entity
@Table(name = "songCollections")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "collectionType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "BASE")
public class SongCollection implements Serializable, Viewable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Column(name = "title", nullable = false)
  private String title;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "genre", nullable = false)
  private GenreType genre;
  @Column(name = "isBanned", nullable = false)
  private Boolean isBanned;
  @Column(name = "isPublic", nullable = false)
  private Boolean isPublic;
  @ManyToOne
  @JoinColumn(name = "ownerID", nullable = false)
  private User owner;
  @ManyToMany
  @JoinTable(
          name = "songsInCollection",
          joinColumns = @JoinColumn(name = "collectionID", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "songID", referencedColumnName = "id"))
  private Set<Song> songs;

  public SongCollection() {
    songs = new HashSet();
  }

  @Override
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

  public GenreType getGenre() {
    return genre;
  }

  public void setGenre(GenreType genre) {
    this.genre = genre;
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

  @Override
  public boolean isBanned() {
    return this.isBanned;
  }

  @Override
  public boolean isPublic() {
    return this.isPublic;
  }

  @Override
  public int ownedBy() {
    return -1;
  }

  @Override
  public void setBanned(boolean value) {
    this.setIsBanned(value);
  }

  @Override
  public void setPublic(boolean value) {
    this.setIsPublic(value);
  }

  public Set<Song> getSongs() {
    return songs;
  }

  public void setSongs(Set<Song> songs) {
    this.songs = songs;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

}
