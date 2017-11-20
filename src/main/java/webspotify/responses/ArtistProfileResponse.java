package webspotify.responses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import webspotify.models.media.Album;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;

/**
 *
 * @author Cardinals
 */
class ArtistProfileResponse implements Serializable {

  private Integer id;
  private String stageName;
  private String about;
  
  private List<String> aliases;
  private List<AlbumInfoResponse> albums;
  private List<SongResponse> songs;

  public ArtistProfileResponse(Artist build) {
    this.id = build.getId();
    this.stageName = build.getStageName();
    this.about = build.getAbout();
    this.aliases = new ArrayList<String>();
    for(String alias : build.getAliases()) {
      this.aliases.add(alias);
    }
    this.albums = new ArrayList<AlbumInfoResponse>();
    for(Album album : build.getOwnedAlbums()) {
      this.albums.add(new AlbumInfoResponse(album));
    }
    this.songs = new ArrayList<SongResponse>();
    for(Song song : build.getOwnedSongs()) {
      this.songs.add(new SongResponse(song));
    }
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

  public List<String> getAliases() {
    return aliases;
  }

  public void setAliases(List<String> aliases) {
    this.aliases = aliases;
  }

  public List<AlbumInfoResponse> getAlbums() {
    return albums;
  }

  public void setAlbums(List<AlbumInfoResponse> albums) {
    this.albums = albums;
  }

  public List<SongResponse> getSongs() {
    return songs;
  }

  public void setSongs(List<SongResponse> songs) {
    this.songs = songs;
  }
  
}
