package webspotify.models.media;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SongQueue implements Serializable {

  private Queue<Song> songQueue;
  private Queue<Song> recentlyPlayed;

  public SongQueue() {
    songQueue = new LinkedList<Song>();
    recentlyPlayed = new LinkedList<Song>();
  }

  public void push(Song song) {
    songQueue.add(song);
  }

  public void pop() {
    if (!isEmpty()) {
      recentlyPlayed.add(songQueue.remove());
    }
  }

  public void clear() {
    songQueue.clear();
  }

  public boolean isEmpty() {
    return songQueue.isEmpty();
  }

  public void pushCollection(SongCollection collection) {
    for (Song song : collection.getSongs()) {
      push(song);
    }
  }

  public List<Song> getUpcomingList() {
    return (List) songQueue;
  }

  public List<Song> getRecentList() {
    return (List) songQueue;
  }

}
