package webspotify.controllers.rest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import webspotify.config.ConfigConstants;
import webspotify.models.users.User;
import webspotify.posts.CreditCardCreateRequest;
import webspotify.services.CreditCardService;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.utilities.SessionUtilities;

/**
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/creditcards")
public class CreditCardController {

  @Autowired
  CreditCardService ccService;
  
  @PostMapping("/post")
  public Response postCreditCard(@RequestBody CreditCardCreateRequest creditCard, HttpSession session) {
    User user = SessionUtilities.getUserFromSession(session);
    if (user == null) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
    return ccService.postCreditCard(user, creditCard);
  }
  
}
