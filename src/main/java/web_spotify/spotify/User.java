package web_spotify.spotify;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Represents the base user functionality for the Spotify project
 *
 * @author Cardinals
 */
public class User implements Viewable {

    /**
     * The user's unique identifier. CANNOT be changed.
     */
    private final int id;

    /**
     * The user's name.
     */
    private String name;

    /**
     * The email address associated with this user.
     */
    private String email;

    /**
     * The real world address associated with the user.
     */
    private String address;

    /**
     * The user's date of birth.
     */
    private Date birthday;
    
    /**
     * The URL of the user's profile picture.
     */
    private Image image;
    
    /**
     * The user's followers.
     */
    private Collection<User> followers;
    
    /**
     * The users this user is following.
     */
    private Collection<User> followedUsers;
    
    /**
     * The artists this user is following.
     */
    private Collection<Artist> followedArtists;
    
    /**
     * The user's saved albums.
     */
    private Collection<Album> savedAlbums;
    
    /** 
     * The playlists this user is following.
     */
    private Collection<Playlist> followedPlaylists;
    
    /**
     * The playlists the user either created or is collaborating on.
     */
    private Collection<Playlist> ownedPlaylists;
    
    /** 
     * The songs this user has saved.
     */
    private Playlist savedSongs;
    
    /** 
     * The user's song queue.
     */
    private SongQueue songQueue;
    
    /**
     * The collection of downloaded playlists.
     */
    private Collection<Playlist> downloadedPlaylists;

    /**
     * The collection of downloaded albums.
     */
    private Collection<Album> downloadedAlbums;

    /**
     * The collection of downloaded songs.
     */
    private Collection<Song> downloadedSongs;


    /**
     * A boolean which determines if the user is public. DEFAULT FALSE.
     */
    private boolean isPublic;

    /**
     * A boolean which determines if the user is banned. DEFAULT FALSE.
     */
    private boolean isBanned;
    
    /** 
     * A boolean which determines if the user is premium. DEFAULT FALSE.
     */
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
        this.id = id;
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
        savedSongs = new Playlist(id, "Songs", this, null, "", "");
        songQueue = new SongQueue();
        downloadedPlaylists = new ArrayList<Playlist>();
        downloadedAlbums = new ArrayList<Album>();
        downloadedSongs = new ArrayList<Song>();
    }
    
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
     * @param user The follower to be removed
     */
    public void removeFollower(User user) {
    		followers.remove(user);
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
     * @	param user The user to be unfollowed
     */
    public void unfollowUser(User user) {
    		followedUsers.remove(user);
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
     * @param artist The artist to be unfollowed
     */
    public void unfollowArtist(Artist artist) {
    		followedArtists.remove(artist);
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
     * @param album The album to be unsaved
     */
    public void unsaveAlbum(Album album) {
    		savedAlbums.remove(album);
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
     * @param playlist The playlist to be unfollowed
     */
    public void unfollowPlaylist(Playlist playlist) {
    		followedPlaylists.remove(playlist);
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
    public void createPlaylist(int id, String title, User owner, Image image, String genre, String description) {
    		Playlist playlist = new Playlist(id, title, owner, image, genre, description);
    		ownedPlaylists.add(playlist);
    }
    
    /**
     * Delete a custom playlist.
     * 
     * @param playlist The playlist to be deleted
     */
    public void deletePlaylist(Playlist playlist) {
    		ownedPlaylists.remove(playlist);
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
     * @param playlist Playlist to be no longer downloaded
     */
    public void removeDownloadedPlaylist(Playlist playlist) {
        downloadedPlaylists.remove(playlist);
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
     * @param album Album to be no longer downloaded
     */
    public void removeDownloadedAlbum(Album album) {
        downloadedAlbums.remove(album);
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
     * @param song Song to be no longer downloaded
     */
    public void removeDownloadedSong(Song song) {
    		downloadedSongs.remove(song);
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
    
    /* Getters */
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
    
    /* Setters */
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
