package web_spotify.spotify;

/**
 * Represents the collection of objects that are playable by the client
 * @author Cardinals
 */
public interface Playable {
    
    /**
     * Determines if the playable has an audio file associated with it
     * @return True if the the playable can be played and false otherwise.
     */
    public abstract boolean isAvailable();
    
    /**
     * Responsible for setting the playable to play its audio
     */
    public abstract void play();
    
    /**
     * Responsible for setting the playable to pause its audio
     */
    public abstract void pause();
    
    /**
     * Gets the length of the playable
     * @return A double representing the duration fo the track in seconds.
     */
    public abstract double getTrackLength();
    
    /**
     * Increments the monthly listen count by 1
     */
    public abstract void incrementMonthlyListens();
    
    /**
     * Resets the currently held monthly listen
     */
    public abstract void resetMonthlyListens();
    
     /**
     * Increments the total listen count by 1
     */
    public abstract void incrementMTotalListens();
}
