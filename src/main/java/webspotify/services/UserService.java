package webspotify.services;

import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;
import webspotify.config.ConfigConstants;
import webspotify.models.media.SongQueue;
import webspotify.models.users.Administrator;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.posts.SignupRequest;
import webspotify.repo.UserRepository;
import webspotify.responses.ArtistResponse;
import webspotify.responses.BasicUserInfoResponse;
import webspotify.responses.UserInfoResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("userService")
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Transactional
  public Response getUserProfileInformation(User user, int userId) {
    if (userRepository.exists(userId)) {
      User currentUser = userRepository.findOne(user.getId());
      User userToFind = userRepository.findOne(userId);
      if (!userToFind.isBanned() && userToFind.isPublic()) {
        UserInfoResponse userInfo = new UserInfoResponse(userToFind);
        userInfo.setFollowed(currentUser.getFollowing().contains(userToFind));
        return ResponseUtilities.filledSuccess(userInfo);
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
  }

  @Transactional
  public User loginUser(String email, String password) throws LoginException {
    List<User> userList = userRepository.findByEmail(email);
    User validUser = null;
    for (User user: userList){
      if(user.getIsDeleted()){
        throw new LoginException(ConfigConstants.USER_DELETED);
      } else if (user.getIsBanned()) {
        throw new LoginException(ConfigConstants.USER_BANNED);
      } else {
         validUser = user;
      }
    }
    if(validUser == null){
      throw new LoginException(ConfigConstants.EMAIL_NO_EXIST);
    }
    if (!validUser.authenticateLogin(password)) {
      throw new LoginException(ConfigConstants.INVALID_CREDENTIALS);
    }
    return validUser;
  }

  @Transactional
  public SongQueue getSongQueue() {
    return new SongQueue();
  }
  
  public User findValidUser(List<User> userList) {
    for(User user : userList) {
      if(!user.getIsDeleted()) {
        return user;
      }
    }
    return null;
  }

  @Transactional
  public Response postUser(SignupRequest newUser) {
    List<User> userList = userRepository.findByEmail(newUser.getEmail());
    User validUser = findValidUser(userList);
    if (validUser != null) {
      return ResponseUtilities.filledFailure(ConfigConstants.EMAIL_EXIST);
    }
    User user = new User();
    user.setAddress(newUser.getAddress());
    user.setBirthdate(newUser.getBirthdate());
    user.setEmail(newUser.getEmail());
    user.setName(newUser.getName());
    user.createSecurePassword(newUser.getPassword());
    user.setHasImage(false);
    user.setIsBanned(false);
    user.setIsPremium(false);
    user.setIsPublic(true);
    user.setIsDeleted(false);
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }
  
  @Transactional
  public Response deleteUser(int userId, User currentUser, HttpSession session) {
    if(!userRepository.exists(userId)){
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
    User user = userRepository.findOne(userId);
    if (currentUser instanceof Administrator) {
      if(currentUser.getId() == user.getId()) {
        return ResponseUtilities.filledFailure(ConfigConstants.ADMIN_NO_DELETE);
      } else {
        user.setIsDeleted(true);
        userRepository.save(user);
        return ResponseUtilities.filledSuccess(new BasicUserInfoResponse(user));
      }
    } else if (currentUser.equals(user)){
      user.setIsDeleted(true);
      userRepository.save(user);
      session.invalidate();
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
  }

  @Transactional
  public Response followUser(User user, int userId) {
    if (userRepository.exists(userId)) {
      User userToFollow = userRepository.findOne(userId);
      User userToChange = userRepository.findOne(user.getId());
      if ((userToFollow.isPublic() && !userToFollow.isBanned()) && userId != user.getId()) {
        boolean successful = userToChange.getFollowing().add(userToFollow);
        if (successful) {
          userRepository.save(userToChange);
          userToFollow.incrementFollowerCount();
          userRepository.save(userToFollow);
          return ResponseUtilities.emptySuccess();
        } else {
          return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_ADD);
        }
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
  }

  @Transactional
  public Response unfollowUser(User user, int userId) {
    if (userRepository.exists(userId)) {
      User userToChange = userRepository.findOne(user.getId());
      User userToFollow = userRepository.findOne(userId);
      boolean successful = userToChange.getFollowing().remove(userToFollow);
      if (successful) {
        userRepository.save(userToChange);
        userToFollow.decrementFollowerCount();
        userRepository.save(userToFollow);
        return ResponseUtilities.emptySuccess();
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_REM);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
  }

  @Transactional
  public Response getFollowedArtists(User user){
    User userToCheck = userRepository.findOne(user.getId());
    List<ArtistResponse> toReturn = new ArrayList<ArtistResponse>();
    for (User following: userToCheck.getFollowing()){
      if(following instanceof Artist){
        toReturn.add(new ArtistResponse((Artist) following));
      }
    }
    return ResponseUtilities.filledSuccess(toReturn);
  }

}
