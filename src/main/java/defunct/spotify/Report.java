package defunct.spotify;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "Reports")
public class Report implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int reportId;
	@Basic
	private String subject;
	@Basic
	private String description;

    public Report() {}
	/**
	 * The default constructor of a report.
	 * @param description The contents of the report
	 * @param subject The subject of the report
	 * @param reportId The report id
	 */
	public Report(int reportId, String subject, String description) {
		this.reportId = reportId;
		this.subject = subject;
		this.description = description;
	}
	/**
	 * Compare Report objects to determine equivalence
	 *
	 * @param r The object to compare
	 * @return True if object is an instance of Administrator and has the same id; False otherwise
	 */
	@Override
	public boolean equals(Object r) {
		if((r != null) && (r instanceof Report)) return ((Report) r).getReportId() == this.getReportId();
		else return false;
	}
	public int getReportId() {
		return reportId;
	}
	public String getSubject() {
		return subject;
	}
	public String getDescription() {
		return description;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
