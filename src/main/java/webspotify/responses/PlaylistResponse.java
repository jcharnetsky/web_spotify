package webspotify.responses;

import java.util.ArrayList;
import java.util.List;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;
import webspotify.types.GenreType;

/**
 *
 * @author Cardinals
 */
public class PlaylistResponse {

    private Integer id;
    private Boolean collaborative;
    private String description;
    private GenreType genre;
    private String title;
    private List<SongResponse> songs;
    
    public PlaylistResponse(Playlist playlist) {
        this.id = playlist.getId();
        this.title = playlist.getTitle();
        this.genre = playlist.getGenre();
        this.description = playlist.getDescription();
        this.collaborative = playlist.getCollaborative();
        this.songs = new ArrayList<SongResponse>();
        for(Song song : playlist.getSongs()) {
            this.songs.add(new SongResponse(song));
        }
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GenreType getGenre() {
        return genre;
    }

    public void setGenre(GenreType genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SongResponse> getSongs() {
        return songs;
    }

    public void setSongs(List<SongResponse> songs) {
        this.songs = songs;
    }
    
    
}
