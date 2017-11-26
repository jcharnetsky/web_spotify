package webspotify.models.media;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import webspotify.interfaces.Viewable;
import webspotify.models.users.User;
import webspotify.types.GenreType;

/**
 * @author Cardinals
 */
@MappedSuperclass
public abstract class SongCollection implements Serializable, Viewable {
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
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "songsInCollection",
          joinColumns = @JoinColumn(name = "collectionID", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "songID", referencedColumnName = "id"))
  private Set<Song> songs;
  protected SongCollection() {
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
  public String getImage() {
    return "../images/logo.png";
  }
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
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
    final SongCollection other = (SongCollection) obj;
    if (!this.id.equals(other.id) && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }
}