package webspotify.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.*;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.users.Concert;
import webspotify.models.users.User;
import webspotify.posts.ConcertArtistChangeRequest;
import webspotify.repo.ArtistRepository;
import webspotify.repo.ConcertRepository;
import webspotify.repo.SongRepository;
import webspotify.responses.ConcertResponse;
import webspotify.responses.SongResponse;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongRepository songRepo;

    @GetMapping("/all")
    public Response getConcerts(HttpSession session) {
        if (session.getAttribute("User") == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        List<SongResponse> responseList = new ArrayList<SongResponse>();
        for (Song s : songRepo.findAll()) {
            responseList.add(new SongResponse(s));
        }
        return ResponseUtilities.filledSuccess(responseList);
    }

}
