package webspotify.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.*;
import webspotify.models.administration.Report;
import webspotify.models.users.User;
import webspotify.repo.ReportRepository;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

	@Autowired
	private ReportRepository reportRepo;

	@GetMapping("/all")
	public Response getReports(HttpSession session) {
		if (session.getAttribute("User") == null) {
			return ResponseUtilities.filledFailure("User is not logged in.");
		}
		return ResponseUtilities.filledSuccess(reportRepo.findAll());
	}

	@GetMapping("/reportNo/{reportId}")
	public Response getReport(@PathVariable final int reportId, HttpSession session) {
		if (session.getAttribute("User") == null) {
			return ResponseUtilities.filledFailure("User is not logged in.");
		}
		Report r = reportRepo.findOne(reportId);
		if (r == null) {
			return ResponseUtilities.filledFailure("Report does not exist.");
		} else {
			List<Report> contentBody = new ArrayList<Report>();
			contentBody.add(r);
			return ResponseUtilities.filledSuccess(contentBody);
		}
	}

	@PostMapping("/create")
	public Response postReport(@RequestBody Report report, HttpSession session) {
		User u = (User)session.getAttribute("User");
		if (u == null) {
			return ResponseUtilities.filledFailure("User is not logged in.");
		}
		report.setCreator(u);
		report.setReportId(0);
		Report resp = reportRepo.saveAndFlush(report);
		if (resp == null) {
			return ResponseUtilities.filledFailure("Could not log report.");
		} else {
			return ResponseUtilities.emptySuccess();
		}
	}
}
