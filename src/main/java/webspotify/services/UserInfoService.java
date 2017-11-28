package webspotify.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.users.User;
import webspotify.repo.UserRepository;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("userInfoService")
public class UserInfoService {

  @Autowired
  UserRepository userRepository;

  @Transactional
  public Response setName(User user, String userName) {
    user.setName(userName);
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response setPassword(User user, String password) {
    user.createSecurePassword(password);
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response setEmail(User user, String email) {
    List<User> eUser = userRepository.findByEmail(email);
    if (eUser.isEmpty()) {
      user.setEmail(email);
      userRepository.saveAndFlush(user);
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_CHANGE);
    }
  }

  @Transactional
  public Response setPremium(User user, Boolean premiumStatus) {
    user.setIsPremium(premiumStatus);
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }
  
  @Transactional
  public Response togglePublic(User user) {
    Boolean toggled = !(user.getIsPublic());
    user.setIsPublic(toggled);
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }
}
