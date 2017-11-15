package defunct.spotify;
import java.util.Collection;
import java.util.Date;
import java.awt.Image;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//@Entity
//@Table(name = "Artists")
public class Artist extends User {
	@Basic
	private String bio;
	@OneToMany
	@Column(name = "aliases")
	private Collection<String> aliases;
	@OneToMany
	@Column(name = "ownedSongs")
	private Collection<Song> ownedSongs;
	@OneToMany
	@Column(name = "ownedAlbums")
	private Collection<Album> ownedAlbums;
	@ManyToMany
	@Column(name = "concerts")
	private Collection<Concert> concerts;

    public Artist() {
    }
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
	public Artist(int id, String name, String email, String address, Date birthday, Image image, String bio, Collection<String> aliases) {
		super(id, name, email, address, birthday, null);
		this.bio = bio;
		this.aliases = new ArrayList<String>();
		this.aliases.addAll(aliases);
		this.ownedSongs = new ArrayList<Song>();
		this.ownedAlbums = new ArrayList<Album>();
		this.concerts = new ArrayList<Concert>();
	}
	/**
	 * Add an alias to an artist's aliases
	 * 
	 * @param alias An alias
	 */
	public void addAlias(String alias) {
		aliases.add(alias);
	}
	/** 
	 * Remove an alias from an artist's aliases
	 * 
	 * @param alias An alias
	 */
	public void removeAlias(String alias) {
		aliases.remove(alias);
	}
	/**
	 * Add a song to an artist's owned songs
	 * 
	 * @param song A song the artist owns
	 */
	public void addOwnedSong(Song song) {
		ownedSongs.add(song);
	}
	/** 
	 * Remove a song from an artist's owned songs
	 * 
	 * @param songId A song the artist owns
	 */
	public void removeOwnedSong(int songId) {
		for(int i = 0; i < ownedSongs.size(); i++) {
			if(((ArrayList<Song>)ownedSongs).get(i).getId() == songId) {
				((ArrayList<Song>)ownedSongs).remove(i);
			}
		}
	}
	/**
	 * Add an album to an artist's owned albums
	 * 
	 * @param album An album the artist owns
	 */
	public void addOwnedAlbum(Album album) {
		ownedAlbums.add(album);
	}
	/** 
	 * Remove an album from an artist's owned songs
	 * 
	 * @param albumId An album the artist owns
	 */
	public void removeOwnedAlbum(int albumId) {
		for(int i = 0; i < ownedAlbums.size(); i++) {
			if(((ArrayList<Album>)ownedAlbums).get(i).getId() == albumId) {
				((ArrayList<Album>)ownedAlbums).remove(i);
			}
		}
	}
	/**
	 * Add an concert to an artist's concerts
	 * 
	 * @param concert A concert the artist is performing at or hosting
	 */
	public void addConcert(Concert concert) {
		concerts.add(concert);
	}
	/** 
	 * Remove a concert from an artist's concerts
	 * 
	 * @param concert A concert the artist is performing at or hosting
	 */
	public void removeConcert(int concertId) {
		for(int i = 0; i < concerts.size(); i++) {
			if(((ArrayList<Concert>)concerts).get(i).getId() == concertId) {
				((ArrayList<Concert>)concerts).remove(i);
			}
		}
	}
	/**
	* Compare BasicArtist objects to determine equivalence
	*
	* @param a object to compare
	* @return True if object is an instance of BasicArtist and has the same id; False otherwise
	*/
	@Override
	public boolean equals(Object a) {
		if((a != null) && (a instanceof Artist)) {
			return ((Artist) a).getId() == this.getId();
		}
		else {
			return false;
		}
	}
	public String getBio() {
		return bio;
	}
	public Collection<String> getAliases() {
		return aliases;
	}
	public Collection<Song> getOwnedSongs() {
		return ownedSongs;
	}
	public Collection<Album> getOwnedAlbums() {
		return ownedAlbums;
	}
	public Collection<Concert> getConcerts() {
		return concerts;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
}
