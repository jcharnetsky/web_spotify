package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import webspotify.responses.GenresResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@RestController
@RequestMapping("/api/home")
public class BrowseController {

  @GetMapping("/overview")
  public Response getOverviewData() {
    return ResponseUtilities.emptySuccess();
  }

  @GetMapping("/genres_moods")
  public Response getGenresMoodsData() {
    return ResponseUtilities.filledSuccess(new GenresResponse());
  }

  @GetMapping("/new_releases")
  public Response getNewReleasesData() {
    return ResponseUtilities.emptySuccess();
  }

  @GetMapping("/discover")
  public Response getDiscoverData(HttpSession session) {
    return ResponseUtilities.emptySuccess();
  }
}
