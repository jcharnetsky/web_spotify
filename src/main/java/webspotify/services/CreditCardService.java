package webspotify.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.models.payment.CreditCard;
import webspotify.models.users.User;
import webspotify.posts.CreditCardCreateRequest;
import webspotify.repo.CreditCardRepository;
import webspotify.repo.UserRepository;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("creditCardService")
public class CreditCardService {

  @Autowired
  CreditCardRepository ccRepository;
  
  @Autowired
  UserRepository userRepository;

  @Transactional
  public Response postCreditCard(User user, CreditCardCreateRequest creditCard) {
    User owner = userRepository.findOne(user.getId());
    CreditCard newCreditCard = new CreditCard();
    newCreditCard.setCardholderName(creditCard.getCardholderName());
    newCreditCard.setCCN(creditCard.getCCN());
    newCreditCard.setCVN(creditCard.getCVN());
    newCreditCard.setExpDate(creditCard.getExpDate());
    newCreditCard.setZipCode(creditCard.getZipCode());
    newCreditCard.setOwner(owner);
    ccRepository.saveAndFlush(newCreditCard);
    return ResponseUtilities.emptySuccess();
  }
}
