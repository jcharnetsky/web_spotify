package web_spotify.controllers;

import web_spotify.Utils.DBUtils;
import web_spotify.spotify.Playlist;

import web_spotify.Utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ResponseBody;
import web_spotify.spotify.User;

@Controller
public class LogInController {

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
    return "homepage";
  }

  /**
   * Attempt to log in a user given their email and password credentials. If the email and password
   * match a user account in the database, add the user to the session and return a JSON object containing a success
   * message. Otherwise, return a JSON object containing an error saying that the credentials did not match. If a
   * user is already logged in, also return an error saying that a user is logged in.
   * @param email    The given email of the user attempting to log in.
   * @param password The given password of the user attempting to log in.
   * @param session  Current session that may contain a logged in user.
   * @return If the credentials match and no user is logged in, a JSON containing a success message.
   * @throws SpotifyException If the credentials did not match or a user is already logged in.
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String loginUser(@RequestParam(value = "email", required = true) String email,
                          @RequestParam(value = "password", required = true) String password,
                          HttpSession session) throws SpotifyException {
    if (session.getAttribute("User") != null) {
      throw new SpotifyException("User already logged in.");
    }

    User user = DBUtils.getUser(email, password);
    
    if (user != null) {
//      User owner = new User(1, "Owner","owner@gmail.com", null, null, null);
//      String[] playlists = {"On the Road", "Spotify and Chill", "Spotify top 50 Sweden", "Spotify Sessions",
//                            "Rock Classics", "Pop Rising", "Liked from Radio"};
//      for (int i = 0; i < playlists.length; i++) {
//        user.followPlaylist(new Playlist(i, playlists[i], owner, null, null, null));
//      }
//      session.setAttribute("User", user);
        session.setAttribute("User", user);
    } else {
      throw new SpotifyException("Invalid Username/Password");
    }
    return JsonUtils.createBlankSuccess();
  }

  /**
   * Remove the user from the current session object and return a JSON object with a success message. If a user is
   * not logged in, return a JSON object with a failure message.
   * @param session Current session that may contain a logged in user
   * @return If the user was successfully logged out, a success JSON object. Otherwise, a failure JSON object.
   * @throws SpotifyException If there was no user to log out
   */
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logoutUser(HttpSession session) throws SpotifyException {
    session.invalidate();
    return "";
  }

}
