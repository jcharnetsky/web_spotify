package web_spotify.spotify;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * Represents an object which contains a collection of songs and other relevant
 * info.
 *
 * @author Cardinals
 */
@Entity
public abstract class SongCollection implements Viewable {
	/**
	 * The Genre enum
	 */
	public enum Genre {ROCK, METAL, RAP, EDM, POP, CLASSICAL, INDIE}
	
    /**
     * The unique id of the Song Collection
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private final int id;

    /**
     * The displayed title of the collection
     */
	@Basic
    private String title;

    /**
     * The owner of the Collection
     */
	@Column(name = "owner")
    private final User owner;

    /**
     * The cover art of the collection
     */
	@Column(name = "image")
    private Image image;

    /**
     * The genre of the collection
     */
	@Column(name = "genre")
    private Genre genre;

    /**
     * The collection of songs
     */
	@Column(name = "songs")
    private Collection<Song> songs;

    /**
     * A boolean which determines if the user is public. DEFAULT FALSE.
     */
	@Basic
    private boolean isPublic;

    /**
     * A boolean which determines if the user is banned. DEFAULT FALSE.
     */
	@Basic
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
    public SongCollection(int id, String title, User owner, Image image, Genre genre) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.image = image;
        this.genre = genre;
        this.isPublic = false;
        this.isBanned = false;
        this.songs = new ArrayList<Song>();
    }

    /**
     * Adds a song to the end of the collection of songs
     *
     * @param song The song to be added
     */
    public void addSongToEnd(Song song) {
        songs.add(song);
    }

    /**
     * Adds a song to a specific index of the collection of songs
     *
     * @param song The song to be added
     * @param index The index at which the song will be added
     */
    public void addSongAtIndex(Song song, int index) {
        ((ArrayList<Song>) this.songs).add(index, song);
    }
    
    /**
     * Remove a song at an index
     *
     * @param int The index at which the song will be removed
     */
    public void removeSongAtIndex(int index) {
    		((ArrayList<Song>) this.songs).remove(index);
    }
    
    /**
     * Compare SongCollection objects to determine equivalence
     *
     * @param a object to compare
     * @return True if object is an instance of Administrator and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object sc) {
        if((sc != null) && (sc instanceof SongCollection)) {
            return ((SongCollection) sc).getId() == this.getId();
        } else return false;
    }
    
    /* OVERRIDES FOR VIEWABLE BELOW */
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
        return owner.getId();
    }

    @Override
    public void setBanned(boolean value) {
        isBanned = value;
    }

    @Override
    public void setPublic(boolean value) {
        isPublic = value;
    }

    /* GETTERS BELOW */
    @Override
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getOwner() {
        return owner;
    }

    public Image getImage() {
        return image;
    }

    public Genre getGenre() {
        return genre;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    /* SETTERS BELOW */
    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    public void setGenre(Genre genre) {
    		this.genre = genre;
    }
}
