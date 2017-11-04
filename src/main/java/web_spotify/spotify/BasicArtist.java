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
    public BasicArtist(int id, String name, String email, String address, Date birthday, String bio, String imageURL, Collection<String> aliases) {
        // Call the constructor
        super(id, name, email, address, birthday, imageURL);
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
     * Set the bio of the artist
     * @param bio
     */
    public void setBio(String bio) {
    		this.bio = bio;
    }
    
    /**
     * Get the bio of the artist
     */
    public String getBio() {
    		return bio;
    }
    
    /**
     * Add a song to an artist's owned songs
     * @param song
     */
    public void addOwnedSong(Song song) {
    		ownedSongs.add(song);
    }
    
    /** 
     * Remove a song from an artist's owned songs
     * @param song
     */
    public void removeOwnedSong(Song song) {
    		ownedSongs.remove(song);
    }
    
    /**
     * Get an artist's owned songs
     */
    public Collection<Song> getOwnedSongs() {
    		return ownedSongs;
    }
    
    /**
     * Add an album to an artist's owned albums
     * @param album
     */
    public void addOwnedAlbum(Album album) {
    		ownedAlbums.add(album);
    }
    
    /** 
     * Remove an album from an artist's owned songs
     * @param song
     */
    public void removeOwnedAlbum(Album album) {
    		ownedAlbums.remove(album);
    }
    
    /**
     * Get an artist's owned albums
     */
    public Collection<Album> getOwnedAlbums() {
    		return ownedAlbums;
    }
    
    /**
     * Add an concert to an artist's concerts
     * @param concert
     */
    public void addConcerts(Concert concert) {
    		concerts.add(concert);
    }
    
    /** 
     * Remove a concert from an artist's concerts
     * @param concert
     */
    public void removeOwnedConcert(Concert concert) {
    		concerts.remove(concert);
    }
    
    /**
     * Get an artist's concerts
     */
    public Collection<Concert> getConcerts() {
    		return concerts;
    }
    
    /**
     * Add an alias to an artist's aliases
     * @param string
     */
    public void addAlias(String alias) {
    		aliases.add(alias);
    }
    
    /** 
     * Remove an alias from an artist's aliases
     * @param alias
     */
    public void removeAlias(String alias) {
    		aliases.remove(alias);
    }
    
    /**
     * Get an artist's aliases
     */
    public Collection<String> getAliases() {
    		return aliases;
    }
}
