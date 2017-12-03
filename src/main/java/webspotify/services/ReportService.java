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
import webspotify.models.users.Administrator;
import webspotify.models.users.User;
import webspotify.posts.HandleReportRequest;
import webspotify.repo.*;
import webspotify.responses.ReportResponse;
import webspotify.types.ReportTypes;
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
    Report resp = reportRepository.save(report);
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
  public Response handleBan(Administrator admin, Integer reportId, Viewable target) {
    if(target instanceof Song){
      target.setBanned(true);
      songRepository.save((Song) target);
    } else if (target instanceof User){
      target.setBanned(true);
      userRepository.save((User) target);
    } else if (target instanceof Album){
      Album album = (Album) target;
      List<Song> songs = album.getSongsInAlbum();
      album.setBanned(true);
      for (Song song: songs){
        song.setBanned(true);
      }
      albumRepository.save(album);
    } else if (target instanceof Playlist){
      target.setBanned(true);
      playlistRepository.save((Playlist) target);
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ENTITY_TYPE_NO_EXIST);
    }
    // Generate Unban report to unban anything that was banned
    Report banReport = reportRepository.findOne(reportId);
    Report report = new Report();
    report.setSubject(banReport.getSubject());
    report.setDescription(ConfigConstants.ORIGINALLY_BANNED + banReport.getDescription());
    report.setEntityType(banReport.getEntityType());
    report.setEntityId(banReport.getEntityId());
    report.setCompleted(false);
    report.setReportType(ReportTypes.UNBAN);
    reportRepository.save(report);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response handleUnban(Viewable target) {
    if(target instanceof Song){
      Song song = (Song) target;
      if(song.getAlbum().isBanned()){
        return ResponseUtilities.filledFailure(ConfigConstants.CANNOT_UNBAN_SONG);
      }
      song.setBanned(false);
      songRepository.save(song);
    } else if (target instanceof User){
      target.setBanned(false);
      userRepository.save((User) target);
    } else if (target instanceof Album){
      Album album = (Album) target;
      List<Song> songs = album.getSongsInAlbum();
      album.setBanned(false);
      for (Song song: songs){
        song.setBanned(false);
      }
      albumRepository.save(album);
    } else if (target instanceof Playlist){
      target.setBanned(false);
      playlistRepository.save((Playlist) target);
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ENTITY_TYPE_NO_EXIST);
    }
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response handleRemove(Viewable target) {
    if(target instanceof Song){
      songRepository.delete((Song) target);
    } else if (target instanceof User){
      User user = (User) target;
      user.setIsDeleted(true);
      userRepository.save(user);
    } else if (target instanceof Album){
      albumRepository.delete((Album) target);
    } else if (target instanceof Playlist){
      playlistRepository.delete((Playlist) target);
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ENTITY_TYPE_NO_EXIST);
    }
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response handleAdd(Viewable target) {
    if(target instanceof Song){
      Song song = (Song) target;
      if(song.getAlbum().isBanned()){
       return ResponseUtilities.filledFailure(ConfigConstants.CANNOT_ADD_SONG);
      }
      song.setBanned(false);
      songRepository.save(song);
    } else if (target instanceof User){
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
    } else if (target instanceof Album){
      Album album = (Album) target;
      List<Song> songs = album.getSongsInAlbum();
      album.setBanned(false);
      for (Song song: songs){
        song.setBanned(false);
      }
      albumRepository.save(album);
    } else if (target instanceof Playlist){
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ENTITY_TYPE_NO_EXIST);
    }
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Viewable getReportEntity(HandleReportRequest request){
    if(!reportRepository.exists(request.getReportId())){
      return null;
    }
    Viewable toHandle;
    if(request.getEntityType() == SpotifyObjectEnum.SONG){
      toHandle = songRepository.findOne(request.getEntityId());
    } else if(request.getEntityType() == SpotifyObjectEnum.USER) {
      toHandle = userRepository.findOne(request.getEntityId());
    } else if(request.getEntityType() == SpotifyObjectEnum.ALBUM) {
      toHandle = albumRepository.findOne(request.getEntityId());
    } else if(request.getEntityType() == SpotifyObjectEnum.PLAYLIST) {
      toHandle = playlistRepository.findOne(request.getEntityId());
    } else {
      return null;
    }
    return toHandle;
  }
}
