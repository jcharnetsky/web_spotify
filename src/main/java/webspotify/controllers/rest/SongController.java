package webspotify.controllers.rest;

import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import webspotify.config.ConfigConstants;
import webspotify.models.users.User;
import webspotify.responses.SongResponse;
import webspotify.services.SongService;
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
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    User user = SessionUtilities.getUserFromSession(session);
    return songService.getAllRelevantSongs(user);
  }

  @GetMapping("/saved/add/{SongId}")
  public Response addSavedSong(@PathVariable final int songId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    User user = SessionUtilities.getUserFromSession(session);
    return songService.addSavedSong(user, songId);
  }

  @GetMapping("/saved/rem/{SongId}")
  public Response remSavedSong(@PathVariable final int songId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    User user = SessionUtilities.getUserFromSession(session);
    return songService.remSavedSong(user, songId);
  }

  @GetMapping("/songNo/{songId}")
  public Response getSong(@PathVariable final int songId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return songService.getSong(songId);
  }
}
