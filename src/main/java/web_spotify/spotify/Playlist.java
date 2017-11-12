package web_spotify.spotify;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Playlists")
public class Playlist extends SongCollection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Basic
  private String description;
	@Basic
  private boolean isCollaborative = false;
	@ManyToMany
	@Column(name = "followers")
  private Collection<User> followers;
	@ManyToMany
	@Column(name = "songs")
  private List<Song> songs;
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
  public Playlist(int id, String title, User owner, String image, Genre genre, String description) {
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
   * @param userId ID of user to be removed from the playlist's followers
   */
  public void removeFollower(int userId) {
    for(int i = 0; i < followers.size(); i++) {
      if(((ArrayList<User>)followers).get(i).getId() == userId) ((ArrayList<User>)followers).remove(i);
    }
  }
  /**
   * Compare Playlist objects to determine equivalence
   *
   * @param p object to compare
   * @return True if object is an instance of Playlist and has the same id; False otherwise
   */
  @Override
  public boolean equals(Object p) {
		if((p != null) && (p instanceof Playlist)) return ((Playlist) p).getId() == this.getId();
		else return false;
  }
  public String getDescription() {
		return description;
  }
  public boolean isCollaborative() {
		return isCollaborative;
  }
  public Collection<User> getFollowersList() {
		return followers;
  }
  public void setDescription(String description) {
		this.description = description;
  }
  /**
   * Set collaborative to value. If true, public must be set to false.
	 * 
   * @param value Whether or not playlist is collaborative
   */
  public void setCollaborative(boolean value) {
		if(value) this.setPublic(false);
		this.isCollaborative = value;
  }
}
