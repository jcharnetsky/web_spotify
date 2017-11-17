package webspotify.controllers.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.models.media.Song;
import webspotify.models.media.SongQueue;
import webspotify.repo.SongRepository;
import webspotify.responses.QueueResponse;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/queue")
public class QueueController {

  @Autowired
  private SongRepository songRepo;

  @GetMapping("/get/all")
  public Response getQueue(HttpSession session) {
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    //Creates some infinite loop, not sure why.
    SongQueue dummy = new SongQueue(); // Dummy data
    List<Song> songs = songRepo.findAll();
    for(Song song: songs){
      dummy.push(song);
    }
    dummy.pop();
    return ResponseUtilities.filledSuccess(new QueueResponse(dummy));
  }
}
