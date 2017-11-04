package web_spotify.spotify;

import java.awt.Image;

/**
 * A collection of songs that are an album
 * @author Cardinals
 */
class Album extends SongCollection {
    
    public Album(int id, String title, User owner, Image image, String genre) {
        super(id, title, owner, image, genre);
    }
    
}
