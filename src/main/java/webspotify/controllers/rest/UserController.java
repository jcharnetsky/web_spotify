package webspotify.controllers.rest;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import webspotify.config.ConfigConstants;
import webspotify.models.users.Administrator;
import webspotify.models.users.User;
import webspotify.posts.SignupRequest;
import webspotify.services.UserService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/login")
  public Response loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) != null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_ALREADY_LOGGED);
    }
    User user;
    try {
      user = userService.loginUser(email, password);
    } catch (LoginException e){
      return ResponseUtilities.filledFailure(e.getMessage());
    }
    session.setAttribute(ConfigConstants.USER_SESSION, user);
    session.setAttribute(ConfigConstants.QUEUE_SESSION, userService.getSongQueue());
    return ResponseUtilities.emptySuccess();
  }

  @GetMapping("/logout")
  public Response logoutUser(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      session.invalidate();
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    session.invalidate();
    return ResponseUtilities.emptySuccess();
  }

  @PostMapping("/register")
  public Response postUser(@RequestBody SignupRequest newUser, HttpSession session) {
    User creator = SessionUtilities.getUserFromSession(session);
    if (creator != null || creator instanceof Administrator) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_ALREADY_LOGGED);
    }
    return userService.postUser(creator, newUser);
  }
  
  @PostMapping("/delete/{userId}")
  public Response deleteUser(@PathVariable final int userId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if(user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
    return userService.deleteUser(userId, user, session);
  }

  @GetMapping("/get/{userId}")
  public Response getUser(@PathVariable final int userId, HttpSession session) {
    User currentUser = SessionUtilities.getUserFromSession(session);
    if (currentUser == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userService.getUserProfileInformation(currentUser, userId);
  }

  @PostMapping("/follow/{userId}")
  public Response followUser(@PathVariable final int userId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userService.followUser(user, userId);
  }

  @PostMapping("/unfollow/{userId}")
  public Response unfollowUser(@PathVariable final int userId, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userService.unfollowUser(user, userId);
  }

  @GetMapping("/get/followedArtists")
  public Response getFollowedArtists(HttpSession session){
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userService.getFollowedArtists(user);
  }
  
}
