package webspotify.services;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;
import webspotify.models.media.SongQueue;
import webspotify.models.users.User;
import webspotify.posts.SignupRequest;
import webspotify.repo.UserRepository;

@Service("userService")
public class UserService {
  @Autowired 
  UserRepository userRepository;
  
  @Transactional
  public ResponseTuple loginUser(String email, String password) {
    ResponseTuple responseTuple = new ResponseTuple();
    List<User> userList = userRepository.findByEmail(email);
    if (userList.size() != 1) {
      responseTuple.setResponse(ResponseUtilities.filledFailure("Email/Password combination is invalid."));
      return responseTuple;
    }
    User user = userList.get(0);
    if (!user.authenticateLogin(password)) {
      responseTuple.setResponse(ResponseUtilities.filledFailure("Email/Password combination is invalid."));
      return responseTuple;
    }
    responseTuple.setResponse(ResponseUtilities.emptySuccess());
    responseTuple.setUser(user);
    responseTuple.setSongQueue(new SongQueue());
    return responseTuple;
  }
  
  @Transactional
  public Response postUser(SignupRequest newUser) {
    if (userRepository.findByEmail(newUser.getEmail()).size() != 0) {
      return ResponseUtilities.filledFailure("Email address is already registered.");
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

