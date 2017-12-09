package webspotify.responses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webspotify.models.media.Album;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.types.GenreType;

/**
 *
 * @author Cardinals
 */
public class AlbumInfoResponse {

  private Integer id;
  private String title;
  private String imageLink;
  private GenreType genre;
  private String ownerName;
  private Integer ownerId;
  private Integer songCount;
  private Integer songTrackLength;
  private List<SongResponse> songs;
  private boolean isPublic;
  private boolean isFollowed;
  private boolean isAlbum;

  public AlbumInfoResponse(Album album) {
    this.id = album.getId();
    this.title = album.getTitle();
    this.genre = album.getGenre();
    this.imageLink = album.getImage();
    if (album.getOwner() instanceof Artist) {
      this.ownerName = ((Artist) album.getOwner()).getStageName();
    } else {
      this.ownerName = album.getOwner().getName();
    }
    this.ownerId = album.getOwner().getId();
    this.songCount = album.getSongsInAlbum().size();
    this.songs = new ArrayList<SongResponse>();
    this.songTrackLength = 0;
    for (Song song : album.getSongsInAlbum()) {
      this.songs.add(new SongResponse(song));
      this.songTrackLength += song.getTrackLength();
    }
    this.isPublic = album.getIsPublic();
    this.isAlbum = true;
  }

  public AlbumInfoResponse(User currentUser, Album album) {
    this.id = album.getId();
    this.title = album.getTitle();
    this.genre = album.getGenre();
    this.imageLink = album.getImage();
    if (album.getOwner() instanceof Artist) {
      this.ownerName = ((Artist) album.getOwner()).getStageName();
    } else {
      this.ownerName = album.getOwner().getName();
    }
    this.ownerId = album.getOwner().getId();
    this.songCount = album.getSongsInAlbum().size();
    this.songs = new ArrayList<SongResponse>();
    this.songTrackLength = 0;
    for (Song song : album.getSongsInAlbum()) {
      this.songs.add(new SongResponse(song));
      this.songTrackLength += song.getTrackLength();
    }
    this.isPublic = album.getIsPublic();
    this.isAlbum = true;

    setFollowed(currentUser.getSavedAlbums().contains(album));
    Set<Integer> ids = new HashSet<Integer>();
    for (Song song : currentUser.getSavedSongs()){
      ids.add(song.getId());
    }
    for(SongResponse songResponse: getSongs()) {
      songResponse.setSaved(ids.contains(songResponse.getId()));
    }
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }

  public Integer getSongCount() {
    return songCount;
  }

  public void setSongCount(Integer songCount) {
    this.songCount = songCount;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public Integer getSongTrackLength() {
    return songTrackLength;
  }

  public void setSongTrackLength(Integer songTrackLength) {
    this.songTrackLength = songTrackLength;
  }

  public GenreType getGenre() {
    return genre;
  }

  public void setGenre(GenreType genre) {
    this.genre = genre;
  }

  public List<SongResponse> getSongs() {
    return songs;
  }

  public void setSongs(List<SongResponse> songs) {
    this.songs = songs;
  }

  public boolean isFollowed() {
    return isFollowed;
  }

  public void setFollowed(boolean followed) {
    isFollowed = followed;
  }

  public boolean isAlbum() {
    return isAlbum;
  }

  public void setAlbum(boolean album) {
    isAlbum = album;
  }
}
