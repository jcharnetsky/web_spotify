package webspotify.utilities;

import javax.servlet.http.HttpSession;
import webspotify.models.media.SongQueue;
import webspotify.models.users.User;

/**
 *
 * @author Cardinals
 */
public class SessionUtilities {
  public static User getUserFromSession(HttpSession session) {
    return (User)session.getAttribute("User");
  }
  public static SongQueue getSongQueueFromSession(HttpSession session) {
    return (SongQueue)session.getAttribute("SongQueue");
  }
}
