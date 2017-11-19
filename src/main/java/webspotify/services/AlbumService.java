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
import webspotify.posts.AlbumCreateRequest;
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
@Service("albumService")
public class AlbumService {

  @Autowired
  AlbumRepository albumRepo;

  @Autowired
  UserRepository userRepo;

  @Autowired
  SongRepository songRepo;

  @Transactional
  public Response getSongsInAlbum(User user, final int albumId) {
    if (albumRepo.exists(albumId)) {
      Album album = albumRepo.findOne(albumId);
      if (album.isPublic() || (!album.isPublic() && album.getOwner().equals(user))) {
        List<SongResponse> songsToReturn = new ArrayList<SongResponse>();
        for (Song song : album.getSongs()) {
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
  public Response getInfoAboutAlbum(User user, final int albumId) {
    if (albumRepo.exists(albumId)) {
      Album album = albumRepo.findOne(albumId);
      if (album.isPublic() || (!album.isPublic() && album.getOwner().equals(user))) {
        return ResponseUtilities.filledSuccess(new AlbumInfoResponse(album));
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
  public Response getAllRelevantPlaylists(User user) {
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
