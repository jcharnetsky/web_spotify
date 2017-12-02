package webspotify.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webspotify.config.ConfigConstants;
import webspotify.models.payment.CreditCard;
import webspotify.models.users.User;
import webspotify.posts.CreditCardCreateRequest;
import webspotify.repo.CreditCardRepository;
import webspotify.repo.UserRepository;
import webspotify.types.CreditCardType;
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
    System.out.println("luhns check: " + luhnsCheck(creditCard.getCCN()));
    if(!verifyValidCC(creditCard.getType(), creditCard.getCCN(), creditCard.getCVN()) || !luhnsCheck(creditCard.getCCN())) {
      return ResponseUtilities.filledFailure(ConfigConstants.INVALID_CC);
    }
    User owner = userRepository.findOne(user.getId());
    CreditCard newCreditCard = new CreditCard();
    newCreditCard.setCardholderName(creditCard.getCardholderName());
    newCreditCard.setCCN(creditCard.getCCN());
    newCreditCard.setCVN(creditCard.getCVN());
    newCreditCard.setZipCode(creditCard.getZipCode());
    newCreditCard.setExpDate(creditCard.getExpDate());
    newCreditCard.setType(creditCard.getType());
    newCreditCard.setOwner(owner);
    ccRepository.saveAndFlush(newCreditCard);
    return ResponseUtilities.emptySuccess();
  }
  
  public Boolean luhnsCheck(long CCN) {
    int checkDigit = (int)(CCN % 10);
    int position = 0;
    int sum = 0;
    CCN /= 10;
    System.out.println("checkDigit: " + checkDigit);
    System.out.println("position: " + position);
    System.out.println("sum: " + sum);
    System.out.println("CCN: " + CCN);
    while(CCN > 0) {
      int digit = (int)(CCN % 10);
      System.out.println("digit: " + digit);
      if(position % 2 == 0) {
        System.out.println("Doubling");
        digit *= 2;
        if(digit >= 10) {
          sum += 1 + digit % 10;
        }
        else {
          sum += digit;
        }
      }
      else {
        sum += digit;
      }
      position++;
      CCN /= 10;
      System.out.println("sum: " + sum);
      System.out.println("position: " + position);
      System.out.println("CCN: " + CCN);
    }
    if((sum * 9) % 10 == checkDigit) {
      return true;
    }
    return false;
  }
  
  public Boolean verifyValidCC(CreditCardType type, long CCN, int CVN) {
    String ccn = String.valueOf(CCN);
    int length = ccn.length();
    int cvnLength = String.valueOf(CVN).length();
    if(type == CreditCardType.VISA && cvnLength == 3) {
      String regex = "4[0-9]{12}|4[0-9]{15}|4[0-9]{18}";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(ccn);
      return matcher.find();
    }
    if(type == CreditCardType.MASTERCARD && cvnLength == 3) {
      String regex = "5[1-5][0-9]{14}";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(ccn);
      if(matcher.find()) {
        return true;
      }
      regex = "(222[1-9]|22[3-6][0-9]|2270)";
      pattern = Pattern.compile(regex);
      matcher = pattern.matcher(ccn);
      if(matcher.find() && length == 16) {
        return true;
      }
      return false;
    }
    if(type == CreditCardType.AMEX && cvnLength == 4) {
      String regex = "^[3][47]\\d{13}$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(ccn);
      return matcher.find();
    }
    if(type == CreditCardType.DISCOVER && cvnLength == 3) {
      String start = ccn.substring(0, 4);
      System.out.println("start: " + start);
      if(start.equals("6011") && length >= 16 && length <= 19) {
        return true;
      }
      start = ccn.substring(0, 2);
      if((start.equals("64") || start.equals("65")) && length >= 16 && length <= 19) {
        return true;
      }
      return false;
    }
    return false;
  }
}
