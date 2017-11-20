package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.models.users.User;
import webspotify.responses.UserResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;
import webspotify.posts.SignupRequest;
import webspotify.services.UserService;
import webspotify.config.ConfigConstants;
import webspotify.utilities.ResponseTuple;

/**
 *
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
    ResponseTuple responseTuple = userService.loginUser(email, password);
    if (!(responseTuple.getResponse().isError())) {
      session.setAttribute(ConfigConstants.USER_SESSION, responseTuple.getUser());
      session.setAttribute(ConfigConstants.QUEUE_SESSION, responseTuple.getSongQueue());
      return responseTuple.getResponse();
    } else {
      return responseTuple.getResponse();
    }
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
    if (SessionUtilities.getUserFromSession(session) != null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_ALREADY_LOGGED);
    }
    return userService.postUser(newUser);
  }

  @GetMapping("/get/{userId}")
  public Response getUser(@PathVariable final int userId, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return userService.getUserProfileInformation(userId);
  }
}
