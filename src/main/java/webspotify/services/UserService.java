package webspotify.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.models.media.SongQueue;
import webspotify.models.users.User;
import webspotify.posts.SignupRequest;
import webspotify.repo.UserRepository;
import webspotify.responses.UserInfoResponse;

@Service("userService")
public class UserService {

  @Autowired
  UserRepository userRepository;

    
  @Transactional
  public Response getUserProfileInformation(int userId) {
   if(userRepository.exists(userId)) {
     User user = userRepository.findOne(userId);
     if(!user.isBanned() && user.isPublic()) {
       return ResponseUtilities.filledSuccess(new UserInfoResponse(user));
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
    }
    else {
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
    user.setImage("");
    user.setIsBanned(false);
    user.setIsPremium(false);
    user.setIsPublic(true);
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }

}
