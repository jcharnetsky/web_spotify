package webspotify.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.users.User;
import webspotify.posts.PlaylistCreateRequest;
import webspotify.repo.PlaylistRepository;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.PlaylistInfoResponse;
import webspotify.responses.SongResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

/**
 *
 * @author Cardinals
 */
@Service("playlistService")
public class PlaylistService {

  @Autowired
  PlaylistRepository playlistRepo;

  @Autowired
  UserRepository userRepo;

  @Autowired
  SongRepository songRepo;

  @Transactional
  public Response getSongsInPlaylist(User user, final int playlistId) {
    if (playlistRepo.exists(playlistId)) {
      Playlist playlist = playlistRepo.findOne(playlistId);
      if (playlist.isPublic() || (!playlist.isPublic() && playlist.getOwner().equals(user))) {
        List<SongResponse> songsToReturn = new ArrayList<SongResponse>();
        for (Song song : playlist.getSongs()) {
          songsToReturn.add(new SongResponse(song));
        }
        return ResponseUtilities.filledSuccess(songsToReturn);
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response getInfoAboutPlaylist(User user, final int playlistId) {
    if (playlistRepo.exists(playlistId)) {
      Playlist playlist = playlistRepo.findOne(playlistId);
      if (playlist.isPublic() || (!playlist.isPublic() && playlist.getOwner().equals(user))) {
        return ResponseUtilities.filledSuccess(new PlaylistInfoResponse(playlist));
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response removeSongFromPlaylist(User user, int playlistId, int songId) {
    if (playlistRepo.exists(playlistId) && songRepo.exists(songId)) {
      Playlist playlist = playlistRepo.findOne(playlistId);
      Song song = songRepo.findOne(songId);
      if (playlist.getOwner().equals(user)) {
        boolean removed = playlist.getSongs().remove(song);
        if (removed) {
          playlistRepo.save(playlist);
          return ResponseUtilities.emptySuccess();
        } else {
          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
        }
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response addSongToPlaylist(User user, int playlistId, int songId) {
    if (playlistRepo.exists(playlistId) && songRepo.exists(songId)) {
      Playlist playlist = playlistRepo.findOne(playlistId);
      Song song = songRepo.findOne(songId);
      if (playlist.getOwner().equals(user)) {
        boolean removed = playlist.getSongs().add(song);
        if (removed) {
          playlistRepo.save(playlist);
          return ResponseUtilities.emptySuccess();
        } else {
          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
        }
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response createPlaylistCollection(User user, PlaylistCreateRequest request) {
    Playlist playlistToAdd = new Playlist();
    playlistToAdd.setBanned(false);
    playlistToAdd.setPublic(true);
    playlistToAdd.setCollaborative(false);
    playlistToAdd.setDescription(request.getDescription());
    playlistToAdd.setTitle(request.getTitle());
    playlistToAdd.setGenre(request.getGenre());
    playlistToAdd.setOwner(user);
    try {
      playlistRepo.save(playlistToAdd);
      user.getOwnedPlaylists().add(playlistToAdd);
      return ResponseUtilities.filledSuccess(new PlaylistInfoResponse(playlistToAdd));
    } catch (Exception e) {
      System.out.println(e);
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_CREATE);
    }
  }

  @Transactional
  public Response deletePlaylist(User user, int playlistId) {
    if (playlistRepo.exists(playlistId)) {
      Playlist playlist = playlistRepo.findOne(playlistId);
      if (playlist.getOwner().equals(user)) {
        user.getOwnedPlaylists().remove(playlist);
        playlistRepo.delete(playlist);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response unfollowPlaylist(User user, int playlistId) {
    if (playlistRepo.exists(playlistId)) {
      Playlist playlist = playlistRepo.findOne(playlistId);
      boolean successful = user.getFollowedPlaylists().remove(playlist);
      if (successful) {
        userRepo.save(user);
        playlist.decrementFollowerCount();
        playlistRepo.save(playlist);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response followPlaylist(User user, int playlistId) {
    if (playlistRepo.exists(playlistId)) {
      Playlist playlist = playlistRepo.findOne(playlistId);
      if (playlist.isPublic()) {
        boolean successful = user.getFollowedPlaylists().add(playlist);
        if (successful) {
          userRepo.save(user);
          playlist.incrementFollowerCount();
          playlistRepo.save(playlist);
          return ResponseUtilities.emptySuccess();
        } else {
          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
        }
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response getAllRelevantPlaylists(User user) {
    Set<Playlist> setOfRelevantPlaylists = new HashSet<Playlist>();
    List<PlaylistInfoResponse> dataToReturn = new ArrayList<PlaylistInfoResponse>();
    setOfRelevantPlaylists.addAll(user.getFollowedPlaylists());
    setOfRelevantPlaylists.addAll(user.getOwnedPlaylists());
    for (Playlist collection : setOfRelevantPlaylists) {
      dataToReturn.add(new PlaylistInfoResponse(collection));
    }
    return ResponseUtilities.filledSuccess(dataToReturn);
  }

}
