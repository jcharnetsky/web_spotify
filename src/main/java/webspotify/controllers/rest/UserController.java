package webspotify.controllers.rest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.Utilities.SessionUtilities;
import webspotify.posts.SignupRequest;
import webspotify.services.ResponseTuple;
import webspotify.services.UserService;

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
      return ResponseUtilities.filledFailure("User is already logged in.");
    }
    ResponseTuple responseTuple = userService.loginUser(email, password);
    if(!(responseTuple.getResponse().isError())) {
      session.setAttribute("User", responseTuple.getUser());
      session.setAttribute("Queue", responseTuple.getSongQueue());
      return responseTuple.getResponse();
    }
    else {
      return responseTuple.getResponse();
    }
  }
  
  @GetMapping("/logout")
  public Response logoutUser(HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) == null) {
      session.invalidate();
      return ResponseUtilities.filledFailure("User previously logged out.");
    }
    session.invalidate();
    return ResponseUtilities.emptySuccess();
  }
  
  @PostMapping("/register")
  public Response postUser(@RequestBody SignupRequest newUser, HttpSession session) {
    if (SessionUtilities.getUserFromSession(session) != null) {
      return ResponseUtilities.filledFailure("User is already logged in.");
    }
    return userService.postUser(newUser);
  }
}
