package web_spotify.spotify;

/**
 * Represents a report
 *
 * @author Cardinals
 */
public class Report {
	
	/**
	 * The id of the report
	 */
	private final int reportId;
	
	/**
	 * The subject of the report
	 */
	private String subject;
	
	/**
	 * The contents of the report
	 */
	private String description;
	
	/**
	 * The default constructor of a report.
	 * @param description The contents of the report
	 * @param subject The subject of the report
	 * @param id The report id
	 */
    public Report(int reportId, String subject, String description) {
    		this.reportId = reportId;
    		this.subject = subject;
    		this.description = description;
    }
    
    /** Getters **/
    public int getReportId() {
    		return reportId;
    }
    
    public String getSubject() {
    		return subject;
    }
    
    public String getDescription() {
    		return description;
    }
    
    /** Setters **/
    public void setSubject(String subject) {
    		this.subject = subject;
    }
    
    public void setDescription(String description) {
    		this.description = description;
    }
}
