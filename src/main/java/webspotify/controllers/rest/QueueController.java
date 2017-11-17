package webspotify.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.models.media.Song;
import webspotify.responses.SongResponse;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/queue")
public class QueueController {

  @GetMapping("/get/all")
  public Response getQueue(HttpSession session) {
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    QueueResponse response = new QueueResponse(session.getAttribute("Queue"));
    return ResponseUtilities.filledSuccess(response);
  }
}
