package web_spotify.spotify;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents an administrator
 *
 * @author Cardinals
 */
public class Administrator extends PremiumUser {

	/**
	 * A collection of the reports
	 */
	private Collection<Report> reports;
	
    /**
     * The default constructor of an administrator
     *
     * @param id The unique identifier for an administrator
     * @param name THe name of the administrator
     * @param email The current email of the administrator
     * @param address The physical address of the administrator
     * @param birthday The birthday of the administrator
     * @return 
     */
    public Administrator(int id, String name, String email, String address, Date birthday, String imageURL) {
        /* Call the super method */
        super(id, name, email, address, birthday, imageURL);

        /* Instantiate all collections */
        reports = new ArrayList<Report>();
    }
    
    /**
     * Return the collection of reports
     * @return
     */
    public Collection<Report> getReports() {
    		return reports;
    }
    
    /** 
     * Add a report to the collection of reports
     * @param report
     */
    public void addReport(Report report) {
    		this.reports.add(report);
    }
    
    /**
     * Remove a report from the collection of reports
     */
    public boolean removeReport(Report report) {
    		return this.reports.remove(report);
    }
    
    /**
     * Ban a user
     */
    public void banUser(User user) {
    		user.setBanned(true);
    }
    
    /**
     * Ban a user
     */
    public void banSong(Song song) {
    		song.setBanned(true);
    }
    
    /**
     * Ban a user
     */
    public void banAlbum(Album album) {
    		album.setBanned(true);
    }
    
    /**
     * Ban a user
     */
    public void banPlaylist(Playlist playlist) {
    		playlist.setBanned(true);
    }
    
    /**
     * Compare Administrator objects to determine equivalence
     *
     * @param a object to compare
     * @return True if object is an instance of Administrator and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object a) {
        if((a != null) && (a instanceof Administrator)) {
            return ((Administrator) a).getId() == this.getId();
        } else return false;
    }
}
