package web_spotify.spotify;

import java.util.ArrayList;

/**
 * Represents an advertisement that a user can play
 *
 * @author Cardinals
 */
public class Advertisement extends Song {

	/**
	 * The advertiser who owns this advertisement
	 */
	private final Advertiser advertiser;

    public Advertisement(int artistId, int albumId, String title, int trackLength, byte[] audioFile, Advertiser advertiser) {
    		super(artistId, albumId, title, trackLength, audioFile);
    		this.advertiser = advertiser;
	}

    /** Getters **/
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
        } else return false;
    }
}