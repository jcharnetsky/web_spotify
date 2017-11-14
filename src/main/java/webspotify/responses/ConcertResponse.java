package webspotify.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import webspotify.models.users.Artist;
import webspotify.models.users.Concert;

/**
 *
 * @author Cardinals
 */
public class ConcertResponse {
	private String venueName;
	private Date concertDate;
	private String concertURL;
	private List<ArtistResponse> artists;
	
	public ConcertResponse (Concert build) {
		this.venueName = build.getVenueName();
		this.concertDate = build.getConcertDate();
		this.concertURL = build.getConcertURL();
		this.artists = new ArrayList<ArtistResponse>();
		for (Artist artist : build.getArtists()) {
			this.artists.add(new ArtistResponse(artist));
		}
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

	public List<ArtistResponse> getArtists() {
		return artists;
	}

	public void setArtists(List<ArtistResponse> artists) {
		this.artists = artists;
	}
	
}
