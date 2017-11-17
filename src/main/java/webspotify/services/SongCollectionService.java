package webspotify.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.media.SongCollection;
import webspotify.models.users.User;
import webspotify.posts.PlaylistCreateRequest;
import webspotify.repo.SongCollectionRepository;
import webspotify.repo.SongRepository;
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
        return ResponseUtilities.filledFailure("User does not have access to Collection.");
      }
    } else {
      return ResponseUtilities.filledFailure("Collection does not exist.");
    }
  }

  @Transactional
  public Response getInfoAboutCollection(User user, final int collectionId) {
    if (songCollectionRepo.exists(collectionId)) {
      SongCollection collection = songCollectionRepo.findOne(collectionId);
      if (collection.isPublic() || (!collection.isPublic() && collection.getOwner().equals(user))) {
        return ResponseUtilities.filledSuccess(new CollectionInfoResponse(collection));
      } else {
        return ResponseUtilities.filledFailure("User does not have access to Collection.");
      }
    } else {
      return ResponseUtilities.filledFailure("Collection does not exist.");
    }
  }

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
          return ResponseUtilities.filledFailure("Collection could not be updated.");
        }
      } else {
        return ResponseUtilities.filledFailure("User does not have access to Collection.");
      }
    } else {
      return ResponseUtilities.filledFailure("Collection/Song does not exist.");
    }
  }

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
          return ResponseUtilities.filledFailure("Collection could not be updated.");
        }
      } else {
        return ResponseUtilities.filledFailure("User does not have access to Collection.");
      }
    } else {
      return ResponseUtilities.filledFailure("Collection/Song does not exist.");
    }
  }

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
      return ResponseUtilities.emptySuccess();
    } catch (Exception e) {
      System.out.println(e);
      return ResponseUtilities.filledFailure("Playlist Could not be Created");
    }
  }

  public Response deleteCollection(User user, int collectionId) {
    if (songCollectionRepo.exists(collectionId)) {
      songCollectionRepo.delete(collectionId);
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure("Collection does not exist.");
    }
  }

}
