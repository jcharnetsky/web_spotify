package webspotify.models.administration;

import java.io.Serializable;
import javax.persistence.*;
import webspotify.models.users.User;
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
  @Column(name = "entity", nullable = false)
  @Enumerated(EnumType.STRING)
  private SpotifyObjectEnum entityType;
  @Column(name = "entityID", nullable = false)
  private Integer entityId;

  public Report() {
  }

  public Report(int reportId, String subject, String description, SpotifyObjectEnum entityType, Integer entityId) {
    this.reportId = reportId;
    this.subject = subject;
    this.description = description;
    this.entityType = entityType;
    this.entityId = entityId;
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
}
