package web_spotify.spotify;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A collection of songs that are a playlist
 *
 * @author Cardinals
 */
class Playlist extends SongCollection {

	/**
     * The description of the playlist
     */
    private String description;
    
    /**
     * Represents whether the playlist is collaborative. By default is FALSE.
     */
    private boolean isCollaborative;
	
    /**
     * The collection of followers
     */
    private Collection<User> followers;

    /**
     * The default constructor of the Playlist Class
     *
     * @param id The unique id of the collection
     * @param title The title of the collection
     * @param owner The owner of the collection
     * @param image The image of the collection
     * @param genre The genre of the collection
     * @param description The description of the collection
     */
    public Playlist(int id, String title, User owner, Image image, Genre genre, String description) {
        super(id, title, owner, image, genre);
        this.description = description;
        this.isCollaborative = false;
        followers = new ArrayList<User>();
    }

    /**
     * Add a follower to the playlist
     *
     * @param user The user to be added to the playlist's followers
     */
    public void addFollower(User user) {
    		followers.add(user);
    }

    /**
     * Remove a follower from the playlist
     *
     * @param user The user to be removed from the playlist's followers
     * @return
     */
    public void removeFollower(User user) {
    		followers.remove(user);
    }
    
    /**
     * Compare Playlist objects to determine equivalence
     *
     * @param p object to compare
     * @return True if object is an instance of Playlist and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object p) {
        if((p != null) && (p instanceof Playlist)) {
            return ((Playlist) p).getId() == this.getId();
        } else return false;
    }
    
    /** Getters **/
    public String getDescription() {
        return description;
    }
    
    public boolean isCollaborative() {
    		return isCollaborative;
    }

    public Collection<User> getFollowersList() {
        return followers;
    }
    
    /** Setters **/
    public void setDescription(String description) {
    		this.description = description;
    }
    
    /**
     * Set collaborative to value. If true, public must be set to false.
     * @param value Whether or not playlist is collaborative
     */
    public void setCollaborative(boolean value) {
    		if(value) {
    			this.setPublic(false);
    		}
    		this.isCollaborative = value;
    }
}
