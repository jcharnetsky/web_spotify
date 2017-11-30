package webspotify.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.ManageSongInfoResponse;
import webspotify.responses.SongResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("songService")
public class SongService {

  @Autowired
  SongRepository songRepository;

  @Autowired
  UserRepository userRepository;

  @Transactional
  public Response getSong(final int songId) {
    Song song = songRepository.findOne(songId);
    if (song == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    } else {
      List<SongResponse> contentBody = new ArrayList<SongResponse>();
      contentBody.add(new SongResponse(song));
      return ResponseUtilities.filledSuccess(contentBody);
    }
  }

  @Transactional
  public Response getAllSavedSongs(User user) {
    List<SongResponse> songsToReturn = new ArrayList<SongResponse>();
    for (Song song : user.getSavedSongs()) {
      SongResponse response = new SongResponse(song);
      response.setSaved(true);
      songsToReturn.add(response);
    }
    return ResponseUtilities.filledSuccess(songsToReturn);
  }

  @Transactional
  public Response addSavedSong(User user, int songId) {
    if (songRepository.exists(songId)) {
      Song songToAdd = songRepository.findOne(songId);
      if (songToAdd.isPublic() || (!songToAdd.isPublic() && songToAdd.getOwner().equals(user))) {
        User userToChange = userRepository.findOne(user.getId());
        boolean successful = userToChange.getSavedSongs().add(songToAdd);
        if (successful) {
          userToChange.getSavedSongs().add(songToAdd);
          userRepository.save(userToChange);
          return ResponseUtilities.emptySuccess();
        } else {
          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
        }
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }

  @Transactional
  public Response remSavedSong(User user, int songId) {
    if (songRepository.exists(songId)) {
      Song songToRem = songRepository.findOne(songId);
      User userToChange = userRepository.findOne(user.getId());
      boolean successful = userToChange.getSavedSongs().remove(songToRem);
      if (successful) {
        user.getSavedSongs().remove(songToRem);
        userRepository.save(userToChange);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }

  @Transactional
  public Response getManageSongInfo(Artist artist, int songId) {
    if (songRepository.exists(songId)) {
      Song song = songRepository.findOne(songId);
      if (artist.getOwnedSongs().contains(song)) {
        return ResponseUtilities.filledSuccess(new ManageSongInfoResponse(song));
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }
}
