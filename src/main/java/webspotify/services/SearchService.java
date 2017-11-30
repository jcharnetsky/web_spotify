package webspotify.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.users.User;
import webspotify.repo.AlbumRepository;
import webspotify.repo.ArtistRepository;
import webspotify.repo.ConcertRepository;
import webspotify.repo.PlaylistRepository;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.BasicCollectionResponse;
import webspotify.responses.BasicSongResponse;
import webspotify.responses.BasicUserInfoResponse;
import webspotify.responses.SearchResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("searchService")
public class SearchService {

  @Autowired
  ConcertRepository concertRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  SongRepository songRepository;
  @Autowired
  ArtistRepository artistRepository;
  @Autowired
  PlaylistRepository playlistRepository;
  @Autowired
  AlbumRepository albumRepository;

  @Transactional
  public Response search(User user, String searchKey) {

    Set<BasicSongResponse> songsToReturn = new HashSet();
    List<Song> songs = songRepository.findTop50ByTitleContaining(searchKey);
    Set<String> titles = new HashSet<String>();
    for (Song song : songs) {
      if (songsToReturn.size() < ConfigConstants.MAX_SEARCH_ELEMENT_COUNT) {
        if (!titles.contains(song.getTitle()) && song.isPublic() && !song.isBanned()) {
          titles.add(song.getTitle());
          songsToReturn.add(new BasicSongResponse(song));
        }
      }
    }
    Set<BasicUserInfoResponse> usersToReturn = new HashSet();
    List<User> users = userRepository.findByNameContaining(searchKey);
    for (User userToCheck : users) {
      if (usersToReturn.size() < ConfigConstants.MAX_SEARCH_ELEMENT_COUNT) {
        if (userToCheck.isPublic() && !userToCheck.isBanned()) {
          usersToReturn.add(new BasicUserInfoResponse(userToCheck));
        }
      }
    }

    Set<BasicCollectionResponse> playlistsToReturn = new HashSet();
    List<Playlist> playlists = playlistRepository.findByTitleContaining(searchKey);
    for (Playlist playlist : playlists) {
      if (playlistsToReturn.size() < ConfigConstants.MAX_SEARCH_ELEMENT_COUNT) {
        if (playlist.isPublic() && !playlist.isBanned()) {
          playlistsToReturn.add(new BasicCollectionResponse(playlist));
        }
      }
    }

    Set<BasicCollectionResponse> albumsToReturn = new HashSet();
    List<Album> albums = albumRepository.findByTitleContaining(searchKey);
    for (Album album : albums) {
      if (albumsToReturn.size() < ConfigConstants.MAX_SEARCH_ELEMENT_COUNT) {
        if (album.isPublic() && !album.isBanned()) {
          albumsToReturn.add(new BasicCollectionResponse(album));
        }
      }
    }

    SearchResponse toReturn = new SearchResponse(songsToReturn, usersToReturn, albumsToReturn, playlistsToReturn);
    return ResponseUtilities.filledSuccess(toReturn);
  }

}
