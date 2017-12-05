package webspotify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webspotify.models.media.Album;
import webspotify.models.users.Artist;

import java.util.List;

/**
 * @author Cardinals
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
