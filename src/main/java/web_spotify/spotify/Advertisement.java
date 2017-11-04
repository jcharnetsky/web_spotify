package web_spotify.spotify;

/**
 * Represents an advertisement that a user can play
 *
 * @author Cardinals
 */
public class Advertisement {

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