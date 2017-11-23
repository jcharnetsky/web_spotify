package webspotify.responses;

import webspotify.models.users.User;

/**
 * @author Cardinals
 */
public class BasicUserInfoResponse {

  private Integer id;
  private String name;
  private String imageLink;

  public BasicUserInfoResponse(User user){
    this.id = user.getId();
    this.name = user.getName();
    this.imageLink = user.getImage();
  }
}
