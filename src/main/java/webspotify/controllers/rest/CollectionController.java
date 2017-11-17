package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.Utilities.SessionUtilities;
import webspotify.models.users.User;
import webspotify.posts.PlaylistChangeSongRequest;
import webspotify.services.SongCollectionService;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/collections")
public class CollectionController {

  @Autowired
  SongCollectionService collectionService;

  @GetMapping("/{collectionId}/get/songs")
  public Response getCollectionSongs(@PathVariable final int collectionId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return collectionService.getSongsInCollection(user, collectionId);
  }

  @GetMapping("/{collectionId}/get/info")
  public Response getCollectionInfo(@PathVariable final int collectionId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return collectionService.getInfoAboutCollection(user, collectionId);
  }

  @GetMapping("/{collectionId}/add/song/{songId}")
  public Response addSongToCollection(@PathVariable final int collectionId, @PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return collectionService.addSongFromCollection(user, collectionId, songId);
  }

  @GetMapping("/{collectionId}/rem/song/{songId}")
  public Response remSongToCollection(@PathVariable final int collectionId, @PathVariable final int songId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return collectionService.removeSongFromCollection(user, collectionId, songId);
  }

}
