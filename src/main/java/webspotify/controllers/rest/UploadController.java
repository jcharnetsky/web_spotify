package webspotify.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import webspotify.services.UploadService;
import webspotify.utilities.Response;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

  @Autowired
  UploadService uploadService;

  @PostMapping("/image/user")
  public Response uploadUserImage(@RequestBody MultipartFile file) {
    return uploadService.uploadUserImage(file);
  }
}
