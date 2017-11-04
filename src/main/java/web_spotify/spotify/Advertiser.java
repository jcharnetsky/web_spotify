package web_spotify.spotify;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents Companies that host advertisements on the service
 *
 * @author Cardinals
 */
public class Advertiser implements Viewable {

    /**
     * The name of the company
     */
    private final String companyName;

    /**
     * The id of this object
     */
    private final int id;
    
    /**
     * The collection of all advertisements
     */
    private final Collection<Advertisement> advertisements;

    /**
     * The default constructor for Advertiser
     *
     * @param companyName The name of the company
     */
    public Advertiser(String companyName, int id) {
        this.companyName = companyName;
        this.id = id;
        this.advertisements = new ArrayList<Advertisement>();
    }

    /* Implementations of abstract methods from Viewable */
    @Override
    public boolean isBanned() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isPublic() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int ownedBy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setBanned(boolean value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setPublic(boolean value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getId() {
        return id;
    }
    
    /**
     * Add an advertisement to the company
     *
     * @param ad The advertisement to add
     * @return True if the addition was successful, false otherwise.
     */
    public boolean addAdvertisement(Advertisement ad) {
        return this.advertisements.add(ad);
    }

    /**
     * Remove an advertisement from the company
     *
     * @param ad The advertisement to remove
     * @return True if the removal was successful, false otherwise.
     */
    public boolean remAdvertisement(Advertisement ad) {
        return this.advertisements.remove(ad);
    }
    
    /* GETTERS */

    public String getCompanyName() {
        return companyName;
    }

    public Collection<Advertisement> getAdvertisements() {
        return advertisements;
    }
    
    /**
     * Compare Advertiser objects to determine equivalence
     *
     * @param a object to compare
     * @return True if object is an instance of Advertiser and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object a) {
        if((a != null) && (a instanceof Advertiser)) {
            return ((Advertiser) a).getId() == this.getId();
        } else return false;
    }

    
}
