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
import webspotify.models.users.Administrator;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.posts.AlbumCreateRequest;
import webspotify.services.AlbumService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

  @Autowired
  AlbumService albumService;

  @GetMapping("/")
  public Response getAlbumsToUser(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return albumService.getAllRelevantAlbums(user);
  }

  @GetMapping("/{albumId}/get/info")
  public Response getAlbumInfo(@PathVariable final int albumId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return albumService.getInfoAboutAlbum(user, albumId);
  }

  @GetMapping("/{albumId}/add/song/{songId}")
  public Response addSongToAlbum(@PathVariable final int albumId, @PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return albumService.addSongToAlbum(user, albumId, songId);
  }

  @GetMapping("/{albumId}/rem/song/{songId}")
  public Response remSongFromAlbum(@PathVariable final int albumId, @PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return albumService.removeSongFromAlbum(user, albumId, songId);
  }

  @GetMapping("/{albumId}/delete")
  public Response deleteCollection(@PathVariable final int albumId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return albumService.deleteAlbum(user.getId(), albumId);
  }

  @PostMapping("/{albumId}/edit")
  public Response editAlbum(@PathVariable final int albumId,
                                 @RequestBody AlbumCreateRequest request, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    } else if (!(user instanceof Artist || user instanceof Administrator)){
      return ResponseUtilities.filledFailure(ConfigConstants.NOT_AN_ARTIST);
    }
    return albumService.editAlbum(user, albumId, request);
  }

  @PostMapping("/{albumId}/save")
  public Response saveAlbumToUser(@PathVariable final int albumId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return albumService.saveAlbum(user, albumId);
  }

  @PostMapping("/{albumId}/unsave")
  public Response unsaveCollectionToUser(@PathVariable final int albumId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return albumService.unsaveAlbum(user, albumId);
  }

  @PostMapping("/create")
  public Response createAlbum(@RequestBody AlbumCreateRequest request, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return albumService.createAlbumCollection(user, request);
  }

}
