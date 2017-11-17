package webspotify.controllers.rest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.controllers.services.ResponseTuple;
import webspotify.controllers.services.UserService;
import webspotify.posts.SignupRequest;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/users")
public class UserController { 
  UserService userService;
  
  @GetMapping("/login")
  public Response loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
    if (session.getAttribute("User") != null) {
      return ResponseUtilities.filledFailure("User is already logged in.");
    }
    ResponseTuple responseTuple = userService.loginUser(email, password, session);
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
    if (session.getAttribute("User") == null) {
      session.invalidate();
      return ResponseUtilities.filledFailure("User previously logged out.");
    }
    session.invalidate();
    return ResponseUtilities.emptySuccess();
  }
  
  @PostMapping("/register")
  public Response postUser(@RequestBody SignupRequest newUser, HttpSession session) {
    if (session.getAttribute("User") != null) {
      return ResponseUtilities.filledFailure("User is already logged in.");
    }
    return userService.postUser(newUser, session);
  }
}
