package webspotify.models.media;

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
@Table(name = "albums")
@DiscriminatorColumn(name = "collectionType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "ALBUM")
public class Album extends SongCollection {
}
