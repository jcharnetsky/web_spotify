package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

  @GetMapping("/get/userInfo/{userId}")
  public Response getUserInfo(@PathVariable final int userId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.getUserInfo(user, userId);
  }

  @GetMapping("/get/userInfo/self")
  public Response getUserInfo(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.getUserInfo(user, user.getId());
  }

  @PostMapping("/set/name")
  public Response setUserName(@RequestParam String name, @RequestParam int userId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setName(user, userId, name);
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
  public Response setEmail(@RequestParam String email, @RequestParam int userId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setEmail(user, userId, email);
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
