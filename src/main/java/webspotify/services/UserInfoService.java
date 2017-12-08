package webspotify.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webspotify.config.ConfigConstants;
import webspotify.models.users.Administrator;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.repo.UserRepository;
import webspotify.repo.ArtistRepository;
import webspotify.responses.UserInfoResponse;
import webspotify.utilities.Response;
import webspotify.utilities.ResponseUtilities;

@Service("userInfoService")
public class UserInfoService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  ArtistRepository artistRepository;

  @Transactional
  public Response setName(User currentUser, int userId, String userName) {
    if (!userRepository.exists(userId)) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
    User user = userRepository.findOne(userId);
    if (!user.equals(currentUser) && !(currentUser instanceof Administrator)) {
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    } else {
      user.setName(userName);
      userRepository.saveAndFlush(user);
      return ResponseUtilities.emptySuccess();
    }
  }

  @Transactional
  public Response setPassword(User user, String oldPassword, String newPassword, String confirmPassword) {
    if (!user.authenticateLogin(oldPassword)) {
      return ResponseUtilities.filledFailure(ConfigConstants.INVALID_PASSWORD);
    }
    if (!(newPassword.equals(confirmPassword))) {
      return ResponseUtilities.filledFailure(ConfigConstants.PASSWORDS_NO_MATCH);
    }
    user.createSecurePassword(newPassword);
    userRepository.saveAndFlush(user);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response setEmail(User currentUser, int userId, String email) {
    if (!userRepository.exists(userId)) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
    User user = userRepository.findOne(userId);
    if (!user.equals(currentUser) && !(currentUser instanceof Administrator)) {
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
    String[] split = email.split("@");
    if (split.length != 2) {
      return ResponseUtilities.filledFailure(ConfigConstants.INVALID_EMAIL);
    }
    List<User> userList = userRepository.findByEmail(email);
    User validUser = findValidUser(userList);
    if (validUser == null) {
      user.setEmail(email);
      userRepository.saveAndFlush(user);
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.COULD_NOT_CHANGE);
    }
  }

  @Transactional
  public Response setAbout(Artist currentArtist, String about) {
    if (!artistRepository.exists(currentArtist.getId())) {
      return ResponseUtilities.filledFailure(ConfigConstants.USER_NOT_FOUND);
    }
    currentArtist.setAbout(about);
    artistRepository.save(currentArtist);
    return ResponseUtilities.emptySuccess();
  }

  public User findValidUser(List<User> userList) {
    for (User user : userList) {
      if (user.getIsDeleted() != true) {
        return user;
      }
    }
    return null;
  }

  @Transactional
  public Response setPremium(User user, Boolean premiumStatus) {
    User toToggle = userRepository.findOne(user.getId());
    toToggle.setIsPremium(premiumStatus);
    userRepository.save(toToggle);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response togglePublic(User user) {
    User toToggle = userRepository.findOne(user.getId());
    toToggle.setIsPublic(!toToggle.getIsPublic());
    userRepository.save(toToggle);
    return ResponseUtilities.emptySuccess();
  }

  @Transactional
  public Response getUserInfo(User user, int userId) {
    User userToCheck = userRepository.findOne(userId);
    if (user.equals(userToCheck) || user instanceof Administrator) {
      return ResponseUtilities.filledSuccess(new UserInfoResponse(userToCheck));
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
  }

  public Response toggleQuality(User user) {
    User toChange = userRepository.findOne(user.getId());
    if (toChange.getIsPremium()) {
      toChange.setHighQuality(!toChange.getHighQuality());
      userRepository.save(toChange);
      return ResponseUtilities.emptySuccess();
    } else {
      return ResponseUtilities.filledFailure(ConfigConstants.ACCESS_DENIED);
    }
  }
}
