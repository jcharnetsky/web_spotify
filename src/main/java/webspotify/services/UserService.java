package webspotify.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.media.SongQueue;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.posts.SignupRequest;
import webspotify.repo.UserRepository;
import webspotify.responses.ArtistResponse;
import webspotify.responses.UserInfoResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("userService")
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Transactional
  public Response getUserProfileInformation(User currentUser, int userId) {
    if (userRepository.exists(userId)) {
      User user = userRepository.findOne(userId);
      if (!user.isBanned() && user.isPublic()) {
        UserInfoResponse userInfo = new UserInfoResponse(user);
        userInfo.setFollowed(currentUser.getFollowing().contains(user));
        return ResponseUtilities.filledSuccess(userInfo);
      } else {
        return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
      }
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
  }

  @Transactional
  public User loginUser(String email, String password) {
    List<User> userList = userRepository.findByEmail(email);
    if (userList.size() != 1) {
      return null;
    }
    User user = userList.get(0);
    if (!user.authenticateLogin(password)) {
      return null;
    } else {
      return user;
    }
  }

  @Transactional
  public SongQueue getSongQueue() {
    return new SongQueue();
  }

  @Transactional
  public Response postUser(SignupRequest newUser) {
    if (!userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
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
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }
  
  @Transactional
  public Response deleteUser(User user) {
    userRepository.delete(user);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response followUser(User user, int userId) {
    if (userRepository.exists(userId)) {
      User userToFollow = userRepository.findOne(userId);
      if ((userToFollow.isPublic() && !userToFollow.isBanned()) && userId != user.getId()) {
        boolean successful = user.getFollowing().add(userToFollow);
        if (successful) {
          userRepository.save(user);
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
      User userToFollow = userRepository.findOne(userId);
      boolean successful = user.getFollowing().remove(userToFollow);
      if (successful) {
        userRepository.save(user);
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
    List<ArtistResponse> toReturn = new ArrayList<ArtistResponse>();
    for (User following: user.getFollowing()){
      if(following instanceof Artist){
        toReturn.add(new ArtistResponse((Artist) following));
      }
    }
    return ResponseUtilities.filledSuccess(toReturn);
  }

}
