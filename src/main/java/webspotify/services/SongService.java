package webspotify.services;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.models.media.Song;
import webspotify.repo.SongRepository;
import webspotify.responses.SongResponse;

@Service("songService")
public class SongService {
  @Autowired 
  SongRepository songRepository;
  
  @Transactional
  public Response getSong(final int songId) {
    Song song = songRepository.findOne(songId);
    if(song == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.SONG_NO_EXIST);
    }
    else {
      List<SongResponse> contentBody = new ArrayList<SongResponse>();
      contentBody.add(new SongResponse(song));
      return ResponseUtilities.filledSuccess(contentBody);
    }
  }
  
  @Transactional
  public HttpEntity<byte[]> getSongAudio(final int songId) {
    Song song = songRepository.findOne(songId);
    if(song == null) {
      return null;
    }
    byte[] songAudio = song.getAudio();
    if(songAudio == null || songAudio.length == 0) {
      return null;
    }
    MediaType type = MediaType.parseMediaType(ConfigConstants.AUDIO_TYPE);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(type);
    headers.setContentLength(songAudio.length);
    return new HttpEntity<byte[]>(songAudio, headers);
  }
}

