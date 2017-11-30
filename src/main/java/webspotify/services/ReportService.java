package webspotify.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.interfaces.Viewable;
import webspotify.models.administration.Report;
import webspotify.models.media.Album;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.users.User;
import webspotify.repo.*;
import webspotify.responses.ReportResponse;
import webspotify.types.SpotifyObjectEnum;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("reportService")
public class ReportService {

  @Autowired
  ReportRepository reportRepository;
  @Autowired
  SongRepository songRepository;
  @Autowired
  PlaylistRepository playlistRepository;
  @Autowired
  AlbumRepository albumRepository;
  @Autowired
  UserRepository userRepository;

  @Transactional
  public Response getActiveReports() {
    List<Report> reports = reportRepository.findAll();
    List<ReportResponse> responses = new ArrayList<ReportResponse>();
    for (Report report: reports){
      if(!report.getCompleted()) {
        responses.add(new ReportResponse(report));
      }
    }
    return ResponseUtilities.filledSuccess(responses);
  }

  @Transactional
  public Response getReport(final int reportId) {
    Report report = reportRepository.findOne(reportId);
    if (report == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.REPORT_NO_EXIST);
    } else {
      return ResponseUtilities.filledSuccess(new ReportResponse(report));
    }
  }

  @Transactional
  public Response postReport(User user, Report report) {
    report.setCreator(user);
    report.setReportId(0);
    Report resp = reportRepository.saveAndFlush(report);
    if (resp == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
    } else {
      return ResponseUtilities.emptySuccess();
    }
  }

  @Transactional
  public Response ignoreReport(int reportId){
    if (!reportRepository.exists(reportId)) {
      return ResponseUtilities.filledFailure(ConfigConstants.REPORT_NO_EXIST);
    }
    Report report = reportRepository.findOne(reportId);
    report.setCompleted(false);
    reportRepository.save(report);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response banContent(SpotifyObjectEnum contentType, int contentId) {
    Viewable toBan;
    if(contentType == SpotifyObjectEnum.SONG){
      toBan = songRepository.findOne(contentId);
    } else if (contentType == SpotifyObjectEnum.ALBUM) {
      toBan = albumRepository.findOne(contentId);
    } else if (contentType == SpotifyObjectEnum.PLAYLIST) {
      toBan = playlistRepository.findOne(contentId);
    } else if (contentType == SpotifyObjectEnum.USER) {
      toBan = userRepository.findOne(contentId);
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.UNKNOWN_TYPE);
    }
    toBan.setBanned(true);
    if(contentType == SpotifyObjectEnum.SONG){
      songRepository.save((Song) toBan);
    } else if (contentType == SpotifyObjectEnum.ALBUM) {
      albumRepository.save((Album) toBan);
    } else if (contentType == SpotifyObjectEnum.PLAYLIST) {
      playlistRepository.save((Playlist) toBan);
    } else if (contentType == SpotifyObjectEnum.USER) {
      userRepository.save((User) toBan);
    }
    return ResponseUtilities.emptySuccess();
  }
}
