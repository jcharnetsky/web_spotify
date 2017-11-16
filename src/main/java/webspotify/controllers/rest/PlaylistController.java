package webspotify.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.*;
import webspotify.models.administration.Report;
import webspotify.models.media.Playlist;
import webspotify.models.users.User;
import webspotify.repo.PlaylistRepository;
import webspotify.responses.PlaylistResponse;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepo;

    @GetMapping("/getData/{playlistId}")
    public Response getPlaylistData(@PathVariable final int playlistId, HttpSession session) {
        if (session.getAttribute("User") == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        List<PlaylistResponse> response = new ArrayList<PlaylistResponse>();
        Playlist playlist = playlistRepo.findOne(playlistId);
        if(playlist == null) {
            return ResponseUtilities.filledFailure("Playlist Does not exist");
        }
        response.add(new PlaylistResponse(playlist));
        return ResponseUtilities.filledSuccess(response);
    }

    @GetMapping("/getAll")
    public Response getAllData(HttpSession session) {
        if (session.getAttribute("User") == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        List<PlaylistResponse> response = new ArrayList<PlaylistResponse>();
        for (Playlist playlist : playlistRepo.findAll()) {
            response.add(new PlaylistResponse(playlist));
        }
        return ResponseUtilities.filledSuccess(response);
    }

}
