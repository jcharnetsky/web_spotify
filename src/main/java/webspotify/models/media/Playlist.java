package webspotify.models.media;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import webspotify.models.users.User;

/**
 * @author Cardinals
 */
@Entity
@Table(name = "playlists")
@DiscriminatorColumn(name = "collectionType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "PLAYLIST")
public class Playlist extends SongCollection implements Serializable {

  @Column(name = "Description", nullable = false)
  private String description;
  @Column(name = "collaborative", nullable = false)
  private Boolean collaborative;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "usersFollowingPlaylists",
          joinColumns = @JoinColumn(name = "playlistID", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "userID", referencedColumnName = "id"))
  private Set<User> followers;

  public Playlist() {
    super();
    followers = new HashSet();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getCollaborative() {
    return collaborative;
  }

  public void setCollaborative(Boolean collaborative) {
    this.collaborative = collaborative;
  }

  public Set<User> getFollowers() {
    return followers;
  }

  public void setFollowers(Set<User> followers) {
    this.followers = followers;
  }

}
