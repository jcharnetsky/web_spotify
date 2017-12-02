package webspotify.responses;

import webspotify.models.administration.Report;
import webspotify.types.ReportTypes;
import webspotify.types.SpotifyObjectEnum;

public class ReportResponse {

  private Integer id;
  private String creatorName;
  private Integer entityId;
  private String subject;
  private String description;
  private ReportTypes reportType;
  private SpotifyObjectEnum entityType;

  public ReportResponse(Report report){
    this.id = report.getReportId();
    this.creatorName = report.getCreator().getName();
    this.entityId = report.getEntityId();
    this.subject = report.getSubject();
    this.description = report.getDescription();
    this.reportType = report.getReportType();
    this.entityType = report.getEntityType();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public Integer getEntityId() {
    return entityId;
  }

  public void setEntityId(Integer entityId) {
    this.entityId = entityId;
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

  public SpotifyObjectEnum getEntityType() {
    return entityType;
  }

  public void setEntityType(SpotifyObjectEnum entityType) {
    this.entityType = entityType;
  }
}
