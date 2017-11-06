package web_spotify.spotify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Represents a concert that artists can play at
 *
 * @author Cardinals
 */
class Concert implements Viewable {

	 /**
     * The id of this concert
     */
    private final int concertId;
    
    /**
     * The name of the venue
     */
    private String venueName;
    
    /**
     * The name of the concert
     */
    private String concertName;
    
    /**
     * The grouping of artists that are to play at the concert
     */
    private Collection<Artist> artists;

    /**
     * The date at which the concert will take place
     */
    private Date date;

    /**
     * The URL of where to purchase the tickets
     */
    private String URL;

    /**
     * A boolean which determines if the user is public. DEFAULT FALSE.
     */
    private boolean isPublic;

    /**
     * A boolean which determines if the user is banned. DEFAULT FALSE.
     */
    private boolean isBanned;

    /**
     * The constructor of the Concert Class
     *
     * @param concertId The ID of the concert
     * @param venueName The name of the venue
     * @param concertName The name of the concert
     * @param artists The collection of artists at the concert
     * @param date The date of the concert
     * @param url The url at which to purchase tickets
     */
    public Concert(int concertId, String venueName, String concertName, Collection<Artist> artists, Date date, String URL) {
        this.concertId = concertId;
        this.venueName = venueName;
        this.concertName = concertName;
        this.artists = artists;
        this.date = date;
        this.URL = URL;
        isPublic = false;
        isBanned = false;
    }
    
    /**
     * Adds an artist to the current venue
     *
     * @param artist The artist to be added to the venue
     */
    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    /**
     * Removes an artist to the current venue
     *
     * @param artist The artist to be removed to the venue
     */
    public void removeArtist(int artistId) {
    		for(int i = 0; i < artists.size(); i++) {
			if(((ArrayList<Artist>)artists).get(i).getId() == artistId) {
				((ArrayList<Artist>)artists).remove(i);
				return;
			}
		}
    }
    
    /**
     * Compare Concert objects to determine equivalence
     *
     * @param c object to compare
     * @return True if object is an instance of Concert and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object c) {
        if((c != null) && (c instanceof Concert)) {
            return ((Concert) c).getId() == this.getId();
        } else return false;
    }
    
    /** Getters **/
    @Override
    public int getId() {
    		return concertId;
    }
    
    public String getVenueName() {
    		return venueName;
    }
    
    public String getConcertName() {
    		return concertName;
    }
    
    public Collection<Artist> getArtists() {
    		return artists;
    }
    
    public Date getDate() {
    		return date;
    }
    
    public String getURL() {
    		return URL;
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
    public int ownedBy() {
    		return -1;
    }
    
    /** Setters **/
    public void setVenueName(String venueName) {
    		this.venueName = venueName;
    }
    
    public void setConcertName(String concertName) {
    		this.concertName	= concertName;
    }
    
    public void setDate(Date date) {
    		this.date = date;
    }
    
    public void setURL(String URL) {
    		this.URL = URL;
    }
    
    @Override 
    public void setBanned(boolean value) {
    		isBanned = value;
    }
    
    @Override
    public void setPublic(boolean value) {
    		isPublic = value;
    }
}
