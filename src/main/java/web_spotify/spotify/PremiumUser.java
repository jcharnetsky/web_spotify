package web_spotify.spotify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Represents a user who has a paid subscription to the service
 *
 * @author Cardinals
 */
public class PremiumUser extends User {

    /**
     * The collection of downloaded playlists
     */
    private Collection<Playlist> downloadedPlaylists;

    /**
     * The collection of downloaded albums
     */
    private Collection<Album> downloadedAlbums;

    /**
     * The collection of downloaded songs
     */
    private Collection<Song> downloadedSongs;

    /**
     * The default constructor of a premium User
     *
     * @param id The unique identifier for a particular user
     * @param name THe name of the User
     * @param email The current email of the user
     * @param address The physical address of the user
     * @param birthday The birthday of the user
     */
    public PremiumUser(int id, String name, String email, String address, Date birthday, String imageURL) {
        /* Call the super method */
        super(id, name, email, address, birthday, imageURL);

        /* Instantiate all collections */
        this.downloadedPlaylists = new ArrayList<Playlist>();
        this.downloadedAlbums = new ArrayList<Album>();
        this.downloadedSongs = new ArrayList<Song>();

    }

    /**
     * Add a downloaded playlist
     *
     * @param playlist Playlist to be added
     * @return True if the addition is successful, false if not
     */
    public boolean addDownloadedPlaylist(Playlist playlist) {
        return this.downloadedPlaylists.add(playlist);
    }

    /**
     * Remove a downloaded playlist
     *
     * @param playlist Playlist to be removed
     * @return True if the removal is successful, false if not
     */
    public boolean remDownloadedPlaylist(Playlist playlist) {
        return this.downloadedPlaylists.remove(playlist);
    }

    /**
     * Add a downloaded album
     *
     * @param album Album to be added.
     * @return True if the addition is successful, false if not
     */
    public boolean addDownloadedAlbum(Album album) {
        return this.downloadedAlbums.add(album);
    }

    /**
     * Remove a downloaded album
     *
     * @param album Album to be removed
     * @return True if the removal is successful, false if not
     */
    public boolean remDownloadedAlbum(Album album) {
        return this.downloadedAlbums.remove(album);
    }

    /**
     * Add a downloaded song
     *
     * @param song Song to be added
     * @return True if the addition is successful, false if not
     */
    public boolean addDownloadedSong(Song song) {
        return this.downloadedSongs.add(song);
    }

    /**
     * Remove a downloaded song
     *
     * @param song Song to be removed
     * @return True if the removal is successful, false if not
     */
    public boolean remDownloadedSong(Song song) {
        return this.downloadedSongs.remove(song);
    }
}
