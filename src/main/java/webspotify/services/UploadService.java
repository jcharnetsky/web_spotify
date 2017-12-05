package webspotify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import webspotify.models.users.User;
import webspotify.repo.UserRepository;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

import java.io.File;
import java.io.IOException;

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
    String uploadPath = "/images/users/";
    String realUploadPath = request.getServletContext().getRealPath(uploadPath);
    String fileName = userId + ".jpg";
    String filePath = realUploadPath + fileName;
    File dest = new File(filePath);
    file.transferTo(dest);
    String desiredSrc = ".."+ uploadPath + fileName;
    User user = userRepository.findOne(userId);
    user.setHasImage(true);
    userRepository.save(user);
    return ResponseUtilities.filledSuccess(desiredSrc);
  }
  
  @Transactional
  public Response uploadPlaylistImage(MultipartFile file, int playlistId) throws IOException {
    String uploadPath = "/images/playlists/";
    String realUploadPath = request.getServletContext().getRealPath(uploadPath);
    String fileName = playlistId + ".jpg";
    String filePath = realUploadPath + fileName;
    File dest = new File(filePath);
    file.transferTo(dest);
    String desiredSrc = ".." + uploadPath + fileName;
    return ResponseUtilities.filledSuccess(desiredSrc);
  }
  
}
