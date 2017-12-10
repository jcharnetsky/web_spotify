package webspotify.models.media;

import java.io.Serializable;
import java.util.*;
import webspotify.types.RepeatType;

public class SongQueue implements Serializable {

  private Queue<Song> currentQueue;
  private Stack<Song> history;
  private Stack<Song> recentlyPlayed;
  private RepeatType repeatType;

  public SongQueue() {
    currentQueue = new LinkedList<Song>();
    history = new Stack<Song>();
    recentlyPlayed = new Stack<Song>();
    repeatType = RepeatType.NONE;
  }

  public void enqueueSong(Song s) {
    currentQueue.add(s);
  }
  
  public void pushSongHistory(Song s) {
    history.push(s);
    recentlyPlayed.push(s);
  }

  public boolean removeSong(Song s) {
    return currentQueue.remove(s);
  }

  public void playNow(Song s) {
    LinkedList<Song> currentLinkedList = (LinkedList) currentQueue;
    currentLinkedList.add(0, s);
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
    for (Song song : history) {
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
    if (!currentQueue.isEmpty()) {
      switch (repeatType) {
        case NONE:
          toReturn = currentQueue.poll();
          break;
        case CURRENT:
          toReturn = currentQueue.peek();
          break;
        case LIBRARY:
          toReturn = currentQueue.poll();
          currentQueue.add(toReturn);
          break;
      }
      if (toReturn != null) {
        history.push(toReturn);
        recentlyPlayed.push(toReturn);
      }
    }
    return toReturn;
  }

  public Song prev() {
    Song previousSong = recentlyPlayed.pop();
    playNow(previousSong);
    return next();
  }

}
