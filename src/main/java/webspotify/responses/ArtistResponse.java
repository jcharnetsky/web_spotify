package webspotify.responses;

import java.io.Serializable;

import webspotify.models.users.Artist;

/**
 * @author Cardinals
 */
public class ArtistResponse implements Serializable {
  private String stageName;
  private String about;

  public ArtistResponse(Artist build) {
    this.stageName = build.getStageName();
    this.about = build.getAbout();
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
}
