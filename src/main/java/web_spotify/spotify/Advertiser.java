package web_spotify.spotify;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents Companies that host advertisements on the service
 *
 * @author Cardinals
 */
public class Advertiser {

    /**
     * The name of the company
     */
    private final String companyName;

    /**
     * The collection of all advertisements
     */
    private final Collection<Advertisement> advertisements;

    /**
     * The default constructor for Advertiser
     *
     * @param companyName The name of the company
     */
    public Advertiser(String companyName) {
        this.companyName = companyName;

        this.advertisements = new ArrayList<Advertisement>();
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
    

}
