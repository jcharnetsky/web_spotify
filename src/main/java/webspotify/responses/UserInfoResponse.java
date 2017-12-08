package webspotify.responses;

import java.util.ArrayList;
import java.util.List;
import webspotify.models.media.Playlist;
import webspotify.models.users.Administrator;
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
  private String imageLink;
  private Boolean isPremium;
  private Boolean isPublic;
  private Boolean isDeleted;
  private Boolean isArtist;
  private Boolean isAdmin;
  private Boolean isAdvertiser;
  private Integer followerCount;
  private List<BasicUserInfoResponse> followers;
  private ArtistProfileResponse artist;
  private List<PlaylistInfoResponse> ownedPlaylists;
  private boolean isFollowed;
  private boolean highAudio;

  public UserInfoResponse(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.birthday = user.getBirthdate();
    this.imageLink = user.getImage();
    this.isPremium = user.getIsPremium();
    this.isPublic = user.getIsPublic();
    this.isDeleted = user.getIsDeleted();
    this.highAudio = user.getHighQuality();
    if (user instanceof Artist) {
      this.isArtist = true;
      this.artist = new ArtistProfileResponse((Artist) user);
    }
    this.isAdmin = user instanceof Administrator;
    this.followers = new ArrayList<BasicUserInfoResponse>();
    for (User follower: user.getFollowing()){
      this.followers.add(new BasicUserInfoResponse(follower));
    }
    ownedPlaylists = new ArrayList<PlaylistInfoResponse>();
    for (Playlist playlist : user.getOwnedPlaylists()) {
      ownedPlaylists.add(new PlaylistInfoResponse(playlist));
    }
    this.followerCount = user.getFollowerCount();
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

  public Boolean getPublic() { 
    return isPublic; 
  }

  public void setPublic(Boolean isPublic) { 
    this.isPublic = isPublic; 
  }
  
  public Boolean getDeleted() {
    return isDeleted;
  }
  
  public void setDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
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

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public List<BasicUserInfoResponse> getFollowers() {
    return followers;
  }

  public void setFollowers(List<BasicUserInfoResponse> followers) {
    this.followers = followers;
  }

  public ArtistProfileResponse getArtist() {
    return artist;
  }

  public Integer getFollowerCount() {
    return followerCount;
  }

  public void setFollowerCount(Integer followerCount) {
    this.followerCount = followerCount;
  }

  public void setArtist(ArtistProfileResponse artist) {
    this.artist = artist;
  }

  public List<PlaylistInfoResponse> getOwnedPlaylists() {
    return ownedPlaylists;
  }

  public void setOwnedPlaylists(List<PlaylistInfoResponse> ownedPlaylists) {
    this.ownedPlaylists = ownedPlaylists;
  }

  public boolean isFollowed() {
    return isFollowed;
  }

  public void setFollowed(boolean followed) {
    isFollowed = followed;
  }

  public Boolean getAdvertiser() {
    return isAdvertiser;
  }

  public void setAdvertiser(Boolean advertiser) {
    isAdvertiser = advertiser;
  }

  public boolean isHighAudio() {
    return highAudio;
  }

  public void setHighAudio(boolean highAudio) {
    this.highAudio = highAudio;
  }
}
