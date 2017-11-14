package defunct.spotify;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;

@Entity
@Table(name = "Songs")
@SecondaryTables({
	@SecondaryTable(name = "SongAudio", pkJoinColumns = @PrimaryKeyJoinColumn(name = "songID"))
	,
	@SecondaryTable(name = "SongListens", pkJoinColumns = @PrimaryKeyJoinColumn(name = "songID"))
})
public class Song implements Viewable, Playable, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int songId;
	@OneToMany
	@Column(name = "artistID", table = "Songs")
	private int artistId;
	@OneToMany
	@Column(name = "albumID", table = "Songs")
	private int albumId;
	@Column(name = "title", table = "Songs")
	private String title;
	@Column(name = "trackLength", table = "Songs")
	private int trackLength;
	@Column(name = "audio", table = "SongAudio")
	private byte[] audioFile;
	@Column(name = "count", table = "SongListens")
	private int monthlyListens;
	private int totalListens;
	@Column(name = "isBanned", table = "Songs")
	private boolean isBanned;
	@Column(name = "isPublic", table = "Songs")
	private boolean isPublic;

	public Song() {
	}
	/**
	 * Default constructor for a song
	 *
	 * @param songId The song id
	 * @param artistId The id of the song's artist
	 * @param albumId The id of the song's album
	 * @param featuredArtists The collection of the song's featured artists
	 * @param title The title of the song
	 * @param trackLength The runtime of the song
	 */
	public Song(int artistId, int albumId, String title, int trackLength, byte[] audioFile) {
		this.artistId = artistId;
		this.albumId = albumId;
		this.title = title;
		this.trackLength = trackLength;
		this.audioFile = audioFile;
		monthlyListens = 0;
		totalListens = 0;
		isBanned = false;
		isPublic = false;
	}
	/**
	 * Increments the monthly listens by 1
	 */
	@Override
	public void incrementMonthlyListens() {
		this.monthlyListens++;
	}
	/**
	 * Sets monthly listens to 0
	 */
	@Override
	public void resetMonthlyListens() {
		this.monthlyListens = 0;
	}
	/**
	 * Increments the total listens by 1
	 */
	@Override
	public void incrementTotalListens() {
		this.totalListens++;
	}
	/**
	 * Compare Song objects to determine equivalence
	 *
	 * @param s object to compare
	 * @return True if object is an instance of Song and has the same id; False
	 * otherwise
	 */
	@Override
	public boolean equals(Object s) {
		if ((s != null) && (s instanceof Song)) {
			return ((Song) s).getId() == this.getId();
		} else {
			return false;
		}
	}
	@Override
	public int getId() {
		return songId;
	}
	@Override
	public int ownedBy() {
		return artistId;
	}
	public int getAlbumid() {
		return albumId;
	}
	public String getTitle() {
		return title;
	}
	@Override
	public double getTrackLength() {
		return trackLength;
	}
	public int getMonthlyListens() {
		return monthlyListens;
	}
	public int getTotalListens() {
		return totalListens;
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
	public boolean isAvailable() {
		return audioFile.length != 0;
	}
	@Override
	public void setBanned(boolean value) {
		isBanned = value;
	}
	@Override
	public void setPublic(boolean value) {
		isPublic = value;
	}
}
