package web_spotify.spotify;

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
    public Album(int id, String title, User owner, String imageURL, String genre, boolean isPublic, boolean isBanned) {
        super(id, title, owner, imageURL, genre, isPublic, isBanned);
    }

}
