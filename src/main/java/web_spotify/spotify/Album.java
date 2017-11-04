package web_spotify.spotify;

import java.awt.Image;

/**
 * A collection of songs that are an album
 *
 * @author Cardinals
 */
class Album extends SongCollection {

    /**
     * The default constructor of the Album Class
     *
     * @param id The unique id of the collection
     * @param title The title of the collection
     * @param owner The owner of the collection
     * @param image The image of the collection
     * @param genre The genre of the collection
     */
    public Album(int id, String title, User owner, Image image, String genre) {
        super(id, title, owner, image, genre);
    }

    /**
     * Compare Album objects to determine equivalence
     *
     * @param a object to compare
     * @return True if object is an instance of Album and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object a) {
        if((a != null) && (a instanceof Album)) {
            return ((Album) a).getId() == this.getId();
        } else return false;
    }
    
}
