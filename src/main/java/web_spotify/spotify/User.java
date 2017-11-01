package web_spotify.spotify;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import web_spotify.spotify.SongCollection.Genre;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents the base user functionality for the Spotify project
 *
 * @author Cardinals
 */
@Entity
@Table(name = "Users")
public class User implements Viewable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String address;
	private Date birthday;
	private Image image;
	private Collection<User> followers;
	private Collection<User> followedUsers;
	private Collection<Artist> followedArtists;
	private Collection<Album> savedAlbums;
	private Collection<Playlist> followedPlaylists;
	private Collection<Playlist> ownedPlaylists;
	private Playlist savedSongs;
	private SongQueue songQueue;
	private Collection<Playlist> downloadedPlaylists;
	private Collection<Album> downloadedAlbums;
	private Collection<Song> downloadedSongs;
	private boolean isPublic;
	private boolean isBanned;
	private boolean isPremium;
	/**
	 * The Constructor for the User Object
	 *
	 * @param id The unique identifier for a particular user
	 * @param name The name of the User
	 * @param email The current email of the user
	 * @param address The physical address of the user
	 * @param birthday The birthday of the user
	 */
	public User(int id, String name, String email, String address, Date birthday, Image image) {
		this.name = name;
		this.email = email;
		this.address = address;
		this.birthday = birthday;
		this.image = image;
		this.isPublic = false;
		this.isBanned = false;
		this.isPremium = false;
		followers = new ArrayList<User>();
		followedUsers = new ArrayList<User>();
		followedArtists = new ArrayList<Artist>();
		savedAlbums = new ArrayList<Album>();
		followedPlaylists = new ArrayList<Playlist>();
		ownedPlaylists = new ArrayList<Playlist>();
		savedSongs = new Playlist(0, "Songs", this, null, null, "");
		songQueue = new SongQueue();
		downloadedPlaylists = new ArrayList<Playlist>();
		downloadedAlbums = new ArrayList<Album>();
		downloadedSongs = new ArrayList<Song>();
	}
	protected User() {}
	/**
	 * Add a follower.
	 * 
	 * @param user The follower to be added
	 */
	public void addFollower(User user) {
		followers.add(user);
	}
	/**
	 * Remove a follower.
	 * 
	 * @param userId ID of the follower to be removed
	 */
	public void removeFollower(int userId) {
		for(int i = 0; i < followers.size(); i++) {
			if(((ArrayList<User>)followers).get(i).getId() == userId) {
				((ArrayList<User>)followers).remove(i);
				return;
			}
		}
	}
	/**
	 * Follow a user.
	 * 
	 * @param user The user to be followed
	 */
	public void followUser(User user) {
		followedUsers.add(user);
	}
	/**
	 * Unfollow a user.
	 * 
	 * @param user The user to be unfollowed
	 */
	public void unfollowUser(int userId) {
		for(int i = 0; i < followedUsers.size(); i++) {
			if(((ArrayList<User>)followedUsers).get(i).getId() == userId) {
				((ArrayList<User>) followedUsers).remove(i);
				return;
			}
		}
	}
	/**
	 * Follow an artist.
	 * 
	 * @param artist The artist to be followed
	 */
	public void followArtist(Artist artist) {
		followedArtists.add(artist);
	}
	/**
	 * Unfollow an artist.
	 * 
	 * @param artistId ID of the artist to be unfollowed
	 */
	public void unfollowArtist(int artistId) {
		for(int i = 0; i < followedArtists.size(); i++) {
			if(((ArrayList<Artist>) followedArtists).get(i).getId() == artistId) {
				((ArrayList<Artist>) followedArtists).remove(i);
				return;
			}
		}
	}
	/**
	 * Save an album.
	 * 
	 * @param album The album to be saved
	 */
	public void saveAlbum(Album album) {
		savedAlbums.add(album);
	}
	/**
	 * Unsave an album.
	 * 
	 * @param albumId ID of the album to be unsaved
	 */
	public void unsaveAlbum(int albumId) {
		for(int i = 0; i < savedAlbums.size(); i++) {
			if(((ArrayList<Album>) savedAlbums).get(i).getId() == albumId) {
				((ArrayList<Album>) savedAlbums).remove(i);
				return;
			}
		}
	}
	/**
	 * Follow a playlist.
	 * 
	 * @param playlist The playlist to be followed
	 */
	public void followPlaylist(Playlist playlist) {
		followedPlaylists.add(playlist);
	}
	/** 
	 * Unfollow a playlist.
	 * 
	 * @param playlistId ID of the playlist to be unfollowed
	 */
	public void unfollowPlaylist(int playlistId) {
		for(int i = 0; i < followedPlaylists.size(); i++) {
			if(((ArrayList<Playlist>) followedPlaylists).get(i).getId() == playlistId) {
				((ArrayList<Playlist>) followedPlaylists).remove(i);
				return;
			}
		}
	}
	/**
	 * Create a custom playlist.
	 * 
	 * @param id The unique id of the collection
	 * @param title The title of the collection
	 * @param owner The owner of the collection
	 * @param image The image of the collection
	 * @param genre The genre of the collection
	 * @param description The description of the collection
	 */
	public void createPlaylist(int id, String title, User owner, Image image, Genre genre, String description) {
		Playlist playlist = new Playlist(id, title, owner, image, genre, description);
		ownedPlaylists.add(playlist);
	}
	/**
	 * Delete a custom playlist.
	 * 
	 * @param playlistId ID of the playlist to be deleted
	 */
	public void deletePlaylist(int playlistId) {
		for(int i = 0; i < ownedPlaylists.size(); i++) {
			if(((ArrayList<Playlist>) ownedPlaylists).get(i).getId() == playlistId) {
				((ArrayList<Playlist>) ownedPlaylists).remove(i);
				return;
			}
		}
	}
	/**
	 * Download a playlist
	 *
	 * @param playlist Playlist to be downloaded
	 */
	public void addDownloadedPlaylist(Playlist playlist) {
		downloadedPlaylists.add(playlist);
	}
	/**
	 * Undownload a playlist
	 *
	 * @param playlistId ID of the playlist to be no longer downloaded
	 */
	public void removeDownloadedPlaylist(int playlistId) {
		for(int i = 0; i < downloadedPlaylists.size(); i++) {
			if(((ArrayList<Playlist>) downloadedPlaylists).get(i).getId() == playlistId) {
				((ArrayList<Playlist>) downloadedPlaylists).remove(i);
				return;
			}
		}
	}
	/**
	 * Download an album
	 *
	 * @param album Album to be downloaded
	 */
	public void addDownloadedAlbum(Album album) {
		downloadedAlbums.add(album);
	}
	/**
	 * Undownload an album
	 *
	 * @param albumId ID of the album to be no longer downloaded
	 */
	public void removeDownloadedAlbum(int albumId) {
		for(int i = 0; i < downloadedAlbums.size(); i++) {
			if(((ArrayList<Album>) downloadedAlbums).get(i).getId() == albumId) {
				((ArrayList<Album>) downloadedAlbums).remove(i);
				return;
			}
		}
	}
	/**
	 * Download a song
	 *
	 * @param song Song to be downloaded
	 */
	public void addDownloadedSong(Song song) {
		downloadedSongs.add(song);
	}
	/**
	 * Undownload a song
	 *
	 * @param songId ID of the song to be no longer downloaded
	 */
	public void removeDownloadedSong(int songId) {
		for(int i = 0; i < downloadedSongs.size(); i++) {
			if(((ArrayList<Song>) downloadedSongs).get(i).getId() == songId) {
				((ArrayList<Song>) downloadedSongs).remove(i);
				return;
			}
		}
	}
	/**
	 * Compare User objects to determine equivalence.
	 *
	 * @param u Object to compare
	 * @return True if object is an instance of User and has the same id. False otherwise.
	 */
	@Override
	public boolean equals(Object u) {
		if((u != null) && (u instanceof User)) {
			return ((User) u).getId() == this.getId();
		} else return false;
	}
	@Override
	public String toString() {
		return String.format(
			"User[id = '%d', name = '%s', email = '%s', address = '%s', birthday = '%t']",
			id, name, email, address, birthday);
	}
	@Override
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public Image getImage() {
		return image;
	}
	public Collection<User> getFollowers() {
		return followers;
	}
	public Collection<User> getFollowedUsers() {
		return followedUsers;
	}
	public Collection<Album> getSavedAlbums() {
		return savedAlbums;
	}
	public Collection<Playlist> getFollowedPlaylists() {
		return followedPlaylists;
	}
	public Collection<Playlist> getOwnedPlaylists() {
		return ownedPlaylists;
	}
	public Playlist getSavedSongs() {
		return savedSongs;
	}
	public SongQueue getSongQueue() {
		return songQueue;
	}
	public Collection<Playlist> getDownloadedPlaylists() {
		return downloadedPlaylists;
	}
	public Collection<Album> getDownloadedAlbums() {
		return downloadedAlbums;
	}
	public Collection<Song> getDownloadedSongs() {
		return downloadedSongs;
	}
	@Override
	public boolean isBanned() {
		return isBanned;
	}
	@Override
	public boolean isPublic() {
		return isPublic;
	}
	public boolean isPremium() {
		return isPremium;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	@Override
	public void setBanned(boolean value) {
		this.isBanned = value;
	}
	@Override
	public void setPublic(boolean value) {
		this.isPublic = value;
	}
	public void setPremium(boolean value) {
		this.isPremium = value;
	}
	/**
	 * Returns the ID of the user who owns this viewable. Not applicable here.
	 * 
	 * @return Return -1 by default. No one should own a user.
	 */
	@Override
	public int ownedBy() {
		return -1;
	}
}
