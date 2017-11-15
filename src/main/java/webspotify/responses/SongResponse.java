package webspotify.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import webspotify.models.media.Song;
import webspotify.models.users.Artist;
import webspotify.models.media.Concert;

/**
 *
 * @author Cardinals
 */
public class SongResponse {

    private String trackName;
    private Integer trackLength;
    private ArtistResponse artist;

    public SongResponse(Song build) {
        this.trackLength = build.getTrackLength();
        this.trackName = build.getTitle();
        this.artist = new ArtistResponse(build.getOwner());
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Integer getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(Integer trackLength) {
        this.trackLength = trackLength;
    }

    public ArtistResponse getArtist() {
        return artist;
    }

    public void setArtist(ArtistResponse artist) {
        this.artist = artist;
    }

}
