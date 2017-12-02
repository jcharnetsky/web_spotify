package webspotify.models.administration;

import java.io.Serializable;
import javax.persistence.*;
import webspotify.models.users.User;
import webspotify.types.ReportTypes;
import webspotify.types.SpotifyObjectEnum;

/**
 *
 * @author Cardinals
 */
@Entity
@Table(name = "Reports")
public class Report implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "reportID", nullable = false)
  private int reportId;
  @ManyToOne
  @JoinColumn(name = "creatorID", nullable = false)
  private User creator;
  @Column(name = "subject", nullable = false)
  private String subject;
  @Column(name = "description", nullable = false)
  private String description;
  @Enumerated(EnumType.STRING)
  @Column(name = "reportType", nullable = false)
  private ReportTypes reportType;
  @Column(name = "entity", nullable = false)
  @Enumerated(EnumType.STRING)
  private SpotifyObjectEnum entityType;
  @Column(name = "entityID", nullable = false)
  private Integer entityId;
  @Column(name = "completed", nullable = false)
  private Boolean completed;

  public Report() {
  }

  public Report(int reportId, String subject, String description,
                SpotifyObjectEnum entityType, ReportTypes reportType,
                Integer entityId, boolean completed) {
    this.reportId = reportId;
    this.subject = subject;
    this.description = description;
    this.reportType = reportType;
    this.entityType = entityType;
    this.entityId = entityId;
    this.completed = completed;
  }

  public int getReportId() {
    return reportId;
  }

  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ReportTypes getReportType() {
    return reportType;
  }

  public void setReportType(ReportTypes reportType) {
    this.reportType = reportType;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public SpotifyObjectEnum getEntityType() {
    return entityType;
  }

  public void setEntityType(SpotifyObjectEnum entityType) {
    this.entityType = entityType;
  }

  public Integer getEntityId() {
    return entityId;
  }

  public void setEntityId(Integer entityId) {
    this.entityId = entityId;
  }

  public boolean getCompleted() { return this.completed; }
}
