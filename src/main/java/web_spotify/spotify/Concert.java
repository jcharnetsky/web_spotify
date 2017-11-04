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
     * The grouping of artists that are to play at the concert
     */
    private Collection<BasicArtist> artists;

    /**
     * The name of the venue
     */
    private String name;

    /**
     * The date at which the concert will take place
     */
    private Date date;

    /**
     * The URL of how to purchase the tickets
     */
    private String url;

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
     * @param name The name of the venue
     * @param date The date of the venue
     * @param url  The url of where to buy the tickets
     */
    public Concert (String name, Date date, String url) {
        this.name = name;
        this.date = date;
        this.url  = url;
        
        this.artists = new ArrayList<BasicArtist>();
    }
    
    /**
     * Adds an artist to the current venue
     * @param artist The artist to be added to the venue
     * @return True or false if the artist was successfully added.
     */
    public boolean addArtist (BasicArtist artist) {
        return this.artists.add(artist);
    }
    
    /**
     * Removes an artist to the current venue
     * @param artist The artist to be removed to the venue
     * @return True or false if the artist was successfully removed.
     */
    public boolean remArtist (BasicArtist artist) {
        return this.artists.remove(artist);
    }
    
    /* OVERRIDES FOR VIEWABLE BELOW */

    @Override
    public boolean isBanned() {
        return this.isBanned;
    }

    @Override
    public boolean isPublic() {
        return this.isPublic;
    }

    @Override
    public int ownedBy() {
        return -1;
    }

    @Override
    public boolean setBanned(boolean value) {
        this.isBanned = value;
        return true;
    }

    @Override
    public boolean setPublic(boolean value) {
        this.isPublic = value;
        return true;
    }
}
