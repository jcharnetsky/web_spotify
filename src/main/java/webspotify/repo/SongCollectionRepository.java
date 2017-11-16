package webspotify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.media.SongCollection;

/**
 *
 * @author Cardinals
 */
@Repository
public interface SongCollectionRepository extends JpaRepository<SongCollection, Integer> {
}
