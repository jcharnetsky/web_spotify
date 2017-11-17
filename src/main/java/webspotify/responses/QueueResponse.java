package webspotify.responses;

import webspotify.models.media.Song;
import webspotify.models.media.SongQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cardinals
 */
public class QueueResponse {

  private List<SongResponse> queue;
  private List<SongResponse> recent;

  public QueueResponse(SongQueue queue) {
    this.queue = new ArrayList<SongResponse>();
    this.recent = new ArrayList<SongResponse>();
    for(Song song: queue.getUpcomingList()){
      this.queue.add(new SongResponse(song));
    }
    for(Song song: queue.getRecentList()){
      this.recent.add(new SongResponse(song));
    }
  }

  public List<SongResponse> getQueue() {
    return queue;
  }

  public void setQueue(List<SongResponse> queue) {
    this.queue = queue;
  }

  public List<SongResponse> getRecent() {
    return recent;
  }

  public void setRecent(List<SongResponse> recent) {
    this.recent = recent;
  }
}
