package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.config.ConfigConstants;
import webspotify.models.media.SongQueue;
import webspotify.models.users.User;
import webspotify.services.QueueService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/queue")
public class QueueController {

  @Autowired
  private QueueService queueService;

  @GetMapping("/")
  public Response getQueueInformation(HttpSession session) {
    User currentUser = SessionUtilities.getUserFromSession(session);
    if (currentUser == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.retrieveEntireQueue(currentUser, queue);
  }

  @PostMapping("/add/song/{songId}")
  public Response addSongToQueue(@PathVariable final int songId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.addSongToQueue(queue, songId);
  }

  @PostMapping("/rem/song/{songId}")
  public Response removeSongFromQueue(@PathVariable final int songId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.removeSongFromQueue(queue, songId);
  }

  @GetMapping("/add/playlist/{playlistId}")
  public Response addPlaylistToQueue(@PathVariable final int playlistId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.addPlaylistToQueue(queue, playlistId);
  }

  @GetMapping("/add/album/{albumId}")
  public Response addAlbumToQueue(@PathVariable final int albumId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.addAlbumToQueue(queue, albumId);
  }

  @GetMapping("/add/savedSongs")
  public Response addSavedSongs(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.addSavedSongsToQueue(user, queue);
  }

  @PostMapping("/clear")
  public Response clearQueue(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.clearQueue(queue);
  }

  @GetMapping("/next")
  public Response getNextSong(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.getNextSong(queue);
  }

  @GetMapping("/prev")
  public Response getPrevSong(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.getPrevSong(queue);
  }

  @GetMapping("/set/repeat/current")
  public Response setRepeatCurrent(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.setRepeatCurrent(queue);
  }

  @GetMapping("/set/repeat/library")
  public Response setRepeatLibrary(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.setRepeatLibrary(queue);
  }

  @GetMapping("/set/repeat/none")
  public Response setRepeatNone(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.setRepeatNone(queue);
  }
}
