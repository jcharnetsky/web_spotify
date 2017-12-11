package webspotify.controllers.rest;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import webspotify.config.ConfigConstants;
import webspotify.models.users.User;
import webspotify.services.UploadService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 *
 * @author Cardinals
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/upload")
public class UploadController {

  @Autowired
  UploadService uploadService;

  @PostMapping("/image/user")
  public Response uploadUserImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") int userId, HttpSession session) throws IOException {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return uploadService.uploadUserImage(file, userId);
  }
  
  @PostMapping("/image/playlist")
  public Response uploadPlaylistImage(@RequestParam("file") MultipartFile file, @RequestParam("playlistId") int playlistId, HttpSession session) throws IOException {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return uploadService.uploadPlaylistImage(file, playlistId);
  }
  
  @PostMapping("/image/album")
  public Response uploadAlbumImage(@RequestParam("file") MultipartFile file, @RequestParam("albumId") int albumId, HttpSession session) throws IOException {
    User user = SessionUtilities.getUserFromSession(session);
    if(user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return uploadService.uploadAlbumImage(file, albumId);
  }
  
  @PostMapping("/audio")
  public Response uploadAudio(@RequestParam("file") MultipartFile file, @RequestParam("id") int id, HttpSession session) throws IOException {
    User user = SessionUtilities.getUserFromSession(session);
    if(user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return uploadService.uploadAudio(file, id);
  }
}
