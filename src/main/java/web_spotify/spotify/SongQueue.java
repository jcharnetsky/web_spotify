package web_spotify.spotify;
import java.util.LinkedList;
import java.util.Queue;

public class SongQueue {
	private Queue<Song> queue;
	private Queue<Song> recentlyPlayed;
	/**
	 * The constructor for the SongQueue
	 *
	 */
	public SongQueue() {
		this.queue = new LinkedList<Song>();
		this.recentlyPlayed = new LinkedList<Song>();
	}
	/**
	 * Enqueue an entire collection into the queue. This clears the origin queue
	 *
	 * @param collection The collection to be added
	 */
	public void enqueueCollection(SongCollection collection) {
		clearSongQueue();
		queue.addAll(collection.getSongs());
	}
	/**
	 * Enqueue a single song to the song queue
	 *
	 * @param song The song to be added
	 */
	public void enqueue(Song song) {
		queue.add(song);
	}
	/**
	 * Dequeue a song from the current queue. Push the song to the recently
	 * played as well
	 *
	 * @return The song that was dequeued
	 */
	public Song dequeue() {
		Song song = queue.poll();
		if (song != null) recentlyPlayed.add(song);
		return song;
	}
	/**
	 * Clear the queue of songs
	 */
	public void clearSongQueue() {
		queue.clear();
	}
	/**
	 * Clear the stack of recentlyPlayed
	 */
	public void clearRecentlyPlayed() {
		recentlyPlayed.clear();
	}
	/**
	 * Checks to see if the queue is empty
	 *
	 * @return True if the queue is empty and false if it is not
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
