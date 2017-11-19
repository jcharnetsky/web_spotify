package webspotify.controllers.rest;

import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.config.ConfigConstants;
import webspotify.models.administration.Report;
import webspotify.models.users.User;
import webspotify.services.ReportService;

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
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return reportService.getReports();
  }

  @GetMapping("/reportNo/{reportId}")
  public Response getReport(@PathVariable final int reportId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return reportService.getReport(reportId);
  }

  @PostMapping("/create")
  public Response postReport(@RequestBody Report report, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return reportService.postReport(u, report);
  }
}
