package webspotify.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import webspotify.services.UploadService;

/**
 *
 * @author Cardinals
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

  @Autowired
  UploadService uploadService;

  @PostMapping("/image/user")
  public void uploadUserImage(@RequestBody MultipartFile file) {
    uploadService.uploadUserImage(file);
  }
}
