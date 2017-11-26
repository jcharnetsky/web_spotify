package webspotify.models.media;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Cardinals
 */
@Entity
@Table(name = "albums")
public class Album extends SongCollection {
  @OneToMany(mappedBy = "album", fetch = FetchType.EAGER)
  @Fetch(value = FetchMode.SUBSELECT)
  private List<Song> songsInAlbum;

  public List<Song> getSongsInAlbum() {
    return songsInAlbum;
  }

  public void setSongsInAlbum(List<Song> songsInAlbum) {
    this.songsInAlbum = songsInAlbum;
  }
}
