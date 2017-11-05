package web_spotify.spotify;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A collection of songs that are a playlist
 *
 * @author Cardinals
 */
class Playlist extends SongCollection {

    /**
     * The collection of followers
     */
    private Collection<User> followers;

    /**
     * The description of the playlist
     */
    private String description;

    /**
     * The default constructor of the Playlist Class
     *
     * @param id The unique id of the collection
     * @param title The title of the collection
     * @param owner The owner of the collection
     * @param image The image of the collection
     * @param genre The genre of the collection
     */
    public Playlist(int id, String title, User owner, String imageURL, String genre, String description) {
        super(id, title, owner, imageURL, genre);
        this.description = description;
        followers = new ArrayList<User>();
    }

    /**
     * Add a follower to the playlist
     *
     * @param user The user to be added to the playlist's followers
     */
    public boolean addFollower(User user) {
        return this.followers.add(user);
    }

    /**
     * Remove a follower from the playlist
     *
     * @param user The user to be removed from the playlist's followers
     * @return
     */
    public boolean removeFollower(User user) {
        return this.followers.remove(user);
    }

    /**
     * Change the description of the playlist
     *
     * @param description
     */
    public void changeDescription(String description) {
        this.description = description;
    }

    /**
     * Return the description of the playlist
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Return the list of followers.
     *
     * @return followers
     */
    public Collection<User> getFollowersList() {
        return this.followers;
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
}
