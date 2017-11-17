package webspotify.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.*;
import webspotify.models.users.Artist;
import webspotify.models.media.Concert;
import webspotify.models.users.User;
import webspotify.posts.ConcertArtistChangeRequest;
import webspotify.repo.ArtistRepository;
import webspotify.repo.ConcertRepository;
import webspotify.repo.UserRepository;
import webspotify.responses.ConcertResponse;
import webspotify.services.ConcertService;

/**
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/concerts")
public class ConcertController {
  @Autowired
  private ConcertService concertService;

  @GetMapping("/all")
  public Response getConcerts(HttpSession session) {
    if(SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return concertService.getConcerts();
  }

  @GetMapping("/concertNo/{concertId}")
  public Response getConcert(@PathVariable final int concertId, HttpSession session) {
    if(SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return concertService.getConcert(concertId);
  }

  @PostMapping("/addArtist")
  public Response addArtistToConcert(@RequestBody ConcertArtistChangeRequest change, HttpSession session) {
    if(SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return concertService.addArtistToConcert(change);
  }

  @PostMapping("/remArtist")
  public Response remArtistFromConcert(@RequestBody ConcertArtistChangeRequest change, HttpSession session) {
    if(SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return concertService.removeArtistFromConcert(change);
  }

  @PostMapping("/remConcert")
  public Response removeConcert(@RequestBody Concert concert, HttpSession session) {
    if(SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return concertService.removeConcert(concert);
  }
  
  @PostMapping("/create")
  public Response postConcert(@RequestBody Concert concert, HttpSession session) {
    if(SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    return concertService.postConcert(concert);
  }
}
