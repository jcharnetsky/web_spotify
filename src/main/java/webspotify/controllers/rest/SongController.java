package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.responses.GenresResponse;
import webspotify.services.SongService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/songs")
public class SongController {

  @Autowired
  private SongService songService;

  @GetMapping("/saved")
  public Response getSongs(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return songService.getAllSavedSongs(user);
  }

  @GetMapping("/saved/add/{songId}")
  public Response addSavedSong(@PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return songService.addSavedSong(user, songId);
  }

  @GetMapping("/saved/rem/{songId}")
  public Response remSavedSong(@PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return songService.remSavedSong(user, songId);
  }

  @GetMapping("/get/{songId}")
  public Response getSong(@PathVariable final int songId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return songService.getSong(songId);
  }

  @GetMapping("/genres")
  public Response getGenres() {
    return ResponseUtilities.filledSuccess(new GenresResponse());
  }

  @GetMapping("/manage/{songId}")
  public Response getManageSongInfo(@PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    } else if (!(user instanceof Artist)){
      return ResponseUtilities.filledFailure(ConfigConstants.NOT_AN_ARTIST);
    }
    boolean ownsSongs = false;
    for (Song song:((Artist) user).getOwnedSongs()){
      if(song.getId() == songId){
        ownsSongs = true;
      }
    }
    if(!ownsSongs){
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
    return songService.getManageSongInfo((Artist) user, songId);
  }
}
