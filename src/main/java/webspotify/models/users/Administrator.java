package webspotify.models.users;

import javax.persistence.*;
/**
 * @author Cardinals
 */
@Entity
@Table(name = "administrator")
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "ADMIN")
public class Administrator extends User{
}
