package webspotify.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.users.Artist;

/**
 *
 * @author Cardinals
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
