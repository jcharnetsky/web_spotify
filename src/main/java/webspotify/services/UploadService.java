package webspotify.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

import javax.transaction.Transactional;

/**
 *
 * @author Cardinals
 */
@Service
public class UploadService {

  @Transactional
  public Response uploadUserImage(MultipartFile file) {
    String imageURL = "../images/default-user.png";
    return ResponseUtilities.filledSuccess(imageURL);
  }
  
}
