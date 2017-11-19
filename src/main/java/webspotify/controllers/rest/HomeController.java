package webspotify.controllers.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

  /**
   * Direct the user to the login page. If there is a user logged in, direct them to
   * the homepage.
   * @param session Current session that may contain the logged in user.
   * @return The login page html if the user is not logged in. The homepage html otherwise.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String loginPage(HttpSession session) {
    if (session.getAttribute("User") == null) {
      return "logIn.html";
    }
    return "home.html";
  }

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

}