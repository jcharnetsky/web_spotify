package webspotify.models.media;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Cardinals
 */
@Entity
@Table(name = "albums")
public class Album extends SongCollection {
}
