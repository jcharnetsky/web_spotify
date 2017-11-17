package webspotify.controllers.services;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.models.users.User;
import webspotify.repo.UserRepository;

@Service
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
    if(eUser.isEmpty()) {
      user.setEmail(email);
      userRepository.saveAndFlush(user);
      return ResponseUtilities.emptySuccess();
    }
    else {
      return ResponseUtilities.filledFailure("Set email unsuccessful.");
    }
  }
  
  @Transactional
  public Response setPremium(User user, Boolean premiumStatus) {
    user.setIsPremium(premiumStatus);
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }
}

