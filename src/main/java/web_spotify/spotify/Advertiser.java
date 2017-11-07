package web_spotify.spotify;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Represents companies that host advertisements on the service
 *
 * @author Cardinals
 */
public class Advertiser extends User {

    /**
     * The name of the company.
     */
    private String companyName;
    
    /**
     * The collection of all advertisements.
     */
    private Collection<Advertisement> advertisements;

    /**
     * The default constructor for Advertiser
     *
     * @param companyName The name of the company
     */
    public Advertiser(int id, String name, String email, String address, Date birthday, Image image, String companyName) {
    		super(id, name, email, address, birthday, null);
        this.companyName = companyName;
        advertisements = new ArrayList<Advertisement>();
    }
    
    /**
     * Add an advertisement to the company
     *
     * @param advertisement The advertisement to add
     */
    public void addAdvertisement(Advertisement advertisement) {
        advertisements.add(advertisement);
    }

    /**
     * Remove an advertisement from the company
     *
     * @param advertisement The advertisement to remove
     */
    public void removeAdvertisement(int advertisementId) {
    		for(int i = 0; i < advertisements.size(); i++) {
			if(((ArrayList<Advertisement>) advertisements).get(i).getId() == advertisementId) {
				((ArrayList<Advertisement>) advertisements).remove(i);
				return;
			}
		}
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
    
    /* Getters */
    public String getCompanyName() {
    		return companyName;
    }
    
    public Collection<Advertisement> getAdvertisements() {
    		return advertisements;
    }
    
    /* Setters */
    public void setCompanyName(String companyName) {
    		this.companyName = companyName;
    }
}
