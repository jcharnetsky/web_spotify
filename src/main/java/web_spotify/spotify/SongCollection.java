package web_spotify.spotify;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents an object which contains a collection of songs and other relevant
 * info.
 *
 * @author Cardinals
 */
public abstract class SongCollection implements Viewable {

    /**
     * The unique id of the Song Collection
     */
    private final int id;

    /**
     * The displayed title of the collection
     */
    private String title;

    /**
     * The owner of the Collection
     */
    private User owner;

    /**
     * The cover art of the collection
     */
    private String imageURL;

    /**
     * The genre of the collection
     */
    private String genre;

    /**
     * The collection of songs
     */
    private Collection<Song> songs;

    /**
     * A boolean which determines if the user is public. DEFAULT FALSE.
     */
    private boolean isPublic;

    /**
     * A boolean which determines if the user is banned. DEFAULT FALSE.
     */
    private boolean isBanned;

    /**
     * The constructor for Song Collection
     *
     * @param id The unique id of the collection
     * @param title The title of the collection
     * @param owner The owner of the collection
     * @param image The image of the collection
     * @param genre The genre of the collection
     */
    protected SongCollection(int id, String title, User owner, String imageURL, String genre, boolean isPublic, boolean isBanned) {
        // Set all the basic values
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.imageURL = imageURL;
        this.genre = genre;
        this.isPublic = isPublic;
        this.isBanned = isBanned;

        // Instantiate the *ORDERED* collection of songs
        this.songs = new ArrayList<Song>();
    }

    /**
     * Adds a song to the end of the collection of songs
     *
     * @param song The song to be added
     */
    public void addSongToEnd(Song song) {
        // A simple .add call 
        this.songs.add(song);
    }

    /**
     * Adds a song to a specific index of the collection of songs
     *
     * @param song The song to be added
     * @param index The index at which the song will be added
     */
    public void addSongAtIndex(Song song, int index) {
        // A simple .add call with the index parameter included
        ((ArrayList) this.songs).add(index, song);
    }
    
    /**
     * Remove a song at an index
     *
     * @param int The index at which the song will be removed
     */
    public void removeSongAtIndex(int index) {
    		((ArrayList) this.songs).remove(index);
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

    /* GETTERS BELOW */
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getOwner() {
        return owner;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getGenre() {
        return genre;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    /* SETTERS BELOW */
    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
