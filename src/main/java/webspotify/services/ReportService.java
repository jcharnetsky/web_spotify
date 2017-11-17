package webspotify.services;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.models.administration.Report;
import webspotify.models.users.User;
import webspotify.repo.ReportRepository;

@Service
public class ReportService {
  @Autowired 
  ReportRepository reportRepository;
  
  @Transactional 
  public Response getReports() {
    return ResponseUtilities.filledSuccess(reportRepository.findAll());
  }
  
  @Transactional
  public Response getReport(final int reportId) {
    Report report = reportRepository.findOne(reportId);
    if (report == null) {
      return ResponseUtilities.filledFailure("Report does not exist.");
    } 
    else {
      List<Report> contentBody = new ArrayList<Report>();
      contentBody.add(report);
      return ResponseUtilities.filledSuccess(contentBody);
    }
  }
  
  @Transactional
  public Response postReport(User user, Report report) {
    report.setCreator(user);
    report.setReportId(0);
    Report resp = reportRepository.saveAndFlush(report);
    if (resp == null) {
      return ResponseUtilities.filledFailure("Could not log report.");
    } else {
      return ResponseUtilities.emptySuccess();
    }
  }
}

