package webspotify.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Album;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.models.media.Song;
import webspotify.models.users.User;
import webspotify.repo.SongRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.SongResponse;

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
  public Response getAllRelevantSongs(User user) {
    Set<Song> relevantSongs = new HashSet<Song>();
    relevantSongs.addAll(user.getSavedSongs());
    for (Album album : user.getSavedAlbums()) {
      relevantSongs.addAll(album.getSongs());
    }
    List<SongResponse> songsToReturn = new ArrayList<SongResponse>();
    for (Song song : relevantSongs) {
      songsToReturn.add(new SongResponse(song));
    }
    return ResponseUtilities.filledSuccess(songsToReturn);
  }

  @Transactional
  public Response addSavedSong(User user, int songId) {
    if (songRepository.exists(songId)) {
      Song songToAdd = songRepository.findOne(songId);
      if (songToAdd.isPublic() || (!songToAdd.isPublic() && songToAdd.getOwner().equals(user))) {
        boolean successful = user.getSavedSongs().add(songToAdd);
        if (successful) {
          userRepository.save(user);
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
      Song songToAdd = songRepository.findOne(songId);
      boolean successful = user.getSavedSongs().remove(songToAdd);
      if (successful) {
        userRepository.save(user);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }

  @Transactional
  public HttpEntity<byte[]> getSongAudio(final int songId) {
    Song song = songRepository.findOne(songId);
    if (song == null) {
      return null;
    }
    byte[] songAudio = song.getAudio();
    if (songAudio == null || songAudio.length == 0) {
      return null;
    }
    MediaType type = MediaType.parseMediaType(ConfigConstants.AUDIO_TYPE);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(type);
    headers.setContentLength(songAudio.length);
    return new HttpEntity<byte[]>(songAudio, headers);
  }
}
