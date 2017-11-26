package webspotify.responses;

import java.io.Serializable;
import webspotify.models.users.Artist;

/**
 * @author Cardinals
 */
public class ArtistResponse implements Serializable {

  private Integer id;
  private String stageName;
  private String about;
  private String imageLink;

  public ArtistResponse(Artist build) {
    this.id = build.getId();
    this.stageName = build.getStageName();
    this.about = build.getAbout();
    this.imageLink = build.getImage();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getStageName() {
    return stageName;
  }

  public void setStageName(String stageName) {
    this.stageName = stageName;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }
}