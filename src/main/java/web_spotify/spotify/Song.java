package web_spotify.spotify;

/**
 * Represents a song that a user can play
 *
 * @author Cardinals
 */
public class Song implements Viewable, Playable {

    /**
     * Whether or not the song is banned
     */
    private boolean isBanned;

    /**
     * Whether or not the song is public
     */
    private boolean isPublic;

    /**
     * The ID of the user who owns the song
     */
    private final int ownerID;

    /** 
     * The ID of the song
     */
    private final int id;
    
    /**
     * The title of the song
     */
    private String title;
    /**
     * The length of the song
     */
    private double trackLength;

    /**
     * The number of listens this month
     */
    private int monthlyListens;

    /**
     * The number of total listens
     */
    private int totalListens;

    /**
     * Whether or not the song is available to play
     */
    private boolean isAvailable;
    
    /**
     * Default constructor for a song
     * @param title The title of the song
     * @param songID The id of the song
     * @param trackLength The length of the song
     * @param totalListens The number of total listens of the song
     * @param monthlyListens The number of monthly listens of the song
     * @param isBanned The banned status of the song
     */
    public Song (String title, int id, int ownerID, double trackLength, int totalListens, int monthlyListens, boolean isBanned, boolean isPublic) {
    		this.title = title;
    		this.ownerID = ownerID;
    		this.id = id;
    		this.trackLength = trackLength;
    		this.totalListens = totalListens;
    		this.monthlyListens = monthlyListens;
    		this.isBanned = isBanned;
    		this.isPublic = isPublic;
    }
    
    /**
     * Returns whether or not the song is banned
     */
    @Override
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * Returns whether or not the song is public
     */
    @Override
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Returns the user ID of the song owner
     */
    @Override
    public int ownedBy() {
        return ownerID;
    }

    /**
     * Sets the banned status of the song
     */
    @Override
    public boolean setBanned(boolean value) {
        this.isBanned = value;
        return true;
    }

    /**
     * Sets the public status of the song
     */
    @Override
    public boolean setPublic(boolean value) {
        this.isPublic = value;
        return true;
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the length of the song
     */
    @Override
    public double getTrackLength() {
        return this.trackLength;
    }

    /**
     * Increments the monthly listens by 1
     */
    @Override
    public void incrementMonthlyListens() {
        this.monthlyListens++;
    }

    /**
     * Sets monthly listens to 0
     */
    @Override
    public void resetMonthlyListens() {
        this.monthlyListens = 0;
    }

    /**
     * Increments the total listens by 1
     */
    @Override
    public void incrementTotalListens() {
        this.totalListens++;
    }

    /**
     * Returns whether or not the song is available
     */
    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }
    
    /**
     * Returns this objects id
     * @return 
     */
    @Override
    public int getId() {
        return id;
    }
    
    /**
     * Returns the monthly listens for the song.
     */
    public int getMonthlyListens() {
    		return monthlyListens;
    }
    
    /**
     * Returns the total listens for the song.
     */
    public int getTotalListens() {
    		return totalListens;
    }
    
    /**
     * Returns the title of the song.
     */
    public String getTitle() {
    		return title;
    }
    
    /**
     * Set the title of the song
     */
    public void setTitle(String title) {
    		this.title = title;
    }
    
    /**
     * Returns the ownerID of the song
     */
    	public int getOwnerID() {
    		return ownerID;
    	}

     /**
     * Compare Song objects to determine equivalence
     *
     * @param s object to compare
     * @return True if object is an instance of Song and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object s) {
        if((s != null) && (s instanceof Song)) {
            return ((Song) s).getId() == this.getId();
        } else return false;
    }
}
