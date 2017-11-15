package webspotify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.media.Song;

/**
 *
 * @author Cardinals
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
}
