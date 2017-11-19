package webspotify.posts;

import webspotify.types.GenreType;

/**
 *
 * @author Cardinals
 */
public class PlaylistCreateRequest {

  private String description;
  private GenreType genre;
  private String title;

  public PlaylistCreateRequest() {

  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
