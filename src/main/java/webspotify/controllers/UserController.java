package webspotify.controllers;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.models.users.Artist;
import webspotify.models.users.User;
import webspotify.posts.SignupRequest;
import webspotify.repo.UserRepository;

/**
 *
 * @author Cardinals
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public Response loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
        if (session.getAttribute("User") != null) {
            return ResponseUtilities.filledFailure("User is already logged in.");
        }
        List<User> userList = userRepo.findByEmail(email);
        if (userList.size() != 1) {
            return ResponseUtilities.filledFailure("Email/Password combination is invalid.");
        }
        User user = userList.get(0);
        if (!user.authenticateLogin(password)) {
            return ResponseUtilities.filledFailure("Email/Password combination is invalid.");
        }
        session.setAttribute("User", user);
        return ResponseUtilities.emptySuccess();
    }

    @GetMapping("/logout")
    public Response logoutUser(HttpSession session) {
        if (session.getAttribute("User") == null) {
            session.invalidate();
            return ResponseUtilities.filledFailure("User previously logged out.");
        }
        session.invalidate();
        return ResponseUtilities.emptySuccess();
    }

    @PostMapping("/register")
    public Response postUser(@RequestBody SignupRequest newUser, HttpSession session) {
        if (session.getAttribute("User") != null) {
            return ResponseUtilities.filledFailure("User is already logged in.");
        }
        if (userRepo.findByEmail(newUser.getEmail()).size() > 0) {
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
        userRepo.saveAndFlush(user);
        return ResponseUtilities.emptySuccess();
    }

    @GetMapping("/info/get/name")
    public Response getUserName(HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        return ResponseUtilities.filledSuccess(u.getName());
    }

    @GetMapping("/info/set/name")
    public Response setUserName(@RequestParam String newName, HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        u.setName(newName);
        userRepo.saveAndFlush(u);
        return ResponseUtilities.emptySuccess();
    }

    @GetMapping("/info/set/password")
    public Response setUserPassword(@RequestParam String password, HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        u.createSecurePassword(password);
        userRepo.saveAndFlush(u);
        return ResponseUtilities.emptySuccess();
    }
}
