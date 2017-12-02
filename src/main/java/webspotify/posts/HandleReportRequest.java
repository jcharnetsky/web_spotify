package webspotify.posts;

import webspotify.types.ReportTypes;
import webspotify.types.SpotifyObjectEnum;

/**
 *
 * @author Cardinals
 */
public class HandleReportRequest {

  private ReportTypes reportType;
  private int reportId ;
  private SpotifyObjectEnum entityType;
  private int entityId;

  public ReportTypes getReportType() {
    return reportType;
  }

  public void setReportType(ReportTypes reportType) {
    this.reportType = reportType;
  }

  public int getReportId() {
    return reportId;
  }

  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  public SpotifyObjectEnum getEntityType() {
    return entityType;
  }

  public void setEntityType(SpotifyObjectEnum entityType) {
    this.entityType = entityType;
  }

  public int getEntityId() {
    return entityId;
  }

  public void setEntityId(int entityId) {
    this.entityId = entityId;
  }


}
