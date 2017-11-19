package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webspotify.config.ConfigConstants;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.responses.UserInfoResponse;
import webspotify.services.UserInfoService;

@RestController
@RequestMapping("/api/users/info")
public class UserInfoController {

  @Autowired
  private UserInfoService userInfoService;

  @GetMapping("/get/userInfo")
  public Response getUserName(HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    UserInfoResponse response = new UserInfoResponse(u.getName(), u.getIsPremium(), u instanceof Artist, false);
    return ResponseUtilities.filledSuccess(response);
  }

  @GetMapping("/set/name")
  public Response setUserName(@RequestParam String name, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setName(u, name);
  }

  @GetMapping("/set/password")
  public Response setUserPassword(@RequestParam String password, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setPassword(u, password);
  }

  @GetMapping("/get/email")
  public Response getUserEmail(HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return ResponseUtilities.filledSuccess(u.getEmail());
  }

  @GetMapping("/set/email")
  public Response setEmail(@RequestParam String email, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setEmail(u, email);
  }

  @GetMapping("/set/premium")
  public Response setUserPremium(@RequestParam Boolean premium, HttpSession session) {
    User u = SessionUtilities.getUserFromSession(session);
    if (u == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userInfoService.setPremium(u, premium);
  }
}
