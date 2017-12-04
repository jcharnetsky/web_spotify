package webspotify.services;

import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
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
    User currentUser = userRepository.findOne(user.getId());
    Set<BasicSongResponse> songsToReturn = new HashSet();
    List<Song> songs = songRepository.findTop50ByTitleContaining(searchKey);
    Set<String> titles = new HashSet<String>();
    for (Song song : songs) {
      if (songsToReturn.size() < ConfigConstants.MAX_SEARCH_ELEMENT_COUNT) {
        if (!titles.contains(song.getTitle()) && song.isPublic() && !song.isBanned()) {
          titles.add(song.getTitle());
          BasicSongResponse songResponse = new BasicSongResponse(song);
          songResponse.setSaved(currentUser.getSavedSongs().contains(song));
          songsToReturn.add(songResponse);
        }
      }
    }
    Set<BasicUserInfoResponse> usersToReturn = new HashSet();
    List<User> users = userRepository.findByNameContaining(searchKey);
    for (User userToCheck : users) {
      if (usersToReturn.size() < ConfigConstants.MAX_SEARCH_ELEMENT_COUNT) {
        if (userToCheck.isPublic() && !userToCheck.isBanned() && !userToCheck.getIsDeleted()) {
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

  @Transactional
  public Response getOverview(){
    List<Album> albums = albumRepository.findAll();
    Set<BasicCollectionResponse> albumResponses = new HashSet<BasicCollectionResponse>();
    int start, end;
    if(albums.size() < ConfigConstants.NUM_ALBUMS_TO_SHOW_BROWSE){
      start = 0;
      end = albums.size();
    } else {
      start = (new Random()).nextInt(albums.size() - ConfigConstants.NUM_ALBUMS_TO_SHOW_BROWSE);
      end = ConfigConstants.NUM_ALBUMS_TO_SHOW_BROWSE;
    }
    for(int i = 0; i < end;i++){
      albumResponses.add(new BasicCollectionResponse(albums.get(start + i)));
    }

    List<Playlist> playlists = playlistRepository.findAll();
    Set<BasicCollectionResponse> playlistResponses = new HashSet<BasicCollectionResponse>();
    if(playlists.size() < ConfigConstants.NUM_PLAYLISTS_TO_SHOW_BROWSE){
      start = 0;
      end = playlists.size();
    } else {
      start = (new Random()).nextInt(playlists.size() - ConfigConstants.NUM_PLAYLISTS_TO_SHOW_BROWSE);
      end = ConfigConstants.NUM_PLAYLISTS_TO_SHOW_BROWSE;
    }
    for(int i = 0; i < end;i++){
      playlistResponses.add(new BasicCollectionResponse(playlists.get(start + i)));
    }

    List<Artist> artists = artistRepository.findAll();
    Set<BasicUserInfoResponse> artistResponses = new HashSet<BasicUserInfoResponse>();
    if(artists.size() < ConfigConstants.NUM_ARTISTS_TO_SHOW_BROWSE){
      start = 0;
      end = artists.size();
    } else {
      start = (new Random()).nextInt(artists.size() - ConfigConstants.NUM_ARTISTS_TO_SHOW_BROWSE);
      end = ConfigConstants.NUM_ARTISTS_TO_SHOW_BROWSE;
    }
    for(int i = 0; i < end;i++){
      artistResponses.add(new BasicUserInfoResponse(artists.get(start + i)));
    }

    SearchResponse response = new SearchResponse(
            null, artistResponses, albumResponses, playlistResponses);
    return ResponseUtilities.filledSuccess(response);
  }

  @Transactional
  public Response getDiscover(int userId){
    User user = userRepository.findOne(userId);
    Set<BasicCollectionResponse> albums = new LinkedHashSet<BasicCollectionResponse>();
    Set<BasicCollectionResponse> playlists = new LinkedHashSet<BasicCollectionResponse>();
    Set<BasicUserInfoResponse> artists = new LinkedHashSet<BasicUserInfoResponse>();
    SearchResponse response = new SearchResponse(null, artists, albums, playlists);
    return ResponseUtilities.filledSuccess(response);
  }

}
