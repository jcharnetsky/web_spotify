package web_spotify.spotify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Concerts")
class Concert implements Viewable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final int concertId;
	@Basic
	private String venueName;
	@Basic
	private String concertName;
	@ManyToMany
	@Column(name = "artists")
	private Collection<Artist> artists;
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;
	@Basic
	private String URL;
	@Basic
	private boolean isPublic;
	@Basic
	private boolean isBanned;
	/**
	 * The constructor of the Concert Class
	 *
	 * @param concertId The ID of the concert
	 * @param venueName The name of the venue
	 * @param concertName The name of the concert
	 * @param artists The collection of artists at the concert
	 * @param date The date of the concert
	 * @param url The url at which to purchase tickets
	 */
	public Concert(int concertId, String venueName, String concertName, Collection<Artist> artists, Date date, String URL) {
		this.concertId = concertId;
		this.venueName = venueName;
		this.concertName = concertName;
		this.artists = artists;
		this.date = date;
		this.URL = URL;
		isPublic = false;
		isBanned = false;
	}
	/**
	 * Adds an artist to the current venue
	 *
	 * @param artist The artist to be added to the venue
	 */
	public void addArtist(Artist artist) {
		this.artists.add(artist);
	}
	/**
	 * Removes an artist to the current venue
	 *
	 * @param artist The artist to be removed to the venue
	 */
	public void removeArtist(int artistId) {
		for(int i = 0; i < artists.size(); i++) {
			if(((ArrayList<Artist>)artists).get(i).getId() == artistId) {
				((ArrayList<Artist>)artists).remove(i);
				return;
			}
		}
	}
	/**
	 * Compare Concert objects to determine equivalence
	 *
	 * @param c object to compare
	 * @return True if object is an instance of Concert and has the same id; False otherwise
	 */
	@Override
	public boolean equals(Object c) {
		if((c != null) && (c instanceof Concert)) {
			return ((Concert) c).getId() == this.getId();
		} else return false;
	}
	@Override
	public int getId() {
		return concertId;
	}
	public String getVenueName() {
		return venueName;
	}
	public String getConcertName() {
		return concertName;
	}
	public Collection<Artist> getArtists() {
		return artists;
	}
	public Date getDate() {
		return date;
	}
	public String getURL() {
		return URL;
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
		return -1;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public void setConcertName(String concertName) {
		this.concertName	= concertName;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setURL(String URL) {
		this.URL = URL;
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
