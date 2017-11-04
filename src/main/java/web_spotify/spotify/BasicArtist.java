package web_spotify.spotify;

import java.util.Collection;
import java.util.Date;
import java.util.ArrayList;

/**
 * Represents the base functionality of a User who is an Artist
 *
 * @author Cardinals
 */
public class BasicArtist extends User {

    /**
     * A collection of strings that represent previous artist aliases
     */
    private Collection<String> aliases;

    /**
     * A collection of songs that the Basic Artist owns
     */
    private Collection<Song> ownedSongs;

    /**
     * A collection of albums that the Basic Artist owns
     */
    private Collection<Album> ownedAlbums;

    /**
     * A collection of concerts that the Basic Artist has/is attended/attending
     */
    private Collection<Concert> concerts;

    /**
     * A piece of text which is the bio for the artist
     */
    private String bio;

    /**
     * The constructor the BasicArtist
     *
     * @param id The id of the account
     * @param name The name of the account user
     * @param email The email of the account user
     * @param address The physical address of the account user
     * @param birthday The birthday of the account user
     * @param bio The bio of the BasicArtist
     * @param aliases A collection of aliases the artist goes by
     */
    public BasicArtist(int id, String name, String email, String address, Date birthday, String bio, Collection<String> aliases) {
        // Call the constructor
        super(id, name, email, address, birthday);
        // Set the bio
        this.bio = bio;
        // Instantiate all the collections
        this.ownedSongs = new ArrayList<Song>();
        this.ownedAlbums = new ArrayList<Album>();
        this.concerts = new ArrayList<Concert>();
        this.aliases = new ArrayList<String>();
        // Add all aliases into the alias set
        this.aliases.addAll(aliases);
    }

    /**
     * Compare BasicArtist objects to determine equivalence
     *
     * @param object to compare
     * @return True if object is an instance of BasicArtist and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object a) {
        if((a != null) && (a instanceof BasicArtist)) {
            return ((BasicArtist) a).getId() == this.getId();
        } else return false;
    }
}
