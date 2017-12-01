package webspotify.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.models.media.Song;
import webspotify.models.users.Administrator;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.posts.AlbumCreateRequest;
import webspotify.repo.AlbumRepository;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.AlbumInfoResponse;
import webspotify.responses.SongResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

/**
 *
 * @author Cardinals
 */
@Service("albumService")
public class AlbumService {

  @Autowired
  AlbumRepository albumRepo;

  @Autowired
  UserRepository userRepo;

  @Autowired
  SongRepository songRepo;

  @Transactional
  public Response getInfoAboutAlbum(User user, final int albumId) {
    if (albumRepo.exists(albumId)) {
      User userToCheck = userRepo.findOne(user.getId());
      Album album = albumRepo.findOne(albumId);
      if (album.isPublic()) {
        return ResponseUtilities.filledSuccess(new AlbumInfoResponse(userToCheck, album));
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response removeSongFromAlbum(User user, int albumId, int songId) {
    if (albumRepo.exists(albumId) && songRepo.exists(songId)) {
      Album album = albumRepo.findOne(albumId);
      Song song = songRepo.findOne(songId);
      if (album.getOwner().equals(user)) {
        boolean removed = album.getSongs().remove(song);
        if (removed) {
          albumRepo.save(album);
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
  public Response addSongToAlbum(User user, int albumId, int songId) {
    if (albumRepo.exists(albumId) && songRepo.exists(songId)) {
      Album album = albumRepo.findOne(albumId);
      Song song = songRepo.findOne(songId);
      if (album.getOwner().equals(user)) {
        boolean removed = album.getSongs().add(song);
        if (removed) {
          albumRepo.save(album);
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
  public Response createAlbumCollection(User user, AlbumCreateRequest request) {
    if (user instanceof Artist) {
      User owner = userRepo.findOne(user.getId());
      Album albumToAdd = new Album();
      albumToAdd.setBanned(false);
      albumToAdd.setPublic(true);
      albumToAdd.setTitle(request.getTitle());
      albumToAdd.setGenre(request.getGenre());
      albumToAdd.setOwner(owner);
      try {
        albumRepo.save(albumToAdd);
        ((Artist) user).getOwnedAlbums().add(albumToAdd);
        return ResponseUtilities.emptySuccess();
      } catch (Exception e) {
        System.out.println(e);
        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_CREATE);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
  }

  @Transactional
  public Response deleteAlbum(User user, int albumId) {
    if (albumRepo.exists(albumId)) {
      Album album = albumRepo.findOne(albumId);
      if (album.getOwner().equals(user) && album.getOwner() instanceof Artist) {
        Artist artist = (Artist) user;
        artist.getOwnedAlbums().remove(album);
        albumRepo.delete(album);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response unsaveAlbum(User user, int albumId) {
    if (albumRepo.exists(albumId)) {
      Album album = albumRepo.findOne(albumId);
      User userToChange = userRepo.findOne(user.getId());
      boolean successful = userToChange.getSavedAlbums().remove(album);
      if (successful) {
        userRepo.save(userToChange);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response editAlbum(User user, int albumId, AlbumCreateRequest request) {
    if(!albumRepo.exists(albumId)){
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
    try {
      Album toEdit = albumRepo.findOne(albumId);
      Artist userToCheck = (Artist) toEdit.getOwner();
      if(!(user instanceof Administrator) && userToCheck.equals(user)){
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
      userToCheck.getOwnedAlbums().remove(toEdit);
      if(request.getTitle() != null) {
        toEdit.setTitle(request.getTitle());
      }
      if(request.getGenre() != null) {
        toEdit.setGenre(request.getGenre());
      }
      userToCheck.getOwnedAlbums().add(toEdit);
      albumRepo.save(toEdit);
      return ResponseUtilities.emptySuccess();
    } catch (Exception e) {
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_EDIT);
    }
  }

  @Transactional
  public Response saveAlbum(User user, int albumId) {
    if (albumRepo.exists(albumId)) {
      Album album = albumRepo.findOne(albumId);
      User userToChange = userRepo.findOne(user.getId());
      boolean successful = userToChange.getSavedAlbums().add(album);
      if (successful) {
        userRepo.save(userToChange);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response getAllRelevantAlbums(User user) {
    User userToCheck = userRepo.findOne(user.getId());
    Set<Album> setOfRelevantAlbums = new HashSet<Album>();
    List<AlbumInfoResponse> dataToReturn = new ArrayList<AlbumInfoResponse>();
    setOfRelevantAlbums.addAll(userToCheck.getSavedAlbums());
    if (userToCheck instanceof Artist) {
      setOfRelevantAlbums.addAll(((Artist) userToCheck).getOwnedAlbums());
    }
    for (Album album : setOfRelevantAlbums) {
      dataToReturn.add(new AlbumInfoResponse(userToCheck, album));
    }
    return ResponseUtilities.filledSuccess(dataToReturn);
  }

}
