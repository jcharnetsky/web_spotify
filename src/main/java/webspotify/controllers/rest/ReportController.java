package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.config.ConfigConstants;
import webspotify.interfaces.Viewable;
import webspotify.models.administration.Report;
import webspotify.models.users.Administrator;
import webspotify.models.users.User;
import webspotify.posts.HandleReportRequest;
import webspotify.services.ReportService;
import webspotify.types.ReportTypes;
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

  @PostMapping("/handle")
  public Response handleReport(@RequestBody HandleReportRequest request,
                              HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    } else if (!(user instanceof Administrator)){
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
    Viewable reportTarget = reportService.getReportEntity(request);
    if(reportTarget == null){
      return ResponseUtilities.filledFailure(ConfigConstants.ENTITY_NO_EXIST);
    }
    ReportTypes reportType = request.getReportType();
    try {
      if (reportType == ReportTypes.BAN) {
        reportService.handleBan((Administrator) user, request.getReportId(), reportTarget);
      } else if (reportType == ReportTypes.UNBAN) {
        reportService.handleUnban(reportTarget);
      } else if (reportType == ReportTypes.REMOVE) {
        reportService.handleRemove(reportTarget);
      } else if (reportType == ReportTypes.ADD) {
        reportService.handleAdd(reportTarget);
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.REPORT_TYPE_NO_EXIST);
      }
    } catch (Exception e){
      return ResponseUtilities.filledFailure(e.getMessage());
    }
    return reportService.completeReport(request.getReportId());
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
