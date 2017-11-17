package webspotify.responses;

import webspotify.models.media.Song;
import webspotify.models.media.SongQueue;

import java.util.List;

/**
 *
 * @author Cardinals
 */
public class QueueResponse {

  private List<Song> queue;
  private List<Song> recent;

  public QueueResponse(SongQueue queue){
  }
}
