package web_spotify.spotify;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Advertiser extends User {
	@Basic
  private String companyName;
	@OneToMany
	@Column(name = "advertisements")
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
	 * @param advertisementId The advertisement to remove
	 */
	public void removeAdvertisement(int advertisementId) {
		for(int i = 0; i < advertisements.size(); i++) {
			if(((ArrayList<Advertisement>) advertisements).get(i).getId() == advertisementId) {
				((ArrayList<Advertisement>) advertisements).remove(i);
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
		}
		else {
			return false;
		}
	}
	public String getCompanyName() {
		return companyName;
	}
	public Collection<Advertisement> getAdvertisements() {
		return advertisements;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
