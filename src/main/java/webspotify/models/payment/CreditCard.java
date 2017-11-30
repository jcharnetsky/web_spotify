package webspotify.models.payment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

import webspotify.models.users.User;

/**
 * @author Cardinals
 */
@Entity
@Table(name = "creditcards")
public class CreditCard implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID", nullable = false)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "userID", nullable = false)
  private User owner;
  @Column(name = "ccn", nullable = false)
  private long ccn;
  @Column(name = "cvn", nullable = false)
  private Integer cvn;
  @Column(name = "cardholdername", nullable = false)
  private String cardholderName;
  @Column(name = "zipcode", nullable = false)
  private Integer zipCode;
  @Column(name = "expdate", nullable = false)
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
