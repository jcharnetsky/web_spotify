package web_spotify.spotify;

public interface Playable {
	/**
	 * Determines if the playable has an audio file associated with it
	 *
	 * @return True if the the playable can be played and false otherwise.
	 */
	public abstract boolean isAvailable();
	/**
	 * Gets the length of the playable
	 *
	 * @return A double representing the duration of the track in seconds.
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
	public abstract void incrementTotalListens();
}
