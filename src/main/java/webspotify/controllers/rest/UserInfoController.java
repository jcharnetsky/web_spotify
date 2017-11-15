package webspotify.controllers.rest;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webspotify.Utilities.Response;
import webspotify.Utilities.ResponseUtilities;
import webspotify.models.users.User;
import webspotify.repo.UserRepository;

@RestController
@RequestMapping("/api/users/info")
public class UserInfoController {

    @Autowired
    private UserRepository userRepo;
    
    @GetMapping("/get/name")
    public Response getUserName(HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        return ResponseUtilities.filledSuccess(u.getName());
    }

    @GetMapping("/set/name")
    public Response setUserName(@RequestParam String name, HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        u.setName(name);
        userRepo.saveAndFlush(u);
        return ResponseUtilities.emptySuccess();
    }

    @GetMapping("/set/password")
    public Response setUserPassword(@RequestParam String password, HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        u.createSecurePassword(password);
        userRepo.saveAndFlush(u);
        return ResponseUtilities.emptySuccess();
    }

    @GetMapping("/get/email")
    public Response getUserEmail(HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        return ResponseUtilities.filledSuccess(u.getEmail());
    }

    @GetMapping("/set/email")
    public Response setUserEmail(@RequestParam String email, HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        List<User> userWithEmail = userRepo.findByEmail(email);
        if (userWithEmail.isEmpty()) {
            u.setEmail(email);
            userRepo.saveAndFlush(u);
            return ResponseUtilities.emptySuccess();
        } else {
            return ResponseUtilities.filledFailure("Email set unsuccessful.");
        }
    }
    
    @GetMapping("/set/premium")
    public Response setUserPremium(@RequestParam Boolean premium, HttpSession session) {
        User u = (User) session.getAttribute("User");
        if (u == null) {
            return ResponseUtilities.filledFailure("User is not logged in.");
        }
        u.setIsPremium(premium);
        userRepo.saveAndFlush(u);
        return ResponseUtilities.emptySuccess();
    }
}