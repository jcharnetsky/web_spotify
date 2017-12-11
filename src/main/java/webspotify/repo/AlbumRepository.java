package webspotify.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webspotify.models.media.Album;
import webspotify.types.GenreType;

/**
 * @author Cardinals
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
  List<Album> findByTitleContaining(String title);
  List<Album> findByGenre(GenreType genre);
}
