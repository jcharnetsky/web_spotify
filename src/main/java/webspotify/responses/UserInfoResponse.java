package webspotify.responses;

import webspotify.models.users.Artist;
import webspotify.models.users.User;

import java.util.Date;

/**
 * @author Cardinals
 */
public class UserInfoResponse {

  private Integer id;
  private String name;
  private String email;
  private Date birthday;
  private Boolean isPremium;
  private Boolean isArtist;
  private Boolean isAdmin;
  private ArtistResponse artist;

  public UserInfoResponse(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.isPremium = user.getIsPremium();
    this.isArtist = user instanceof Artist;
    if(user instanceof Artist){
      this.artist = new ArtistResponse((Artist)user);
    }
    this.isAdmin = false;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Boolean getIsPremium() {
    return isPremium;
  }

  public Boolean getPremium() {
    return isPremium;
  }

  public void setPremium(Boolean premium) {
    isPremium = premium;
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

  public void setIsPremium(Boolean isPremium) {
    this.isPremium = isPremium;
  }

  public Boolean getIsArtist() {
    return isArtist;
  }

  public void setIsArtist(Boolean isArtist) {
    this.isArtist = isArtist;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public ArtistResponse getArtist() {
    return artist;
  }

  public void setArtist(ArtistResponse artist) {
    this.artist = artist;
  }

}
