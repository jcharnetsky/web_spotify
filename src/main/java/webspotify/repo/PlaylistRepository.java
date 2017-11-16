package webspotify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.media.Playlist;

/**
 *
 * @author Cardinals
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
}
