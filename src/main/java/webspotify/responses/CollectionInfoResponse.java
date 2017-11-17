package webspotify.responses;

import java.util.ArrayList;
import java.util.List;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.media.SongCollection;
import webspotify.models.users.Artist;
import webspotify.types.GenreType;

/**
 *
 * @author Cardinals
 */
public class CollectionInfoResponse {

  private Integer id;
  private String title;
  private String description;
  private GenreType genre;
  private String ownerName;
  private Integer ownerId;
  private Integer songCount;
  private Integer songTrackLength;
  private List<SongResponse> songs;

  public CollectionInfoResponse(SongCollection collection) {
    this.id = collection.getId();
    this.title = collection.getTitle();
    this.genre = collection.getGenre();
    if (collection instanceof Playlist) {
      this.description = ((Playlist) collection).getDescription();
    }
    if (collection.getOwner() instanceof Artist) {
      this.ownerName = ((Artist)collection.getOwner()).getStageName();
    } else {
      this.ownerName = collection.getOwner().getName();
    }
    this.ownerId = collection.getOwner().getId();
    this.songCount = collection.getSongs().size();
    this.songs = new ArrayList<SongResponse>();
    this.songTrackLength = 0;
    for (Song song : collection.getSongs()) {
      this.songs.add(new SongResponse(song));
      this.songTrackLength += song.getTrackLength();
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

  
  
}
