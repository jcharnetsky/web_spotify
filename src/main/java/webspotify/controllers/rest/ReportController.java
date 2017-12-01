package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.config.ConfigConstants;
import webspotify.models.administration.Report;
import webspotify.models.users.Administrator;
import webspotify.models.users.User;
import webspotify.services.ReportService;
import webspotify.types.SpotifyObjectEnum;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

  @Autowired
  private ReportService reportService;

  @GetMapping("/all")
  public Response getReports(HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    } else if (!(u instanceof Administrator)){
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
    return reportService.getActiveReports();
  }

  @GetMapping("/reportNo/{reportId}")
  public Response getReport(@PathVariable final int reportId, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    } else if (!(u instanceof Administrator)){
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
    return reportService.getReport(reportId);
  }

  @PostMapping("/banContent/{reportId}/{contentType}/{contentId}")
  public Response handleReport(@PathVariable final int reportId,
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

  @PostMapping("/create")
  public Response postReport(@RequestBody Report report, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return reportService.postReport(u, report);
  }

  @PostMapping("/ignore/{reportId}")
  public Response ignoreReport(@PathVariable final int reportId, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    } else if (!(u instanceof Administrator)){
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
    return reportService.ignoreReport(reportId);
  }
}
