package webspotify.posts;

/**
 *
 * @author Cardinals
 */
public class PlaylistChangeSongRequest {
  private Integer songID;
  private Integer playlistID;

  public Integer getSongID() {
    return songID;
  }

  public void setSongID(Integer songID) {
    this.songID = songID;
  }

  public Integer getPlaylistID() {
    return playlistID;
  }

  public void setPlaylistID(Integer playlistID) {
    this.playlistID = playlistID;
  }
}
