package webspotify.models.users;

import java.io.Serializable;
import java.security.*;
import java.util.Date;
import javax.persistence.*;
import webspotify.interfaces.Viewable;

/**
 *
 * @author Cardinals
 */
@Entity
@Table(name = "Users")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="userType", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="BASE")
public class User implements Viewable, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable=false)
	private Integer id;
	@Column(name = "name", nullable=false)
	private String name;
	@Column(name = "email", nullable=false)
	private String email;
	@Column(name = "address", nullable=false)
	private String address;
	@Column(name = "birthdate", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	@Column(name = "image", nullable=false)
	private String image;
	@Column(name = "isbanned", nullable=false)
	private Boolean isBanned;
	@Column(name = "ispublic", nullable=false)
	private Boolean isPublic;
	@Column(name = "ispremium", nullable=false)
	private Boolean isPremium;
	@Column(name = "password", nullable=false)
	private String password;
	@Column(name = "salt", nullable=false)
	private String passwordSalt;

	public User() {
	}

	public boolean createSecurePassword(final String plainPassword) {
		try {
			SecureRandom rand = new SecureRandom();
			byte[] preSalt = new byte[12];
			rand.nextBytes(preSalt);
			String saltValue = new String(preSalt);
			String saltedPassword = saltValue + plainPassword;
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(saltedPassword.getBytes());
			String hashedPassword = new String(md.digest());
			this.password = hashedPassword;
			this.passwordSalt = saltValue;
			return true;
		} catch (Exception e) {
			System.out.println("Error occurred on Password Creation.");
			return false;
		}
	}

	public boolean authenticateLogin(final String plainPassword) {
		try {
			String saltedPassword = this.passwordSalt + plainPassword;
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(saltedPassword.getBytes());
			String hashedPassword = new String(md.digest());
			return hashedPassword.equals(this.password);
		} catch (Exception ex) {
			System.out.println("Error occurred on Password Auth.");
			return false;
		}
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getIsBanned() {
		return isBanned;
	}

	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Boolean getIsPremium() {
		return isPremium;
	}

	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	@Override
	public boolean isBanned() {
		return this.getIsBanned();
	}

	@Override
	public boolean isPublic() {
		return this.getIsPremium();
	}

	@Override
	public int ownedBy() {
		return -1;
	}

	@Override
	public void setBanned(boolean value) {
		this.setIsBanned(value);
	}

	@Override
	public void setPublic(boolean value) {
		this.setIsPublic(value);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
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
		final User other = (User) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

}
