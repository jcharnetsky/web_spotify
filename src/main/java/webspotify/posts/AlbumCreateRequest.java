package webspotify.posts;

import webspotify.types.GenreType;

/**
 *
 * @author Cardinals
 */
public class AlbumCreateRequest {

  private GenreType genre;
  private String title;

  public AlbumCreateRequest() {
  }

  public GenreType getGenre() {
    return genre;
  }

  public void setGenre(GenreType genre) {
    this.genre = genre;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
