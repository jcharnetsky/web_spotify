package webspotify.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import webspotify.models.media.Song;

/**
 * @author Cardinals
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
  List<Song> findTop50ByTitleContaining(String title);
  
  @Query("select sum(s.totalListens) from Song s")
  Integer getTotalSongListens();
  
  @Query("select sum(s.monthlyListens) from Song s")
  Integer getMonthlySongListens();
}
