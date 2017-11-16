package webspotify.models.media;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Cardinals
 */
@Entity
@Table(name = "playlists")
@DiscriminatorColumn(name="collectionType", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="PLAYLIST")
public class Playlist extends SongCollection implements Serializable {
    @Column(name = "Description", nullable = false)
    private String description;
    @Column(name = "collaborative", nullable = false)
    private Boolean collaborative;

    public Playlist() {
        super();
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }
    
}
