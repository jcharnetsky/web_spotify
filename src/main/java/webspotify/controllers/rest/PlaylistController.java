package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Playlist;
import webspotify.models.users.User;
import webspotify.posts.PlaylistCreateRequest;
import webspotify.responses.PlaylistInfoResponse;
import webspotify.services.PlaylistService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

  @Autowired
  PlaylistService playlistService;

  @GetMapping("/")
  public Response getPlaylistsToUser(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return playlistService.getAllRelevantPlaylists(user);
  }

  @GetMapping("/{playlistId}/get/info")
  public Response getPlaylistInfo(@PathVariable final int playlistId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    for (Playlist list: user.getOwnedPlaylists()){
      if(list.getId() == playlistId) {
        return ResponseUtilities.filledSuccess(new PlaylistInfoResponse(list));
      }
    }
    return playlistService.getInfoAboutPlaylist(user, playlistId);
  }

  @PostMapping("/{playlistId}/add/song/{songId}")
  public Response addSongToPlaylist(@PathVariable final int playlistId, @PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return playlistService.addSongToPlaylist(user, playlistId, songId);
  }

  @PostMapping("/{playlistId}/rem/song/{songId}")
  public Response remSongFromPlaylist(@PathVariable final int playlistId, @PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return playlistService.removeSongFromPlaylist(user, playlistId, songId);
  }

  @PostMapping("/{playlistId}/delete")
  public Response deleteCollection(@PathVariable final int playlistId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return playlistService.deletePlaylist(user, playlistId);
  }

  @PostMapping("/{playlistId}/edit")
  public Response editCollection(@PathVariable final int playlistId,
                                 @RequestBody PlaylistCreateRequest request, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return playlistService.editPlaylist(user, playlistId, request);
  }

  @GetMapping("/{playlistId}/follow")
  public Response savePlaylistToUser(@PathVariable final int playlistId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return playlistService.followPlaylist(user, playlistId);
  }

  @GetMapping("/{playlistId}/unfollow")
  public Response unsaveCollectionToUser(@PathVariable final int playlistId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return playlistService.unfollowPlaylist(user, playlistId);
  }

  @PostMapping("/create")
  public Response createPlaylist(@RequestBody PlaylistCreateRequest request, HttpSession session) {
    System.out.println(request.getGenre());
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in");
    }
    return playlistService.createPlaylist(user, request);
  }

}
