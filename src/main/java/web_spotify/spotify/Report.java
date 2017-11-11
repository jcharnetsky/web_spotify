package web_spotify.spotify;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

/**
 * Represents a report
 *
 * @author Cardinals
 */
@Entity
@Table(name = "Reports")
public class Report {
	
	/**
	 * The id of the report
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final int reportId;
	
	/**
	 * The subject of the report
	 */
	@Basic
	private String subject;
	
	/**
	 * The contents of the report
	 */
	@Basic
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
    
    /**
     * Compare Report objects to determine equivalence
     *
     * @param a object to compare
     * @return True if object is an instance of Administrator and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object r) {
        if((r != null) && (r instanceof Report)) {
            return ((Report) r).getReportId() == this.getReportId();
        } else return false;
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
