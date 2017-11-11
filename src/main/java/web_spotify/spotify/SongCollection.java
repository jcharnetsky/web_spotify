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
import javax.persistence.ManyToMany;

@Entity
public abstract class SongCollection implements Viewable {
	public enum Genre {ROCK, METAL, RAP, EDM, POP, CLASSICAL, INDIE}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final int id;
	@Basic
	private String title;
	@Column(name = "owner")
	private final User owner;
	@Column(name = "image")
	private Image image;
	@Column(name = "genre")
	private Genre genre;
	@ManyToMany
	@Column(name = "songs")
	private Collection<Song> songs;
	@Basic
	private boolean isPublic;
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
	 * @param index The index at which the song will be removed
	 */
	public void removeSongAtIndex(int index) {
		((ArrayList<Song>) this.songs).remove(index);
	}
	/**
	 * Compare SongCollection objects to determine equivalence
	 *
	 * @param sc The object to compare
	 * @return True if object is an instance of Administrator and has the same id; False otherwise
	 */
	@Override
	public boolean equals(Object sc) {
		if((sc != null) && (sc instanceof SongCollection)) {
			return ((SongCollection) sc).getId() == this.getId();
		} else return false;
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