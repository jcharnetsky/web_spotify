package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webspotify.config.ConfigConstants;
import webspotify.models.users.User;
import webspotify.services.RoyaltyService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/bookkeeping")
public class BookKeepingController {

  @Autowired
  RoyaltyService royaltyService;

  @GetMapping("/monthly")
  public Response getMonthlyUpdate(HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if(user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_LOGGED);
    }
    return royaltyService.endOfMonthRoyalty(user);
  }

}
