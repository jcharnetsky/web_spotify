package defunct.spotify;
import java.sql.Date;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity
public class Administrator extends User {
	@Column(name = "reports")
	private Collection<Report> reports;
	/**
	 * The default constructor of an administrator
	 *
	 * @param id The unique identifier for an administrator
	 * @param name THe name of the administrator
	 * @param email The current email of the administrator
	 * @param address The physical address of the administrator
	 * @param birthday The birthday of the administrator
	 */
	public Administrator(int id, String name, String email, String address, Date birthday, Image image) {
		super(id, name, email, address, birthday, null);
		reports = new ArrayList<Report>();
	}
	public Administrator(){}
	/** 
	 * Add a report to the collection of reports
	 * 
	 * @param report A report
	 */
	public void addReport(Report report) {
		this.reports.add(report);
	}
	/**
	 * Remove a report from the collection of reports
	 * 
	 * @param reportId ID of the report to remove
	 */
	public void removeReport(int reportId) {
		for(int i = 0; i < reports.size(); i++) {
			if(((ArrayList<Report>) reports).get(i).getReportId() == reportId) {
				((ArrayList<Report>) reports).remove(i);
			}
		}
	}
	/**
	 * Ban a user
	 * 
	 * @param user A user of Spotify
	 */
	public void banUser(User user) {
		user.setBanned(true);
	}
	/** Unban a user
	 * 
	 * @param user A user of Spotify
	 */
	public void unbanUser(User user) {
		user.setBanned(false);;
	}
	/**
	 * Ban a song
	 * 
	 * @param song A song currently listed on Spotify
	 */
	public void banSong(Song song) {
		song.setBanned(true);
	}
	/**
	 * Unban a song
	 * 
	 * @param song A song currently listed on Spotify
	 */
	public void unbanSong(Song song) {
		song.setBanned(false);
	}
	/**
	 * Ban an album
	 * 
	 * @param album An album currently listed on Spotify
	 */
	public void banAlbum(Album album) {
		album.setBanned(true);
	}
	/**
	 * Unban an album
	 * 
	 * @param album An album currently listed on Spotify
	 */
	public void unbanAlbum(Album album) {
		album.setBanned(false);
	}
	/**
	 * Ban a playlist
	 * 
	 * @param playlist A playlist currently listed on Spotify
	 */
	public void banPlaylist(Playlist playlist) {
		playlist.setBanned(true);
	}
	/**
	 * Unban a playlist
	 * 
	 * @param playlist A playlist currently listed on Spotify
	 */
	public void unbanPlaylist(Playlist playlist) {
		playlist.setBanned(false);
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
		}
		else {
			return false;
		}
	}
	public Collection<Report> getReports() {
		return reports;
	}
}
