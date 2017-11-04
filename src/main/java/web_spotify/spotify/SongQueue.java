package web_spotify.spotify;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Cardinals
 */
public class SongQueue {

    /**
     * The owner of the song queue
     */
    private User owner;

    /**
     * The queue of the songs playing
     */
    private Queue<Song> queue;

    /**
     * The stack of previously played songs
     */
    private Stack<Song> recentlyPlayed;

    /**
     * The constructor for the SongQueue
     *
     * @param owner The owner of the song queue
     */
    public SongQueue(User owner) {
        this.owner = owner;

        /* Instantiate the queue */
        this.queue = new LinkedList<Song>();
        /* Instantiate the recentlyPlayed Stack */
        this.recentlyPlayed = new Stack<Song>();
    }

    /**
     * Enqueue an entire collection into the queue. This clears the origin queue
     *
     * @param collection The collection to be added
     */
    public void enqueueCollection(SongCollection collection) {
        /* Clear the queue first */
        this.clearQueue();
        /* Add all the collection elements to the queue */
        this.queue.addAll(collection.getSongs());
    }

    /**
     * Enqueue a single song to the song queue
     *
     * @param song The song to be added
     */
    public void enqueue(Song song) {
        /* Add the single song to the queue */
        this.queue.add(song);
    }

    /**
     * Dequeue a song from the current queue. Push the song to the recently
     * played as well
     *
     * @return The song that was dequeued
     */
    public Song dequeue() {
        /* Removes the song from the queue */
        Song song = this.queue.poll();
        /* If the song isn't null the song to the recently played as well */
        if (song != null) {
            this.recentlyPlayed.push(song);
        }
        /* Return the current song */
        return song;
    }

    /**
     * Clear the queue of songs
     */
    public void clearQueue() {
        /* Clear the entire queue */
        this.queue.clear();
    }

    /**
     * Clear the stack of recentlyPlayed
     */
    public void clearRecentlyPlayed() {
        /* Clear the recentlyPlayed stack */
        this.recentlyPlayed.clear();
    }

    /**
     * Checks to see if the queue is empty
     *
     * @return True if the queue is empty and false if it is not
     */
    public boolean isEmpty() {
        /* Check to see if the queue is empty */
        return this.queue.isEmpty();
    }

}
