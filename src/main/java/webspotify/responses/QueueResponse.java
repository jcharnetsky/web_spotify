package webspotify.responses;

import webspotify.models.media.Song;
import webspotify.models.media.SongQueue;

import java.util.List;

/**
 * @author Cardinals
 */
public class QueueResponse {

  private List<Song> queue;
  private List<Song> recent;

  public QueueResponse(SongQueue queue) {
    this.queue = queue.getUpcomingList();
    this.recent = queue.getRecentList();
  }

  public List<Song> getQueue() {
    return queue;
  }

  public void setQueue(List<Song> queue) {
    this.queue = queue;
  }

  public List<Song> getRecent() {
    return recent;
  }

  public void setRecent(List<Song> recent) {
    this.recent = recent;
  }
}
