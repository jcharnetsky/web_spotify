package webspotify.responses;

/**
 * @author Cardinals
 */
public class UserInfoResponse {

  private String name;
  private Boolean isPremium;
  private Boolean isArtist;
  private Boolean isAdmin;

  public UserInfoResponse(String name, Boolean premium, Boolean artist, Boolean admin) {
    this.name = name;
    this.isPremium = premium;
    this.isArtist = artist;
    this.isAdmin = admin;
  }

  public Boolean getPremium() {
    return isPremium;
  }

  public void setPremium(Boolean premium) {
    isPremium = premium;
  }

  public Boolean getArtist() {
    return isArtist;
  }

  public void setArtist(Boolean artist) {
    isArtist = artist;
  }

  public Boolean getAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean admin) {
    isAdmin = admin;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
