package webspotify.posts;

import webspotify.types.CreditCardType;

/**
 *
 * @author Cardinals
 */
public class CreditCardCreateRequest {
  
  private long ccn;
  private Integer cvn;
  private String cardholderName;
  private Integer zipCode;
  private String expDate;
  private CreditCardType type;

  public CreditCardCreateRequest() {
  }

  public long getCCN() {
    return ccn;
  }
  
  public void setCCN(long ccn) {
    this.ccn = ccn;
  }
  
  public Integer getCVN() {
    return cvn;
  }
  
  public void setCVN(Integer cvn) {
    this.cvn = cvn;
  }
  
  public String getCardholderName() {
    return cardholderName;
  }
  
  public void setCardholderName(String cardholderName) {
    this.cardholderName = cardholderName;
  }
  
  public Integer getZipCode() {
    return zipCode;
  }
  
  public void setZipCode(Integer zipCode) {
    this.zipCode = zipCode;
  }
  
  public String getExpDate() {
    return expDate;
  }
  
  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }
  
  public CreditCardType getType() {
    return type;
  }
  
  public void setType(CreditCardType type) {
    this.type = type;
  }
}
