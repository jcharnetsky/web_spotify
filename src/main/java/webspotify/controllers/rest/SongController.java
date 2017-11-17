package webspotify.controllers.rest;

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
import webspotify.Utilities.*;
import webspotify.models.media.Song;
import webspotify.responses.SongResponse;
import webspotify.services.SongService;

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
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    List<SongResponse> responseList = new ArrayList<SongResponse>();
    //for (Song s : songRepo.findAll()) {
    //  responseList.add(new SongResponse(s));
    //}
    return ResponseUtilities.filledSuccess(responseList);
  }

  @GetMapping("/songNo/{songId}")
  public Response getSong(@PathVariable final int songId, HttpSession session) {
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return songService.getSong(songId);
  }

  @GetMapping("/audio/{songId}.mp3")
  public HttpEntity<byte[]> getSongAudio(@PathVariable final int songId, HttpSession session) throws IOException {
    if (session.getAttribute("User") == null) {
      return null;
    }
    return songService.getSongAudio(songId);
  }
}
