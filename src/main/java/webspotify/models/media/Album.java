package webspotify.models.media;

import java.io.File;
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
  @OneToMany(mappedBy = "album")
  private List<Song> songsInAlbum;

  public List<Song> getSongsInAlbum() {
    return songsInAlbum;
  }

  public void setSongsInAlbum(List<Song> songsInAlbum) {
    this.songsInAlbum = songsInAlbum;
  }

  @Override
  public String getImage(){
    File imageFile = new File("src/main/webapp/images/albums/" + this.getId() + ".jpg");
    if(imageFile.exists()){
      return "../images/albums/" + this.getId() + ".jpg";
    } else {
      return "../images/logo.png";
    }
  }
}
