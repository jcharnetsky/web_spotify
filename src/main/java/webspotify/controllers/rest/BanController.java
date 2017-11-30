package webspotify.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webspotify.config.ConfigConstants;
import webspotify.models.users.Administrator;
import webspotify.models.users.User;
import webspotify.services.ReportService;
import webspotify.types.SpotifyObjectEnum;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/ban")
public class BanController {

  @Autowired
  private ReportService reportService;

  @PostMapping("/banContent/{reportId}/{contentType}/{contentId}")
  public Response banViewable(@PathVariable final int reportId,
                              @PathVariable final SpotifyObjectEnum contentType,
                              @PathVariable final int contentId,
                              HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    } else if (!(u instanceof Administrator)){
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
    return reportService.banContent(contentType, contentId, reportId);
  }
}
