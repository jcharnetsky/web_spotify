package webspotify.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webspotify.config.ConfigConstants;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.media.SongCollection;
import webspotify.models.users.User;
import webspotify.posts.AlbumCreateRequest;
import webspotify.posts.PlaylistCreateRequest;
import webspotify.repo.SongCollectionRepository;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.CollectionInfoResponse;
import webspotify.responses.SongResponse;

/**
 *
 * @author Cardinals
 */
@Service("songCollectionService")
public class SongCollectionService {
  @Autowired
  SongCollectionRepository songCollectionRepo;
  @Autowired
  UserRepository userRepo;
  
  @Autowired
  SongRepository songRepo;

  @Transactional
  public Response getSongsInCollection(User user, final int collectionId) {
    if (songCollectionRepo.exists(collectionId)) {
      SongCollection collection = songCollectionRepo.findOne(collectionId);
      if (collection.isPublic() || (!collection.isPublic() && collection.getOwner().equals(user))) {
        List<SongResponse> songsToReturn = new ArrayList<SongResponse>();
        for (Song song : collection.getSongs()) {
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
  public Response getInfoAboutCollection(User user, final int collectionId) {
    if (songCollectionRepo.exists(collectionId)) {
      SongCollection collection = songCollectionRepo.findOne(collectionId);
      if (collection.isPublic() || (!collection.isPublic() && collection.getOwner().equals(user))) {
        return ResponseUtilities.filledSuccess(new CollectionInfoResponse(collection));
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response removeSongFromCollection(User user, int collectionId, int songId) {
    if (songCollectionRepo.exists(collectionId) && songRepo.exists(songId)) {
      SongCollection collection = songCollectionRepo.findOne(collectionId);
      Song song = songRepo.findOne(songId);
      if (collection.getOwner().equals(user)) {
        boolean removed = collection.getSongs().remove(song);
        if (removed) {
          songCollectionRepo.save(collection);
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
  public Response addSongFromCollection(User user, int collectionId, int songId) {
    if (songCollectionRepo.exists(collectionId) && songRepo.exists(songId)) {
      SongCollection collection = songCollectionRepo.findOne(collectionId);
      Song song = songRepo.findOne(songId);
      if (collection.getOwner().equals(user)) {
        boolean added = collection.getSongs().add(song);
        if (added) {
          songCollectionRepo.save(collection);
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
      songCollectionRepo.save(playlistToAdd);
      user.getOwnedPlaylists().add(playlistToAdd);
      return ResponseUtilities.emptySuccess();
    } catch (Exception e) {
      System.out.println(e);
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_CREATE);
    }
  }
  
  @Transactional
  //NOT FINISHED
  public Response createAlbum(User user, AlbumCreateRequest request) {
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response deleteCollection(User user, int collectionId) {

    if (songCollectionRepo.exists(collectionId)) {
      SongCollection collection = songCollectionRepo.findOne(collectionId);
      if (collection.getOwner().equals(user)) {
        collection.getOwner().getOwnedPlaylists().remove(collection);
        songCollectionRepo.delete(collection);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  public Response getAllRelevantPlaylists(User user) {
    Set<SongCollection> setOfRelevantPlaylists = new HashSet<SongCollection>();
    List<CollectionInfoResponse> dataToReturn = new ArrayList<CollectionInfoResponse>();
    setOfRelevantPlaylists.addAll(user.getFollowedPlaylists());
    setOfRelevantPlaylists.addAll(user.getOwnedPlaylists());
    for (SongCollection collection : setOfRelevantPlaylists) {
      dataToReturn.add(new CollectionInfoResponse(collection));
    }
    return ResponseUtilities.filledSuccess(dataToReturn);
  }

  @Transactional
  public Response unsaveCollection(User user, int collectionId) {
    if (songCollectionRepo.exists(collectionId)) {
      SongCollection collection = songCollectionRepo.findOne(collectionId);
      if (collection instanceof Playlist) {
        boolean successful = user.getFollowedPlaylists().remove(collection);
        if (successful) {
          userRepo.save(user);
          ((Playlist) collection).decrementFollowerCount();
          songCollectionRepo.save(collection);
          return ResponseUtilities.emptySuccess();
        } else {
          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
        }
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.NOT_IMPLEMENTED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response saveCollection(User user, int collectionId) {
    if (songCollectionRepo.exists(collectionId)) {
      SongCollection collection = songCollectionRepo.findOne(collectionId);
      if (collection instanceof Playlist) {
        boolean successful = user.getFollowedPlaylists().add((Playlist)collection);
        if (successful) {
          userRepo.save(user);
          ((Playlist) collection).incrementFollowerCount();
          songCollectionRepo.save(collection);
          return ResponseUtilities.emptySuccess();
        } else {
          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
        }
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.NOT_IMPLEMENTED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

}
