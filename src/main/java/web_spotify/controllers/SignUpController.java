package web_spotify.controllers;

import Utils.DBUtils;
import Utils.JsonUtils;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web_spotify.spotify.User;

@Controller
public class SignUpController {

  /**
   * Redirect the user to the sign up page. If they are already logged in,
   * send them to the homepage.
   * @param session Current session that may contain the logged in user
   * @return The sign up page html.
   */
  @RequestMapping(value = "/signUpPage", method = RequestMethod.GET)
  public String signUpPage(HttpSession session) {
    return "signUp.html";
  }
  
//  @RequestMapping(value = "/signup", method = RequestMethod.GET, produces = "application/json")
//  @ResponseBody
//  public String signUpUser(@RequestParam(value = "name", required = true) String name,
//                          @RequestParam(value = "email", required = true) String email,
//                          @RequestParam(value = "birthdate", required = true) Date birthdate,
//                          @RequestParam(value = "password", required = true) String password,
//                          HttpSession session) throws SpotifyException {
//    User user = DBUtils.putUser(name, email, birthdate, password);
//    if (user != null) {
//      session.setAttribute("User", user);
//    } else {
//      throw new SpotifyException("Invalid Username/Password");
//    }
//    return JsonUtils.createBlankSuccess();
//  }

}
