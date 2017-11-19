package webspotify.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.media.SongCollection;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.posts.PlaylistCreateRequest;
import webspotify.repo.AlbumRepository;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.AlbumInfoResponse;
import webspotify.responses.PlaylistInfoResponse;
import webspotify.responses.SongResponse;

/**
 *
 * @author Cardinals
 */
//@Service("playlistService")
public class AlbumService {
//
//  @Autowired
//  AlbumRepository albumRepo;
//
//  @Autowired
//  UserRepository userRepo;
//
//  @Autowired
//  SongRepository songRepo;
//
//  @Transactional
//  public Response getSongsInAlbum(User user, final int albumId) {
//    if (albumRepo.exists(albumId)) {
//      Album album = albumRepo.findOne(albumId);
//      if (album.isPublic() || (!album.isPublic() && album.getOwner().equals(user))) {
//        List<SongResponse> songsToReturn = new ArrayList<SongResponse>();
//        for (Song song : album.getSongs()) {
//          songsToReturn.add(new SongResponse(song));
//        }
//        return ResponseUtilities.filledSuccess(songsToReturn);
//      } else {
//        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
//      }
//    } else {
//      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
//    }
//  }
//
//  @Transactional
//  public Response getInfoAboutAlbum(User user, final int albumId) {
//    if (albumRepo.exists(albumId)) {
//      Album album = albumRepo.findOne(albumId);
//      if (album.isPublic() || (!album.isPublic() && album.getOwner().equals(user))) {
//        return ResponseUtilities.filledSuccess(new AlbumInfoResponse(album));
//      } else {
//        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
//      }
//    } else {
//      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
//    }
//  }
//
//  @Transactional
//  public Response removeSongFromAlbum(User user, int albumId, int songId) {
//    if (albumRepo.exists(albumId) && songRepo.exists(songId)) {
//      Album album = albumRepo.findOne(albumId);
//      Song song = songRepo.findOne(songId);
//      if (album.getOwner().equals(user)) {
//        boolean removed = album.getSongs().remove(song);
//        if (removed) {
//          albumRepo.save(album);
//          return ResponseUtilities.emptySuccess();
//        } else {
//          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
//        }
//      } else {
//        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
//      }
//    } else {
//      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
//    }
//  }
//
//  @Transactional
//  public Response addSongToAlbum(User user, int albumId, int songId) {
//    if (albumRepo.exists(albumId) && songRepo.exists(songId)) {
//      Album album = albumRepo.findOne(albumId);
//      Song song = songRepo.findOne(songId);
//      if (album.getOwner().equals(user)) {
//        boolean removed = album.getSongs().add(song);
//        if (removed) {
//          albumRepo.save(album);
//          return ResponseUtilities.emptySuccess();
//        } else {
//          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
//        }
//      } else {
//        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
//      }
//    } else {
//      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
//    }
//  }
//
//  @Transactional
//  public Response createPlaylistCollection(User user, PlaylistCreateRequest request) {
//    if () {
//      Playlist playlistToAdd = new Playlist();
//      playlistToAdd.setBanned(false);
//      playlistToAdd.setPublic(true);
//      playlistToAdd.setCollaborative(false);
//      playlistToAdd.setDescription(request.getDescription());
//      playlistToAdd.setTitle(request.getTitle());
//      playlistToAdd.setGenre(request.getGenre());
//      playlistToAdd.setOwner(user);
//      try {
//        albumRepo.save(playlistToAdd);
//        user.getOwnedPlaylists().add(playlistToAdd);
//        return ResponseUtilities.emptySuccess();
//      } catch (Exception e) {
//        System.out.println(e);
//        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_CREATE);
//      }
//    } else {
//      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);z
//    }
//  }
//
//  @Transactional
//  public Response deleteAlbum(User user, int albumId) {
//    if (albumRepo.exists(albumId)) {
//      Album album = albumRepo.findOne(albumId);
//      if (album.getOwner().equals(user) && album.getOwner() instanceof Artist) {
//        Artist artist = (Artist) user;
//        artist.getOwnedAlbums().remove(album);
//        albumRepo.delete(album);
//        return ResponseUtilities.emptySuccess();
//      } else {
//        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
//      }
//    } else {
//      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
//    }
//  }
//
//  @Transactional
//  public Response unfollowPlaylist(User user, int albumId) {
//    if (albumRepo.exists(albumId)) {
//      Playlist playlist = albumRepo.findOne(albumId);
//      boolean successful = user.getFollowedPlaylists().remove(playlist);
//      if (successful) {
//        userRepo.save(user);
//        playlist.decrementFollowerCount();
//        albumRepo.save(playlist);
//        return ResponseUtilities.emptySuccess();
//      } else {
//        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
//      }
//    } else {
//      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
//    }
//  }
//
//  @Transactional
//  public Response followPlaylist(User user, int albumId) {
//    if (albumRepo.exists(albumId)) {
//      Playlist playlist = albumRepo.findOne(albumId);
//      boolean successful = user.getFollowedPlaylists().add(playlist);
//      if (successful) {
//        userRepo.save(user);
//        playlist.incrementFollowerCount();
//        albumRepo.save(playlist);
//        return ResponseUtilities.emptySuccess();
//      } else {
//        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
//      }
//    } else {
//      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
//    }
//  }
//
//  @Transactional
//  public Response getAllRelevantPlaylists(User user) {
//    Set<Playlist> setOfRelevantPlaylists = new HashSet<Playlist>();
//    List<PlaylistInfoResponse> dataToReturn = new ArrayList<PlaylistInfoResponse>();
//    setOfRelevantPlaylists.addAll(user.getFollowedPlaylists());
//    setOfRelevantPlaylists.addAll(user.getOwnedPlaylists());
//    for (SongCollection collection : setOfRelevantPlaylists) {
//      dataToReturn.add(new PlaylistInfoResponse(collection));
//    }
//    return ResponseUtilities.filledSuccess(dataToReturn);
//  }

}
