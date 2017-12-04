package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.models.users.User;
import webspotify.responses.GenresResponse;
import webspotify.services.AlbumService;
import webspotify.services.SearchService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

@RestController
@RequestMapping("/api/home")
public class BrowseController {

  @Autowired
  AlbumService albumService;

  @Autowired
  SearchService searchService;

  @GetMapping("/overview")
  public Response getOverviewData() {
    return ResponseUtilities.emptySuccess();
  }

  @GetMapping("/genres_moods")
  public Response getGenresMoodsData() {
    return ResponseUtilities.filledSuccess(new GenresResponse());
  }

  @GetMapping("/new_releases")
  public Response getNewReleasesData(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return albumService.getNewReleases();
  }

  @GetMapping("/discover")
  public Response getDiscoverData(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure("User not logged in.");
    }
    return searchService.getDiscover(user.getId());
  }
}
