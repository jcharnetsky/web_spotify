package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webspotify.config.ConfigConstants;
import webspotify.models.users.User;
import webspotify.responses.UserInfoResponse;
import webspotify.services.UserInfoService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

@RestController
@RequestMapping("/api/users/info")
public class UserInfoController {

  @Autowired
  private UserInfoService userInfoService;

  @GetMapping("/get/userInfo")
  public Response getUserInfo(HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    UserInfoResponse response = new UserInfoResponse(u);
    return ResponseUtilities.filledSuccess(response);
  }

  @PostMapping("/set/name")
  public Response setUserName(@RequestParam String name, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setName(u, name);
  }

  @PostMapping("/set/password")
  public Response setUserPassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setPassword(user, oldPassword, newPassword, confirmPassword);
  }

  @GetMapping("/get/email")
  public Response getUserEmail(HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return ResponseUtilities.filledSuccess(u.getEmail());
  }

  @PostMapping("/set/email")
  public Response setEmail(@RequestParam String email, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setEmail(u, email);
  }

  @PostMapping("/set/premium")
  public Response setUserPremium(@RequestParam Boolean premium, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setPremium(u, premium);
  }
  
  @PostMapping("/set/public")
  public Response setIsPublic(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if(user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.togglePublic(user);
  }
}
