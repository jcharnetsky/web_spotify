package defunct.spotify;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
//@Table(name = "advertisements")
public class Advertisement extends Song {
	@Column(name = "advertiser")
        @ManyToOne
	private Advertiser advertiser;

	public Advertisement(int artistId, int albumId, String title, int trackLength, byte[] audioFile, Advertiser advertiser) {
		super(artistId, albumId, title, trackLength, audioFile);
		this.advertiser = advertiser;
	}
	public Advertisement(){
		super();
	}
	public Advertiser getAdvertiser() {
		return advertiser;
	}
	/**
	 * Compare Advertisement objects to determine equivalence
	 *
	 * @param a object to compare
	 * @return True if object is an instance of Advertisement and has the same id; False otherwise
	 */
	@Override
	public boolean equals(Object a) {
		if((a != null) && (a instanceof Advertisement)) {
			return ((Advertisement) a).getId() == this.getId();
		}
		else {
			return false;
		}
	}
}