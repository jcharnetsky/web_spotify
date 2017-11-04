package web_spotify.spotify;

/**
 * Represents a report
 *
 * @author Cardinals
 */
public class Report {

	/**
	 * The contents of the report
	 */
	private String description;
	
	/**
	 * The subject of the report
	 */
	private String subject;
	
	/**
	 * The id of the report
	 */
	private final int reportID;
	
	/**
	 * The default constructor of a report.
	 * @param description The contents of the report
	 * @param subject The subject of the report
	 * @param id The report id
	 */
    public Report(String description, String subject, int reportID) {
    		this.description = description;
    		this.subject = subject;
    		this.reportID = reportID;
    }
    
    /**
     * Set the description of the report.
     * @param description
     */
    public void setDescription(String description) {
    		this.description = description;
    }
    
    /**
     * Get the description of the report
     * @return
     */
    public String getDescription() {
    		return description;
    }
    
    /**
     * Set the subject of the report
     * @param subject
     */
    public void setSubject(String subject) {
    		this.subject = subject;
    }
    
    /**
     * Get the subject of the report
     * @return
     */
    public String getSubject() {
    		return subject;
    }
    
    /**
     * Get the reportID of the report
     * @return
     */
    public int getReportID() {
    		return reportID;
    }
}
