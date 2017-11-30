package webspotify.responses;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Cardinals
 */
public class SearchResponse implements Serializable {

  private Set<BasicSongResponse> songs;
  private Set<BasicUserInfoResponse> users;
  private Set<BasicCollectionResponse> albums;
  private Set<BasicCollectionResponse> playlists;

  public SearchResponse(Set<BasicSongResponse> songs, Set<BasicUserInfoResponse> users,
          Set<BasicCollectionResponse> albums, Set<BasicCollectionResponse> playlists) {
    this.playlists = playlists;
    this.albums = albums;
    this.songs = songs;
    this.users = users;
  }

  public Set<BasicSongResponse> getSongs() {
    return songs;
  }

  public void setSongs(Set<BasicSongResponse> songs) {
    this.songs = songs;
  }

  public Set<BasicUserInfoResponse> getUsers() {
    return users;
  }

  public void setUsers(Set<BasicUserInfoResponse> users) {
    this.users = users;
  }

  public Set<BasicCollectionResponse> getAlbums() {
    return albums;
  }

  public void setAlbums(Set<BasicCollectionResponse> albums) {
    this.albums = albums;
  }

  public Set<BasicCollectionResponse> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(Set<BasicCollectionResponse> playlists) {
    this.playlists = playlists;
  }

}
