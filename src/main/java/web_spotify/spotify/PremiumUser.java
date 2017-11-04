package web_spotify.spotify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Represents a user who has a paid subscription to the service
 * @author Cardinals
 */
public class PremiumUser extends User {
    
    private Collection<Playlist> downloadedPlaylists;
    private Collection<Album>    downloadedAlbums;
    private Collection<Song>     downloadedSongs;
    
    /**
     * The default constructor of a premium User
     * @param id The unique identifier for a particular user
     * @param name THe name of the User
     * @param email The current email of the user
     * @param address The physical address of the user
     * @param birthday The birthday of the user
     */
    public PremiumUser(int id, String name, String email, String address, Date birthday) {
        /* Call the super method */
        super(id, name, email, address, birthday);
        
        /* Instantiate all collections */
        this.downloadedPlaylists = new ArrayList<Playlist>();
        this.downloadedAlbums    = new ArrayList<Album>();
        this.downloadedSongs     = new ArrayList<Song>();
        
    }
    
    
    
}
