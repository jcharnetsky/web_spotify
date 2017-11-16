package webspotify.posts;
import java.io.Serializable;

/**
 *
 * @author Cardinals
 */
public class ConcertArtistChangeRequest implements Serializable {
	private int concertID;
	private int artistID;
	
	public ConcertArtistChangeRequest() {
	}
	
	public int getConcertID() {
		return concertID;
	}
	
	public void setConcertID(int concertID) {
		this.concertID = concertID;
	}
	
	public int getArtistID() {
		return artistID;
	}
	
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}
}
