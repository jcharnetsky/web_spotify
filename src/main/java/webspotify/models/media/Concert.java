package webspotify.models.media;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import webspotify.interfaces.Viewable;
import webspotify.models.users.Artist;

/**
 *
 * @author Cardinals
 */
@Entity
@Table(name = "concerts")
public class Concert implements Serializable, Viewable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable=false)
	private Integer id;
	@Column(name = "venue", nullable=false)
	private String venueName;
	@Column(name = "concertDate", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date concertDate;
	@Column(name = "url", nullable=false)
	private String concertURL;
	@Column(name = "isPublic", nullable=false)
	private Boolean isPublic;
	@Column(name = "isBanned", nullable=false)
	private Boolean isBanned;
	@ManyToMany
	@JoinTable(
					name = "artistConcerts",
					joinColumns = @JoinColumn(name = "concertID", referencedColumnName = "id"),
					inverseJoinColumns = @JoinColumn(name = "artistID", referencedColumnName = "id"))
	private Set<Artist> artists;

	public Concert() {
            artists = new HashSet<Artist>();
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public Date getConcertDate() {
		return concertDate;
	}

	public void setConcertDate(Date concertDate) {
		this.concertDate = concertDate;
	}

	public String getConcertURL() {
		return concertURL;
	}

	public void setConcertURL(String concertURL) {
		this.concertURL = concertURL;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Boolean getIsBanned() {
		return isBanned;
	}

	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Set<Artist> getArtists() {
		return artists;
	}

	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}

	@Override
	public boolean isBanned() {
		return this.getIsBanned();
	}

	@Override
	public boolean isPublic() {
		return this.getIsPublic();
	}

	@Override
	public int ownedBy() {
		return -1;
	}

	@Override
	public void setBanned(boolean value) {
		this.setIsBanned(value);
	}

	@Override
	public void setPublic(boolean value) {
		this.setIsPublic(isPublic);
	}

}
