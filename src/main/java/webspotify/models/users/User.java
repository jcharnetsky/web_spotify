package webspotify.models.users;

import java.io.Serializable;
import java.security.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import webspotify.interfaces.Viewable;
import webspotify.models.media.Album;
import webspotify.models.media.Playlist;
import webspotify.models.media.Song;

/**
 * @author Cardinals
 */
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "BASE")
public class User implements Viewable, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID", nullable = false)
  private Integer id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "address", nullable = false)
  private String address;
  @Column(name = "birthdate", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date birthdate;
  @Column(name = "hasImage", nullable = false)
  private Boolean hasImage;
  @Column(name = "isbanned", nullable = false)
  private Boolean isBanned;
  @Column(name = "ispublic", nullable = false)
  private Boolean isPublic;
  @Column(name = "ispremium", nullable = false)
  private Boolean isPremium;
  @Column(name = "isdeleted", nullable = false)
  private Boolean isDeleted;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "salt", nullable = false)
  private String passwordSalt;
  @Column(name = "isdeleted", nullable = false)
  private Boolean isDeleted;
  @OneToMany(mappedBy = "owner")
  private Set<Playlist> ownedPlaylists;
  @ManyToMany
  @JoinTable(
          name = "usersFollowingPlaylists",
          joinColumns = @JoinColumn(name = "userID", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "playlistID", referencedColumnName = "id"))
  private Set<Playlist> followedPlaylists;
  @ManyToMany
  @JoinTable(
          name = "usersSavedAlbums",
          joinColumns = @JoinColumn(name = "userID", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "albumID", referencedColumnName = "id"))
  private Set<Album> savedAlbums;
  @ManyToMany
  @JoinTable(
          name = "usersSavedSongs",
          joinColumns = @JoinColumn(name = "userID", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "songID", referencedColumnName = "id"))
  private Set<Song> savedSongs;
  @Column(name = "followerCount", nullable = false)
  private Integer followerCount;
  @ManyToMany
  @JoinTable(name = "usersFollowingUsers",
          joinColumns = @JoinColumn(name = "userID"),
          inverseJoinColumns = @JoinColumn(name = "followingID")
  )
  private Set<User> following;

  public User() {
    this.ownedPlaylists = new HashSet<Playlist>();
    this.followedPlaylists = new HashSet<Playlist>();
    this.savedAlbums = new HashSet<Album>();
    this.savedSongs = new HashSet<Song>();
    this.following = new HashSet<User>();
    this.hasImage = false;
    this.followerCount = 0;
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

  public void incrementFollowerCount() {
    this.followerCount++;
  }

  public void decrementFollowerCount() {
    this.followerCount--;
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
    if(this.hasImage) {
      return "../images/users/" + this.getId() + ".jpg";
    } else {
      return "../images/users/logo.png";
    }
  }

  public Boolean getHasImage() {
    return hasImage;
  }

  public void setHasImage(Boolean hasImage) {
    this.hasImage = hasImage;
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
  
  public Boolean getIsDeleted() {
    return isDeleted;
  }
  
  public void setIsDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
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

  public Set<Playlist> getOwnedPlaylists() {
    return ownedPlaylists;
  }

  public void setOwnedPlaylists(Set<Playlist> ownedPlaylists) {
    this.ownedPlaylists = ownedPlaylists;
  }

  public Set<Playlist> getFollowedPlaylists() {
    return followedPlaylists;
  }

  public void setFollowedPlaylists(Set<Playlist> followedPlaylists) {
    this.followedPlaylists = followedPlaylists;
  }

  public Set<Album> getSavedAlbums() {
    return savedAlbums;
  }

  public void setSavedAlbums(Set<Album> savedAlbums) {
    this.savedAlbums = savedAlbums;
  }

  public Set<Song> getSavedSongs() {
    return savedSongs;
  }

  public void setSavedSongs(Set<Song> savedSongs) {
    this.savedSongs = savedSongs;
  }

  public Integer getFollowerCount() {
    return followerCount;
  }

  public void setFollowerCount(Integer followerCount) {
    this.followerCount = followerCount;
  }

  public Set<User> getFollowing() {
    return following;
  }

  public void setFollowing(Set<User> following) {
    this.following = following;
  }

  @Override
  public boolean isBanned() {
    return this.getIsBanned();
  }

  @Override
  public boolean isPublic() {
    return this.getIsPublic();
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

  public Boolean getDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }

  @Override
  public int hashCode() {
    return 7;
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
