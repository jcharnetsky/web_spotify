package webspotify.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.media.Album;

/**
 * @author Cardinals
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
  List<Album> findByTitleContaining(String title);
}
