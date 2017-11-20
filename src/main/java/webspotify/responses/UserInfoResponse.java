package webspotify.responses;

import java.util.ArrayList;
import java.util.List;
import webspotify.models.media.Playlist;
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
  private ArtistProfileResponse artist;
  private List<PlaylistInfoResponse> ownedPlaylists;

  public UserInfoResponse(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.isPremium = user.getIsPremium();
    this.isArtist = user instanceof Artist;
    if (user instanceof Artist) {
      this.artist = new ArtistProfileResponse((Artist) user);
    }
    ownedPlaylists = new ArrayList<PlaylistInfoResponse>();
    for (Playlist playlist : user.getOwnedPlaylists()) {
      ownedPlaylists.add(new PlaylistInfoResponse(playlist));
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

  public Boolean getIsPremium() {
    return isPremium;
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

  public ArtistProfileResponse getArtist() {
    return artist;
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

}
