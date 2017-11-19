package webspotify.models.media;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Cardinals
 */
@Entity
@Table(name = "playlists")
public class Playlist extends SongCollection {

  @Column(name = "Description", nullable = false)
  private String description;
  @Column(name = "collaborative", nullable = false)
  private Boolean collaborative;
  @Column(name = "followerCount", nullable = false)
  private Integer followerCount;

  public Playlist() {
    super();
    this.followerCount = 0;
  }

  public void incrementFollowerCount() {
    this.followerCount++;
  }

  public void decrementFollowerCount() {
    this.followerCount--;
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

  public Integer getFollowerCount() {
    return followerCount;
  }

  public void setFollowerCount(Integer followerCount) {
    this.followerCount = followerCount;
  }

}
