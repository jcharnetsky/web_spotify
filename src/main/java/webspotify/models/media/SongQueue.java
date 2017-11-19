package webspotify.models.media;

import webspotify.types.RepeatType;

import java.io.Serializable;
import java.util.*;

public class SongQueue implements Serializable {

  private Queue<Song> currentQueue;
  private Stack<Song> recentlyPlayed;
  private Stack<Song> history;
  private RepeatType repeatType;

  public SongQueue() {
    currentQueue = new LinkedList<Song>();
    recentlyPlayed = new Stack<Song>();
    history = new Stack<Song>();
    repeatType = RepeatType.NONE;
  }

  public void enqueueSong(Song s) {
    currentQueue.add(s);
  }

  public void enqueueCollection(Collection<Song> collection) {
    clearQueue();
    currentQueue.addAll(collection);
  }

  public void clearQueue() {
    currentQueue.clear();
  }

  public void setRepeatType(RepeatType repeat) {
    repeatType = repeat;
  }

  public List<Song> getHistory() {
    List<Song> songs = new ArrayList<Song>();
    for (Song song : recentlyPlayed) {
      songs.add(song);
    }
    return songs;
  }

  public List<Song> getQueue() {
    List<Song> songs = new ArrayList<Song>();
    for (Song song : currentQueue) {
      songs.add(song);
    }
    return songs;
  }

  public RepeatType getRepeatType() {
    return repeatType;
  }

  public Song next() {
    Song toReturn = null;
//    if (!currentQueue.isEmpty()) {
//      switch (repeatType) {
//        case NONE:
//          toReturn = currentQueue.poll();
//          recentlyPlayed.push(toReturn);
//          break;
//        case CURRENT:
//          toReturn = currentQueue.peek();
//          break;
//        case LIBRARY:
//          toReturn = currentQueue.poll();
//          currentQueue.add(toReturn);
//          break;
//      }
//      if (toReturn != null) {
//        history.push(toReturn);
//      }
//    }
    return toReturn;
  }

  public Song prev() {
    Song toReturn = null;
//    if (!currentQueue.isEmpty()) {
//      switch (repeatType) {
//        case NONE:
//         
//          break;
//        case CURRENT:
//          
//          break;
//        case LIBRARY:
//          
//          break;
//      }
//      if (toReturn != null) {
//        int sizeOfQueue = currentQueue.size();
//        int trueLocation = (sizeOfQueue - 1 - pointer) % sizeOfQueue;
//        recentlyPlayed.push(currentQueue.get(trueLocation));
//      }
//    }
    return toReturn;
  }

}
