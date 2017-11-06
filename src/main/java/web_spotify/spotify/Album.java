package web_spotify.spotify;

import java.awt.Image;
import java.sql.Date;
import java.util.Collection;
import java.util.ArrayList;

/**
 * A collection of songs that are an album
 *
 * @author Cardinals
 */
class Album extends SongCollection {

	/**
	 * The album's release date
	 */
	private final Date releaseDate;
	
	/**
	 * The main artist of the album
	 */
	private final String artist;
	
	/**
	 * The featured artists on the album
	 */
	private final Collection<Artist> featuredArtists;
	
    /**
     * The default constructor of the Album Class
     *
     * @param id The unique id of the collection
     * @param title The title of the collection
     * @param owner The owner of the collection
     * @param image The image of the collection
     * @param genre The genre of the collection
     */
    public Album(int id, String title, User owner, Image image, Genre genre, Date releaseDate, String artist, Collection<Artist> featuredArtists) {
        super(id, title, owner, image, genre);
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.featuredArtists = new ArrayList<Artist>();
        this.featuredArtists.addAll(featuredArtists);
    }

    /**
     * Compare Album objects to determine equivalence
     *
     * @param a object to compare
     * @return True if object is an instance of Album and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object a) {
        if((a != null) && (a instanceof Album)) {
            return ((Album) a).getId() == this.getId();
        } else return false;
    }
    
    /** Getters **/
    public Date getReleaseDate() {
    		return releaseDate;
    }
    
    public String getArtist() {
    		return artist;
    }
    
    public Collection<Artist> getFeaturedArtists() {
    		return featuredArtists;
    }
}
