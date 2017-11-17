package webspotify.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.media.Concert;

/**
 * @author Cardinals
 */
@Repository
public interface ConcertRepository extends JpaRepository<Concert, Integer> {
}
