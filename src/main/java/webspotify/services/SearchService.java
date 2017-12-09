package webspotify.services;

import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.media.SongCollection;
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
import webspotify.types.GenreType;
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
    Set<BasicCollectionResponse> albumResponses = getRandomCollectionResponses(albums);
    List<Playlist> playlists = playlistRepository.findAll();
    Set<BasicCollectionResponse> playlistResponses = getRandomCollectionResponses(playlists);
    List<Artist> artists = artistRepository.findAll();
    Set<BasicUserInfoResponse> artistResponses = getRandomUserResponses(artists);
    SearchResponse response = new SearchResponse(null, artistResponses, albumResponses, playlistResponses);
    return ResponseUtilities.filledSuccess(response);
  }

  @Transactional
  public Response getGenreAlbums(GenreType genre){
    List<Album> albums = albumRepository.findByGenre(genre);
    Set<BasicCollectionResponse> responses = getRandomCollectionResponses(albums);
    return ResponseUtilities.filledSuccess(responses);
  }

  @Transactional
  public Response getNewReleases(){
    List<Album> albums = albumRepository.findAll();
    Set<BasicCollectionResponse> responses = getRandomCollectionResponses(albums);
    return ResponseUtilities.filledSuccess(responses);
  }

  @Transactional
  public Response getDiscover(int userId){
    User user = userRepository.findOne(userId);
    List<SongCollection> followedCollections = new LinkedList<SongCollection>();
    for(Album album: user.getSavedAlbums()){
      followedCollections.add((SongCollection) album);
    }
    for(Playlist playlist: user.getFollowedPlaylists()){
      followedCollections.add((SongCollection) playlist);
    }
    for(Playlist playlist: user.getOwnedPlaylists()){
      followedCollections.add((SongCollection) playlist);
    }
    GenreType genre = getMostOccuringGenreInCollections(followedCollections);
    Set<BasicCollectionResponse> albumResponses = getRandomCollectionResponses(albumRepository.findByGenre(genre));
    Set<BasicCollectionResponse> playlistResponses = getRandomCollectionResponses(playlistRepository.findByGenre(genre));

    List<Artist> artists = new ArrayList<Artist>();
    Artist lastFollowedArtist = null;
    for (User following : user.getFollowing()){
      if(following instanceof Artist){
        lastFollowedArtist = (Artist) following;
      }
    }
    if(lastFollowedArtist != null){
      if(lastFollowedArtist.getOwnedAlbums().size() > 0) {
        genre = ((Album) lastFollowedArtist.getOwnedAlbums().toArray()[0]).getGenre();
      }
      for(Album genreAlbum: albumRepository.findByGenre(genre)){
        Artist artist = (Artist) genreAlbum.getOwner();
        if (!artists.contains(artist)) {
          artists.add(artist);
        }
      }
    } else {
      artists = artistRepository.findAll();
    }
    Set<BasicUserInfoResponse> artistResponses = getRandomUserResponses(artists);
    SearchResponse response = new SearchResponse(null, artistResponses, albumResponses, playlistResponses);
    return ResponseUtilities.filledSuccess(response);
  }

  public Set<BasicCollectionResponse> getRandomCollectionResponses(List<? extends SongCollection> collections){
    Set<BasicCollectionResponse> responses = new HashSet<BasicCollectionResponse>();
    int start, end;
    if(collections.size() < ConfigConstants.NUM_COLLECTIONS_TO_SHOW_BROWSE){
      start = 0;
      end = collections.size();
    } else {
      start = (new Random()).nextInt(collections.size() - ConfigConstants.NUM_COLLECTIONS_TO_SHOW_BROWSE);
      end = ConfigConstants.NUM_COLLECTIONS_TO_SHOW_BROWSE;
    }
    for(int i = 0; i < end;i++) {
      responses.add(new BasicCollectionResponse(collections.get(start + i)));
    }
    return responses;
  }

  public Set<BasicUserInfoResponse> getRandomUserResponses(List<? extends User> users){
    Set<BasicUserInfoResponse> responses = new HashSet<BasicUserInfoResponse>();
    int start, end;
    if(users.size() < ConfigConstants.NUM_ARTISTS_TO_SHOW_BROWSE){
      start = 0;
      end = users.size();
    } else {
      start = (new Random()).nextInt(users.size() - ConfigConstants.NUM_ARTISTS_TO_SHOW_BROWSE);
      end = ConfigConstants.NUM_ARTISTS_TO_SHOW_BROWSE;
    }
    for(int i = 0; i < end;i++) {
      responses.add(new BasicUserInfoResponse(users.get(start + i)));
    }
    return responses;
  }

  public GenreType getMostOccuringGenreInCollections(List<SongCollection> collections){
    Map<GenreType, Integer> listenedGenres = new HashMap<GenreType, Integer>();
    GenreType genre = GenreType.POP;
    for(SongCollection collection : collections){
      GenreType listenedGenre = collection.getGenre();
      Integer count = listenedGenres.get(genre);
      if(count != null){
        listenedGenres.put(listenedGenre, count+1);
      } else {
        listenedGenres.put(listenedGenre, 1);
      }
    }
    int maxGenreListens = 0;
    for (GenreType listenedGenre: listenedGenres.keySet()){
      if(listenedGenres.get(listenedGenre) > maxGenreListens){
        genre = listenedGenre;
        maxGenreListens = listenedGenres.get(listenedGenre);
      }
    }
    return genre;
  }

  public Response getRelatedArtists(int userId){
    Artist currentArtist = artistRepository.findOne(userId);
    List<Artist> artists = new ArrayList<Artist>();
    GenreType genre = GenreType.POP;
    if(currentArtist != null){
      if(currentArtist.getOwnedAlbums().size() > 0) {
        genre = ((Album) currentArtist.getOwnedAlbums().toArray()[0]).getGenre();
      }
      for(Album genreAlbum: albumRepository.findByGenre(genre)){
        Artist artist = (Artist) genreAlbum.getOwner();
        if (!artists.contains(artist)) {
          artists.add(artist);
        }
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ARTIST_NO_EXIST);
    }
    Set<BasicUserInfoResponse> artistResponses = getRandomUserResponses(artists);
    return ResponseUtilities.filledSuccess(artistResponses);
  }

}
