package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;
import webspotify.config.ConfigConstants;
import webspotify.models.users.User;
import webspotify.posts.PlaylistCreateRequest;
import webspotify.services.PlaylistService;

/**
 *
 * @author Cardinals
 */
//@RestController
//@RequestMapping("/api/albums")
public class AlbumController {

  @Autowired
  PlaylistService albumService;

  @GetMapping("/")
  public Response getPlaylistsToUser(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return albumService.getAllRelevantPlaylists(user);
  }

  @GetMapping("/{playlistId}/get/info")
  public Response getPlaylistInfo(@PathVariable final int playlistId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return albumService.getInfoAboutPlaylist(user, playlistId);
  }

  @GetMapping("/{playlistId}/add/song/{songId}")
  public Response addSongToPlaylist(@PathVariable final int playlistId, @PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return albumService.addSongToPlaylist(user, playlistId, songId);
  }

  @GetMapping("/{playlistId}/rem/song/{songId}")
  public Response remSongFromPlaylist(@PathVariable final int playlistId, @PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return albumService.removeSongFromPlaylist(user, playlistId, songId);
  }

  @GetMapping("/{playlistId}/delete")
  public Response deleteCollection(@PathVariable final int playlistId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return albumService.deletePlaylist(user, playlistId);
  }

  @GetMapping("/{playlistId}/follow")
  public Response savePlaylistToUser(@PathVariable final int playlistId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return albumService.followPlaylist(user, playlistId);
  }

  @GetMapping("/{playlistId}/unfollow")
  public Response unsaveCollectionToUser(@PathVariable final int playlistId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return albumService.unfollowPlaylist(user, playlistId);
  }

  @PostMapping("/create")
  public Response createPlaylist(@RequestBody PlaylistCreateRequest request, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return albumService.createPlaylistCollection(user, request);
  }
  
}
