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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }
}
