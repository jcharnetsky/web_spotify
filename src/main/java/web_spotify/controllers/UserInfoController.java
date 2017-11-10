package web_spotify.controllers;

import org.json.JSONObject;
import web_spotify.spotify.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserInfoController {

  /* Ignore get and change profile image for now */

  /**
   * Change the name of the logged in user to a specified name
   * @param newName The specified new name to change to
   * @param session Current session that should contain the user
   * @return A JSON object containing only the new user name
   * @throws SpotifyException If there is no logged in user
   */
  @RequestMapping(value = "/editName", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String editName(@RequestParam(value = "newName", required = true) String newName,
                         HttpSession session) throws SpotifyException {
    return "";
  }

  /**
   * Get the name of the logged in user
   * @param session Current session containing the logged in user
   * @return A JSON object containing the logged in user's name
   * @throws SpotifyException If there is no logged in user
   */
  @RequestMapping(value = "/getName", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getName(HttpSession session) throws SpotifyException {
    User user = (User) session.getAttribute("User");
    if (user == null) {
      throw new SpotifyException("Cannot get name if User is not logged in");
    }
    JSONObject name = new JSONObject();
    name.put("name", user.getName());
    return name.toString();
  }

  /**
   * Change the password of the logged in user to a specified password
   * @param newPassword The specified new password to change to
   * @param session Current session that should contain the user
   * @return A JSON object containing a success message
   * @throws SpotifyException If there is no logged in user or the password does not meet validation criteria
   */
  @RequestMapping(value = "/editPassword", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String editPassword(@RequestParam(value = "newPassword", required = true) String newPassword,
                             HttpSession session) throws SpotifyException {
    return "";
  }


}
