package webspotify.models.media;

import java.io.Serializable;
import java.util.*;
import webspotify.types.RepeatType;

public class SongQueue implements Serializable {

  private Song nowPlaying;
  private Deque<Song> currentQueue;
  private Deque<Song> history;
  private Stack<Song> recentlyPlayed;
  private RepeatType repeatType;
  private boolean shuffleEnabled;
  private Deque<Song> orderedQueueHolder;

  public SongQueue() {
    nowPlaying = null;
    currentQueue = new LinkedList<Song>();
    history = new LinkedList<Song>();
    recentlyPlayed = new Stack<Song>();
    repeatType = RepeatType.NONE;
    shuffleEnabled = false;
    orderedQueueHolder = new LinkedList<Song>();
  }
  
  public List<Song> getQueue() {
    List<Song> songs = new ArrayList<Song>();
    for (Song song : currentQueue) {
      songs.add(song);
    }
    return songs;
  }

  public void enqueueSong(Song s) {
    currentQueue.add(s);
  }
  
  public boolean removeSong(Song s) {
    return currentQueue.remove(s);
  }

  public void enqueueCollection(Collection<Song> collection) {
    clearQueue();
    currentQueue.addAll(collection);
  }
  
  public void clearQueue() {
    currentQueue.clear();
  }

  public List<Song> getHistory() {
    List<Song> songs = new ArrayList<Song>();
    for (Song song : history) {
      songs.add(song);
    }
    return songs;
  }
  
  public List<Song> getRecentlyPlayed() {
    List<Song> songs = new ArrayList<Song>();
    for(Song song : recentlyPlayed) {
      songs.add(song);
    }
    return songs;
  }

  public RepeatType getRepeatType() {
    return repeatType;
  }
  
  public void setRepeatType(RepeatType repeat) {
    repeatType = repeat;
  }
  
  public void toggleShuffle() {
    shuffleEnabled = !shuffleEnabled;
    if(shuffleEnabled) {
      shuffle();
    }
    else {
      if(!orderedQueueHolder.isEmpty()) {
        while(orderedQueueHolder.peek() != nowPlaying) {
          history.push(orderedQueueHolder.pop());
        }
        orderedQueueHolder.pop();
        currentQueue = orderedQueueHolder;
      }
    }
  }
  
  public void shuffle() {
    orderedQueueHolder.clear();
    orderedQueueHolder.addAll(currentQueue);
    ArrayList<Song> shuffleHelper = new ArrayList<Song>();
    shuffleHelper.addAll(currentQueue);
    currentQueue.clear();
    Random random = new Random();
    while(!shuffleHelper.isEmpty()) {
      currentQueue.push(shuffleHelper.remove(random.nextInt(shuffleHelper.size())));
    }
  }

  public Song next() {
    Song toReturn = null;
    if(nowPlaying != null) {
      if(repeatType != RepeatType.CURRENT) {
        history.push(nowPlaying);  
      }
      recentlyPlayed.push(nowPlaying);
    }
    if(nowPlaying == null && shuffleEnabled) {
      shuffle();
    }
    if(nowPlaying != null && repeatType == RepeatType.LIBRARY) {
      currentQueue.add(nowPlaying);
    }
    if (!currentQueue.isEmpty()) {
      switch (repeatType) {
        case NONE:
          toReturn = currentQueue.poll();
          break;
        case CURRENT:
          if(nowPlaying == null) {
            toReturn = currentQueue.poll();
          }
          else {
            toReturn = nowPlaying;  
          }
          break;
        case LIBRARY:
          toReturn = currentQueue.poll();
          break;
      }
      nowPlaying = toReturn;
    }
    return toReturn;
  }

  public Song prev() {
    Song toReturn = null;
    if(history.isEmpty() && nowPlaying == null) {
      return toReturn;
    }
    else if (history.isEmpty()) {
      recentlyPlayed.push(nowPlaying);
      return nowPlaying;
    }
    else {
      switch (repeatType) {
      case NONE:
        currentQueue.push(nowPlaying);
        toReturn = history.pop();
        recentlyPlayed.push(toReturn);
        break;
      case CURRENT:
        toReturn = nowPlaying;
        recentlyPlayed.push(toReturn);
        break;
      case LIBRARY:
        currentQueue.push(nowPlaying);
        toReturn = history.pop();
        history.addFirst(currentQueue.pollLast());
        break;
      }
      nowPlaying = toReturn;
    }
    return toReturn;
  }
}

/*
public void playNow(Song s) {
    LinkedList<Song> currentLinkedList = (LinkedList) currentQueue;
    currentLinkedList.add(0, s);
  }

  public void pushSongHistory(Song s) {
    history.push(s);
    recentlyPlayed.push(s);
  }
*/
