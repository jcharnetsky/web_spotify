package webspotify.responses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webspotify.models.media.Song;
import webspotify.models.media.SongQueue;
import webspotify.models.users.User;
import webspotify.types.RepeatType;

/**
 * @author Cardinals
 */
public class QueueResponse {

  private RepeatType repeatType;
  private List<SongResponse> queue;
  private List<SongResponse> history;

  public QueueResponse(SongQueue queue) {
    this.queue = new ArrayList<SongResponse>();
    this.history = new ArrayList<SongResponse>();
    for (Song song : queue.getQueue()) {
      this.queue.add(new SongResponse(song));
    }
    for (Song song : queue.getHistory()) {
      this.history.add(new SongResponse(song));
    }
    this.repeatType = queue.getRepeatType();
  }

  public QueueResponse(User user, SongQueue queue) {
    this.queue = new ArrayList<SongResponse>();
    this.history = new ArrayList<SongResponse>();
    for (Song song : queue.getQueue()) {
      this.queue.add(new SongResponse(song));
    }
    for (Song song : queue.getHistory()) {
      this.history.add(new SongResponse(song));
    }
    this.repeatType = queue.getRepeatType();

    Set<Integer> ids = new HashSet<Integer>();
    for (Song song : user.getSavedSongs()){
      ids.add(song.getId());
    }
    for(SongResponse songResponse: getQueue()){
      songResponse.setSaved(ids.contains(songResponse.getId()));
    }
    for(SongResponse songResponse: getHistory()){
      songResponse.setSaved(ids.contains(songResponse.getId()));
    }
  }

  public RepeatType getRepeatType() {
    return repeatType;
  }

  public void setRepeatType(RepeatType repeatType) {
    this.repeatType = repeatType;
  }

  public List<SongResponse> getQueue() {
    return queue;
  }

  public void setQueue(List<SongResponse> queue) {
    this.queue = queue;
  }

  public List<SongResponse> getHistory() {
    return history;
  }

  public void setHistory(List<SongResponse> history) {
    this.history = history;
  }
}
