package web_spotify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LogInController {

    @RequestMapping("/attemptLogin")
    public String loginPage(HttpSession session){
        if(session.getAttribute("User") == null)
            return "logIn.html";
        return "homepage";
    }

    @RequestMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
                            HttpSession session){
        // Check if the Email and password match

        // Load the user into the session

        return "attemptLogin";
    }
}
