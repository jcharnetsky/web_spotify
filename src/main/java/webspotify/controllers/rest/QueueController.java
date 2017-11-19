package webspotify.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webspotify.services.QueueService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.models.media.Song;
import webspotify.models.media.SongQueue;
import webspotify.repo.SongRepository;
import webspotify.responses.QueueResponse;

import javax.servlet.http.HttpSession;
import java.util.List;

import webspotify.config.ConfigConstants;
import webspotify.utilities.SessionUtilities;

/**
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/queue")
public class QueueController {

  @Autowired
  private QueueService queueService;

//  @GetMapping("/get/all")
//  public Response getQueue(HttpSession session) {
//    if (SessionUtilities.getUserFromSession(session) == null) {
//      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
//    }
//    //Creates some infinite loop, not sure why.
//    SongQueue dummy = new SongQueue(); // Dummy data
//    List<Song> songs = songRepo.findAll();
//    for (Song song : songs) {
//      dummy.push(song);
//    }
//    dummy.pop();
//    return ResponseUtilities.filledSuccess(new QueueResponse(dummy));
//  }

  @GetMapping("/")
  public Response getQueueInformation(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.retrieveEntireQueue(queue);
  }

  @GetMapping("/add/song/{songId}")
  public Response addSongToQueue(@PathVariable final int songId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    SongQueue queue = SessionUtilities.getSongQueueFromSession(session);
    return queueService.addSongToQueue(queue, songId);
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
