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
    public Administrator(int id, String name, String email, String address, Date birthday) {
        /* Call the super method */
        super(id, name, email, address, birthday);

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
    
}
