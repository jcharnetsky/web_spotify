package webspotify.responses;

import webspotify.models.users.User;

import java.util.Date;

/**
 * @author Cardinals
 */
public class UserResponse {

  private String name;
  private String email;
  private Date birthday;

  public UserResponse(User user) {
    this.name  = user.getName();
    this.email = user.getEmail();
    this.birthday = user.getBirthdate();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
}
