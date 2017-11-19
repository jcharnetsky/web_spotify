package webspotify.responses;

import webspotify.models.users.Artist;
import webspotify.models.users.User;

/**
 * @author Cardinals
 */
public class UserInfoResponse {

  private Integer id;
  private String name;
  private Boolean isPremium;
  private Boolean isArtist;
  private Boolean isAdmin;

  public UserInfoResponse(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.isPremium = user.getIsPremium();
    this.isArtist = user instanceof Artist;
    this.isAdmin = false;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
