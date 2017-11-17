package webspotify.controllers.rest;

import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import webspotify.config.ConfigConstants;
import webspotify.models.media.Song;
import webspotify.responses.SongResponse;
import webspotify.services.SongService;
import webspotify.utilities.SessionUtilities;

/**
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/songs")
public class SongController {

  @Autowired
  private SongService songService;

  @GetMapping("/all")
  public Response getSongs(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    List<SongResponse> responseList = new ArrayList<SongResponse>();
    //for (Song s : songRepo.findAll()) {
    //  responseList.add(new SongResponse(s));
    //}
    return ResponseUtilities.filledSuccess(responseList);
  }

  @GetMapping("/songNo/{songId}")
  public Response getSong(@PathVariable final int songId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return songService.getSong(songId);
  }

  @GetMapping("/audio/{songId}.mp3")
  public HttpEntity<byte[]> getSongAudio(@PathVariable final int songId, HttpSession session) throws IOException {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return null;
    }
    return songService.getSongAudio(songId);
  }
}
