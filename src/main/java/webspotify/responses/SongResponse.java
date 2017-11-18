package webspotify.responses;

import webspotify.models.media.Song;
import webspotify.types.GenreType;

/**
 * @author Cardinals
 */
public class SongResponse {

  private String trackName;
  private Integer trackLength;
  private ArtistResponse artist;
  private GenreType genre;
  private Integer albumId;
  private String albumName;

  public SongResponse(Song build) {
    this.trackLength = build.getTrackLength();
    this.trackName = build.getTitle();
    this.artist = new ArtistResponse(build.getOwner());
    this.genre = build.getGenre();
    this.albumId = build.getAlbum().getId();
    this.albumName = build.getAlbum().getTitle();
  }

  public String getTrackName() {
    return trackName;
  }

  public void setTrackName(String trackName) {
    this.trackName = trackName;
  }

  public Integer getTrackLength() {
    return trackLength;
  }

  public void setTrackLength(Integer trackLength) {
    this.trackLength = trackLength;
  }

  public ArtistResponse getArtist() {
    return artist;
  }

  public void setArtist(ArtistResponse artist) {
    this.artist = artist;
  }

  public GenreType getGenre() {
    return genre;
  }

  public void setGenre(GenreType genre) {
    this.genre = genre;
  }

  public Integer getAlbumId() {
    return albumId;
  }

  public void setAlbumId(Integer albumId) {
    this.albumId = albumId;
  }

  public String getAlbumName() {
    return albumName;
  }

  public void setAlbumName(String albumName) {
    this.albumName = albumName;
  }

}
