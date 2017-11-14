package defunct.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

  /**
   * Redirect the user to the home page. If the user is not logged in, send
   * them to the user sign up page.
   * @param session Current session that should contain the logged in user.
   * @return The spotify homepage html if the user is logged in. The sign up page html otherwise.
   */
  @RequestMapping(value = "/homepage", method = RequestMethod.GET)
  public String getHomePage(HttpSession session) {
    if (session.getAttribute("User") == null) {
      return "logIn.html";
    }
    return "home.html";
  }
}
