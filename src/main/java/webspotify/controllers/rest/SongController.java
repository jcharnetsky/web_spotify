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
import webspotify.repo.SongRepository;
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

    @GetMapping("/songNo/{songId}")
    public Response getSong(@PathVariable final int songId, HttpSession session) {
        if (session.getAttribute("User") == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        Song songToFind = songRepo.findOne(songId);
        if (songToFind == null) {
            return ResponseUtilities.filledFailure("Song does not exist.");
        } else {
            List<SongResponse> contentBody = new ArrayList<SongResponse>();
            contentBody.add(new SongResponse(songToFind));
            return ResponseUtilities.filledSuccess(contentBody);
        }
    }

    @GetMapping("/audio/{songId}.mp3")
    public HttpEntity<byte[]> getSongAudio(@PathVariable final int songId, HttpSession session, HttpServletResponse response) throws IOException {
//        if (session.getAttribute("User") == null) {
//            return;
//        }
        Song songToFind = songRepo.findOne(songId);
//        if (songToFind == null) {
//            return;
//        }
//        if (songToFind.getAudio() == null || songToFind.getAudio().length == 0) {
//            return;
//        }
        MediaType type = MediaType.parseMediaType("audio/mpeg3");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type);
        headers.setContentLength(songToFind.getAudio().length);
        
        return new HttpEntity<byte[]>(songToFind.getAudio(), headers);
    }

}
