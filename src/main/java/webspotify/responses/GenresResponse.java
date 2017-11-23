package webspotify.responses;

import webspotify.types.GenreType;

public class GenresResponse {

  private GenreType[] genres;

  public GenresResponse() {
    genres = GenreType.values();
  }

  public GenreType[] getGenres() {
    return genres;
  }

  public void setGenres(GenreType[] genres) {
    this.genres = genres;
  }
}
