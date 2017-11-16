package webspotify.controllers.rest;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.*;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.models.users.User;
import webspotify.posts.PlaylistChangeSongRequest;
import webspotify.posts.PlaylistCreateRequest;
import webspotify.repo.PlaylistRepository;
import webspotify.repo.SongRepository;
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
  @Autowired
  private SongRepository songRepo;

  @GetMapping("/get/specific/{playlistId}")
  public Response getPlaylistData(@PathVariable final int playlistId, HttpSession session) {
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    List<PlaylistResponse> response = new ArrayList<PlaylistResponse>();
    Playlist playlist = playlistRepo.findOne(playlistId);
    if (playlist == null) {
      return ResponseUtilities.filledFailure("Playlist Does not exist");
    }
    response.add(new PlaylistResponse(playlist));
    return ResponseUtilities.filledSuccess(response);
  }

  @GetMapping("/get/all")
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

  @PostMapping("/create")
  public Response createPlaylist(@RequestBody PlaylistCreateRequest request, HttpSession session) {
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    Playlist playlistToAdd = new Playlist();
    playlistToAdd.setBanned(false);
    playlistToAdd.setPublic(true);
    playlistToAdd.setCollaborative(false);
    playlistToAdd.setDescription(request.getDescription());
    playlistToAdd.setTitle(request.getTitle());
    playlistToAdd.setGenre(request.getGenre());
    try {
      playlistRepo.save(playlistToAdd);
      return ResponseUtilities.emptySuccess();
    } catch (Exception e) {
      System.out.println(e);
      return ResponseUtilities.filledFailure("Playlist Could not be Created");
    }
  }

  @PostMapping("/add/song")
  public Response addSongToPlaylist(@RequestBody PlaylistChangeSongRequest request, HttpSession session) {
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    if (!playlistRepo.exists(request.getPlaylistID()) || !songRepo.exists(request.getSongID())) {
      return ResponseUtilities.filledFailure("Song/Playlist does not exist");
    }
    Playlist playlistToUpdate = playlistRepo.findOne(request.getPlaylistID());
    Song songToAdd = songRepo.findOne(request.getSongID());

    boolean success = playlistToUpdate.getSongs().add(songToAdd);
    if (!success) {
      return ResponseUtilities.filledFailure("Song/Playlist could not be added.");
    } else {
      playlistRepo.save(playlistToUpdate);
      return ResponseUtilities.emptySuccess();
    }
  }

  @PostMapping("/remSong")
  public Response remSongToPlaylist(@RequestBody PlaylistChangeSongRequest request, HttpSession session) {
    if (session.getAttribute("User") == null) {
      return ResponseUtilities.filledFailure("User is not logged in.");
    }
    if (!playlistRepo.exists(request.getPlaylistID()) || !songRepo.exists(request.getSongID())) {
      return ResponseUtilities.filledFailure("Song/Playlist does not exist");
    }
    Playlist playlistToUpdate = playlistRepo.findOne(request.getPlaylistID());
    Song songToRemove = songRepo.findOne(request.getSongID());

    boolean success = playlistToUpdate.getSongs().remove(songToRemove);
    if (!success) {
      return ResponseUtilities.filledFailure("Song/Playlist could not be removed.");
    } else {
      playlistRepo.save(playlistToUpdate);
      return ResponseUtilities.emptySuccess();
    }
  }

  @PostMapping("/delete/{playlistId}")
  public Response deletePlaylist(@PathVariable final int playlistId ,HttpSession session){
    User user = (User) session.getAttribute("User");
    if(user == null){
      return ResponseUtilities.filledFailure("User is not logged in");
    }
    Playlist playlistToDelete = playlistRepo.findOne(playlistId);
    if(playlistToDelete == null){
      return ResponseUtilities.filledFailure("Playlist with that ID does not exist");
    }
    // Check to see that user owns the playlist
    playlistRepo.delete(playlistToDelete);
    return ResponseUtilities.emptySuccess();
  }

}
