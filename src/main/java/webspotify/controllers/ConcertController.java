package webspotify.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.*;
import webspotify.models.users.Artist;
import webspotify.models.users.Concert;
import webspotify.models.users.User;
import webspotify.posts.ConcertArtistChangeRequest;
import webspotify.repo.ArtistRepository;
import webspotify.repo.ConcertRepository;
import webspotify.responses.ConcertResponse;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

	@Autowired
	private ConcertRepository concertRepo;

	@Autowired
	private ArtistRepository artistRepo;

	@GetMapping("/all")
	public Response getConcerts(HttpSession session) {
		if (session.getAttribute("User") == null) {
			return ResponseUtilities.filledFailure("User is not logged in.");
		}
		List<ConcertResponse> responseList = new ArrayList<ConcertResponse>();
		for (Concert c : concertRepo.findAll()) {
			responseList.add(new ConcertResponse(c));
		}
		return ResponseUtilities.filledSuccess(responseList);
	}

	@GetMapping("/conertNo/{conertId}")
	public Response getConcert(@PathVariable final int concertId, HttpSession session) {
		if (session.getAttribute("User") == null) {
			return ResponseUtilities.filledFailure("User is not logged in.");
		}
		Concert concertToFind = concertRepo.findOne(concertId);
		if (concertToFind == null) {
			return ResponseUtilities.filledFailure("Concert does not exist.");
		} else {
			List<Concert> contentBody = new ArrayList<Concert>();
			contentBody.add(concertToFind);
			return ResponseUtilities.filledSuccess(contentBody);
		}
	}

	@PostMapping("/addArtist")
	public Response addArtistToConcert(@RequestBody ConcertArtistChangeRequest change, HttpSession session) {
		if (session.getAttribute("User") == null) {
			return ResponseUtilities.filledFailure("User is not logged in.");
		}
		Concert c = concertRepo.findOne(change.getConcertID());
		Artist a = artistRepo.findOne(change.getArtistID());
		if (c == null) {
			return ResponseUtilities.filledFailure("Concert Does Not Exist.");
		}
		if (a == null) {
			return ResponseUtilities.filledFailure("Artist Does Not Exist.");
		}

		if (!c.getArtists().contains(a)) {
			c.getArtists().add(a);
			concertRepo.save(c);
			return ResponseUtilities.emptySuccess();
		} else {
			return ResponseUtilities.filledFailure("Artist Could not be added.");
		}
	}

	@PostMapping("/remArtist")
	public Response remArtistFromConcert(@RequestBody ConcertArtistChangeRequest change, HttpSession session) {
		if (session.getAttribute("User") == null) {
			return ResponseUtilities.filledFailure("User is not logged in.");
		}
		Concert c = concertRepo.findOne(change.getConcertID());
		Artist a = artistRepo.findOne(change.getArtistID());
		if (c == null) {
			return ResponseUtilities.filledFailure("Concert Does Not Exist.");
		}
		if (a == null) {
			return ResponseUtilities.filledFailure("Artist Does Not Exist.");
		}
		if (c.getArtists().contains(a)) {
			c.getArtists().remove(a);
			concertRepo.save(c);
			return ResponseUtilities.emptySuccess();
		} else {
			return ResponseUtilities.filledFailure("Artist Could not be removed.");
		}

	}

	@PostMapping("/create")
	public Response postConcert(@RequestBody Concert concert, HttpSession session) {
		User u = (User) session.getAttribute("User");
		if (u == null) {
			return ResponseUtilities.filledFailure("User is not logged in.");
		}
		concert.setId(null);
		concert.setIsPublic(true);
		concert.setIsBanned(false);
		Concert resp = concertRepo.saveAndFlush(concert);
		if (resp == null) {
			return ResponseUtilities.filledFailure("Could not create concert.");
		} else {
			return ResponseUtilities.emptySuccess();
		}
	}
}
