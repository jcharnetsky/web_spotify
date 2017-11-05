package web_spotify.spotify;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a song that a user can play
 *
 * @author Cardinals
 */
public class Song implements Viewable, Playable {

	/** 
     * The ID of the song
     */
    private final int songId;
    
    /**
     * The ID of the artist who owns the song
     */
    private final int artistId;
    
    /** 
     * The album the song belongs to
     */
    private final int albumId;
    
    /**
     * The featured artists on the song
     */
    private final Collection<Artist> featuredArtists;
    
    /**
     * The title of the song
     */
    private final String title;
    
    /**
     * The length of the song
     */
    private final double trackLength;
    
    /**
     * The audio file
     */
    private final byte[] audioFile;

    /**
     * The number of listens this month
     */
    private int monthlyListens;

    /**
     * The number of total listens
     */
    private int totalListens;
    
    /**
     * Whether or not the song is banned
     */
    private boolean isBanned;

    /**
     * Whether or not the song is public
     */
    private boolean isPublic;
    
    /**
     * Default constructor for a song
     * 
     * @param songId The song id
     * @param artistId The id of the song's artist
     * @param albumId The id of the song's album
     * @param featuredArtists The collection of the song's featured artists
     * @param title The title of the song
     * @param trackLength The runtime of the song
     */ 
    public Song (int songId, int artistId, int albumId, ArrayList<Artist> featuredArtists, String title, double trackLength, byte[] audioFile) {
    		this.songId = songId;
    		this.artistId = artistId;
    		this.albumId = albumId;
    		this.featuredArtists = new ArrayList<Artist>();
    		this.featuredArtists.addAll(featuredArtists);
    		this.title = title;
    		this.trackLength = trackLength;
    		this.audioFile = audioFile;
    		monthlyListens = 0;
    		totalListens = 0;
    		isBanned = false;
    		isPublic = false;
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
    
    /** Getters **/
    @Override
    public int getId() {
    		return songId;
    }
    
    @Override
    public int ownedBy() {
        return artistId;
    }
    
    public int getAlbumid() {
    		return albumId;
    }
    
    public Collection<Artist> getFeaturedArtist() {
    		return featuredArtists;
    }
    
    public String getTitle() {
    		return title;
    }
    
    @Override
    public double getTrackLength() {
    		return trackLength;
    }
    
    public int getMonthlyListens() {
    		return monthlyListens;
    }
    
    public int getTotalListens() {
    		return totalListens;
    }
    
    @Override
    public boolean isBanned() {
        return isBanned;
    }
    
    @Override
    public boolean isPublic() {
        return isPublic;
    }
    
    @Override
    public boolean isAvailable() {
    		if(audioFile.length == 0) {
    			return false;
    		}
    		return true;
    }
    
    /** Setters **/
    @Override
    public void setBanned(boolean value) {
        isBanned = value;
    }

    @Override
    public void setPublic(boolean value) {
        isPublic = value;
    }
}
