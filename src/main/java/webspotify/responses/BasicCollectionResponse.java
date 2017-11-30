package webspotify.responses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webspotify.models.media.Album;
import webspotify.models.media.Song;
import webspotify.models.media.SongCollection;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.types.GenreType;

/**
 *
 * @author Cardinals
 */
public class BasicCollectionResponse {

  private Integer id;
  private String title;
  private String imageLink;

  public BasicCollectionResponse(SongCollection collection) {
    this.id = collection.getId();
    this.imageLink = collection.getImage();
    this.title = collection.getTitle();
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

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }
}
