package webspotify.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
  public Response uploadUserImage(@RequestParam("file") MultipartFile file) {
    return uploadService.uploadUserImage(file);
  }
}
