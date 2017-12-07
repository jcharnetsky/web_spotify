package webspotify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import webspotify.config.ConfigConstants;
import webspotify.models.users.User;
import webspotify.repo.UserRepository;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/**
 *
 * @author Cardinals
 */
@Service
public class UploadService {

  @Autowired
  private HttpServletRequest request;
  @Autowired
  private UserRepository userRepository;
  
  @Transactional
  public Response uploadUserImage(MultipartFile file, int userId) throws IOException {
    String realUploadPath = request.getServletContext().getRealPath(ConfigConstants.USER_UPLOAD_PATH);
    String fileName = userId + ConfigConstants.JPEG;
    String filePath = realUploadPath + fileName;
    File dest = new File(filePath);
    file.transferTo(dest);
    User user = userRepository.findOne(userId);
    user.setHasImage(true);
    userRepository.save(user);
    StringBuffer currentURL = request.getRequestURL();
    String[] splitOne = currentURL.toString().split("://");
    String[] splitTwo = splitOne[1].split("/");
    String desiredSrc = splitOne[0] + "://" + splitTwo[0] + ConfigConstants.USER_UPLOAD_PATH + fileName + "?x=" + new Date().getTime();
    return ResponseUtilities.filledSuccess(desiredSrc);
  }
  
  @Transactional
  public Response uploadPlaylistImage(MultipartFile file, int playlistId) throws IOException {
    String realUploadPath = request.getServletContext().getRealPath(ConfigConstants.PLAYLIST_UPLOAD_PATH);
    String fileName = playlistId + ConfigConstants.JPEG;
    String filePath = realUploadPath + fileName;
    File dest = new File(filePath);
    file.transferTo(dest);
    StringBuffer currentURL = request.getRequestURL();
    String[] splitOne = currentURL.toString().split("://");
    String[] splitTwo = splitOne[1].split("/");
    String desiredSrc = splitOne[0] + "://" + splitTwo[0] + ConfigConstants.PLAYLIST_UPLOAD_PATH + fileName + "?x=" + new Date().getTime();
    return ResponseUtilities.filledSuccess(desiredSrc);
  }
  
  @Transactional
  public Response uploadAlbumImage(MultipartFile file, int albumId) throws IOException {
    String realUploadPath = request.getServletContext().getRealPath(ConfigConstants.ALBUM_UPLOAD_PATH);
    String fileName = albumId + ConfigConstants.JPEG;
    String filePath = realUploadPath + fileName;
    File dest = new File(filePath);
    file.transferTo(dest);
    StringBuffer currentURL = request.getRequestURL();
    String[] splitOne = currentURL.toString().split("://");
    String[] splitTwo = splitOne[1].split("/");
    String desiredSrc = splitOne[0] + "://" + splitTwo[0] + ConfigConstants.ALBUM_UPLOAD_PATH + fileName + "?x=" + new Date().getTime();
    return ResponseUtilities.filledSuccess(desiredSrc);
  }
  
  @Transactional
  public Response uploadAudio(MultipartFile file, int id) throws IOException {
    String realUploadPath = request.getServletContext().getRealPath(ConfigConstants.AUDIO_UPLOAD_PATH);
    String fileName = id + ConfigConstants.MP3;
    String filePath = realUploadPath + fileName;
    File dest = new File(filePath);
    file.transferTo(dest);;
    String desiredSrc = ".." + ConfigConstants.AUDIO_UPLOAD_PATH + fileName;
    return ResponseUtilities.filledSuccess(desiredSrc);
  }
  
}
