package webspotify.models.payment;

import java.io.Serializable;
import webspotify.models.users.User;

/**
 * @author Cardinals
 */
public class CreditCard implements Serializable {
  
  private Integer id;
  private User owner;
  private String ccn;
  private Integer cvn;
  private String cardholderName;
  private Integer zipCode;
  private String expDate;

  public CreditCard() {
  }
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
  public User getOwner() {
    return owner;
  }
  
  public void setOwner(User owner) {
    this.owner = owner;
  }
  
  public String getCCN() {
    return ccn;
  }
  
  public void setCCN(String ccn) {
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
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CreditCard other = (CreditCard) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }
}
