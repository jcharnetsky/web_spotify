package webspotify.models.users;

import javax.persistence.*;

/**
 * @author Cardinals
 */
@Entity
@Table(name = "advertiser")
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "ADVERT")
public class Advertiser extends User{
  public Advertiser(User toCopy){
    super(toCopy);
  }
}
