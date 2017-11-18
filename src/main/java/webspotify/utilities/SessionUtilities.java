package webspotify.utilities;

import javax.servlet.http.HttpSession;
import webspotify.config.ConfigConstants;
import webspotify.models.media.SongQueue;
import webspotify.models.users.User;

/**
 *
 * @author Cardinals
 */
public class SessionUtilities {
  public static User getUserFromSession(HttpSession session) {
    return (User)session.getAttribute(ConfigConstants.USER_SESSION);
  }
  public static SongQueue getSongQueueFromSession(HttpSession session) {
    return (SongQueue)session.getAttribute(ConfigConstants.QUEUE_SESSION);
  }
}
