package webspotify.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Song;
import webspotify.models.users.Administrator;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.repo.ArtistRepository;
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

  @Autowired
  ArtistRepository artistRepository;

  @Autowired
  RoyaltyService royaltyService;

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
    User userToCheck = userRepository.findOne(user.getId());
    List<SongResponse> songsToReturn = new ArrayList<SongResponse>();
    for (Song song : userToCheck.getSavedSongs()) {
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
      Artist artistToCheck = (Artist) userRepository.findOne(artist.getId());
      Song song = songRepository.findOne(songId);
      if (artistToCheck.getOwnedSongs().contains(song)) {
        Double monthlyRevenue = royaltyService.getMonthlySongRevenue(song);
        Double allRevenue = royaltyService.getTotalSongRevenue(song);
        ManageSongInfoResponse toReturn = new ManageSongInfoResponse(song);
        toReturn.setAllRoyalties(allRevenue);
        toReturn.setMonthRoyalties(monthlyRevenue);
        return ResponseUtilities.filledSuccess(toReturn);
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }

  @Transactional
  public Response getLyrics(int songId){
    Song song = songRepository.findOne(songId);
    if (song != null && !song.isBanned()) {
      return ResponseUtilities.filledSuccess(song.getLyrics());
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }

  @Transactional
  public Response setLyrics(User user, int songId, String lyrics) {
    Song song = songRepository.findOne(songId);
    if (song != null && !song.isBanned()) {
      if (user instanceof Administrator || song.getOwner().equals(user)) {
        song.setLyrics(lyrics);
        songRepository.save(song);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
  }
}
