package webspotify.responses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.types.GenreType;

/**
 *
 * @author Cardinals
 */
public class PlaylistInfoResponse {

  private Integer id;
  private String title;
  private String description;
  private String imageLink;
  private GenreType genre;
  private String ownerName;
  private Integer ownerId;
  private Integer songCount;
  private Integer songTrackLength;
  private Integer followerCount;
  private List<SongResponse> songs;
  private boolean isPublic;
  private boolean isFollowed;
  private boolean isPlaylist;

  public PlaylistInfoResponse(Playlist playlist) {
    this.id = playlist.getId();
    this.title = playlist.getTitle();
    this.genre = playlist.getGenre();
    this.description = playlist.getDescription();
    this.imageLink = playlist.getImage();
    this.followerCount = playlist.getFollowerCount();

    if (playlist.getOwner() instanceof Artist) {
      this.ownerName = ((Artist) playlist.getOwner()).getStageName();
    } else {
      this.ownerName = playlist.getOwner().getName();
    }
    this.ownerId = playlist.getOwner().getId();
    this.songCount = playlist.getSongs().size();
    this.songs = new ArrayList<SongResponse>();
    this.songTrackLength = 0;
    for (Song song : playlist.getSongs()) {
      this.songs.add(new SongResponse(song));
      this.songTrackLength += song.getTrackLength();
    }
    this.isPublic = playlist.getIsPublic();
    this.isPlaylist = true;
  }

  public PlaylistInfoResponse(User currentUser, Playlist playlist) {
    this.id = playlist.getId();
    this.title = playlist.getTitle();
    this.genre = playlist.getGenre();
    this.description = playlist.getDescription();
    this.imageLink = playlist.getImage();
    this.followerCount = playlist.getFollowerCount();

    if (playlist.getOwner() instanceof Artist) {
      this.ownerName = ((Artist) playlist.getOwner()).getStageName();
    } else {
      this.ownerName = playlist.getOwner().getName();
    }
    this.ownerId = playlist.getOwner().getId();
    this.songCount = playlist.getSongs().size();
    this.songs = new ArrayList<SongResponse>();
    this.songTrackLength = 0;
    for (Song song : playlist.getSongs()) {
      this.songs.add(new SongResponse(song));
      this.songTrackLength += song.getTrackLength();
    }
    this.isPublic = playlist.getIsPublic();
    this.isPlaylist = true;

    setFollowed(currentUser.getFollowedPlaylists().contains(playlist));
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Integer getSongTrackLength() {
    return songTrackLength;
  }

  public void setSongTrackLength(Integer songTrackLength) {
    this.songTrackLength = songTrackLength;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
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

  public Integer getFollowerCount() {
    return followerCount;
  }

  public void setFollowerCount(Integer followerCount) {
    this.followerCount = followerCount;
  }

  public boolean isFollowed() {
    return isFollowed;
  }

  public void setFollowed(boolean followed) {
    isFollowed = followed;
  }

  public boolean isPlaylist() {
    return isPlaylist;
  }

  public void setPlaylist(boolean playlist) {
    isPlaylist = playlist;
  }
}
