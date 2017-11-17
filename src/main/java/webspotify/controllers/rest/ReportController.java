package webspotify.controllers.rest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.*;
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
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return reportService.getReports();
  }

  @GetMapping("/reportNo/{reportId}")
  public Response getReport(@PathVariable final int reportId, HttpSession session) {
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return reportService.getReport(reportId);
  }

  @PostMapping("/create")
  public Response postReport(@RequestBody Report report, HttpSession session) {
    User u = (User) session.getAttribute("User");
    if (u == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return reportService.postReport(u, report);
  }
}
