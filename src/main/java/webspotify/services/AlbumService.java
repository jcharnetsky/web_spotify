package webspotify.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.models.media.Song;
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
    if (user instanceof Artist) {
      Artist artist = (Artist) user;
      for (Album album : artist.getOwnedAlbums()) {
        if (album.getId() == albumId) {
          AlbumInfoResponse response = new AlbumInfoResponse(album);
          Set<Integer> ids = new HashSet<Integer>();
          for (Song song : user.getSavedSongs()){
            ids.add(song.getId());
          }
          for(SongResponse songResponse: response.getSongs()){
            songResponse.setSaved(ids.contains(songResponse.getId()));
          }
          return ResponseUtilities.filledSuccess(response);
        }
      }
    }
    for (Album album: user.getSavedAlbums()){
      if(album.getId() == albumId) {
        AlbumInfoResponse response = new AlbumInfoResponse(album);
        response.setFollowed(true);
        Set<Integer> ids = new HashSet<Integer>();
        for (Song song : user.getSavedSongs()){
          ids.add(song.getId());
        }
        for(SongResponse songResponse: response.getSongs()){
          songResponse.setSaved(ids.contains(songResponse.getId()));
        }
        return ResponseUtilities.filledSuccess(response);
      }
    }
    if (albumRepo.exists(albumId)) {
      Album album = albumRepo.findOne(albumId);
      if (album.isPublic()) {
        AlbumInfoResponse response = new AlbumInfoResponse(album);
        response.setFollowed(false);
        Set<Integer> ids = new HashSet<Integer>();
        for (Song song : user.getSavedSongs()){
          ids.add(song.getId());
        }
        for(SongResponse songResponse: response.getSongs()){
          songResponse.setSaved(ids.contains(songResponse.getId()));
        }
        return ResponseUtilities.filledSuccess(response);
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
      Album albumToAdd = new Album();
      albumToAdd.setBanned(false);
      albumToAdd.setPublic(true);
      albumToAdd.setTitle(request.getTitle());
      albumToAdd.setGenre(request.getGenre());
      albumToAdd.setOwner(user);
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
      boolean successful = user.getSavedAlbums().remove(album);
      if (successful) {
        userRepo.save(user);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COLLECTION_NO_EXIST);
    }
  }

  @Transactional
  public Response saveAlbum(User user, int albumId) {
    if (albumRepo.exists(albumId)) {
      Album album = albumRepo.findOne(albumId);
      boolean successful = user.getSavedAlbums().add(album);
      if (successful) {
        userRepo.save(user);
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
    Set<Album> setOfRelevantAlbums = new HashSet<Album>();
    List<AlbumInfoResponse> dataToReturn = new ArrayList<AlbumInfoResponse>();
    setOfRelevantAlbums.addAll(user.getSavedAlbums());
    if (user instanceof Artist) {
      setOfRelevantAlbums.addAll(((Artist) user).getOwnedAlbums());
    }
    for (Album album : setOfRelevantAlbums) {
      dataToReturn.add(new AlbumInfoResponse(album));
    }
    return ResponseUtilities.filledSuccess(dataToReturn);
  }

}
