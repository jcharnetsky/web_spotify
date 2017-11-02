package web_spotify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LogInController {

    @RequestMapping(value="/attemptLogin", method= RequestMethod.GET)
    public String loginPage(HttpSession session){
        if(session.getAttribute("User") == null)
            return "logIn.html";
        return "homepage";
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String loginUser(@RequestParam(value="email", required=true) String email,
                            @RequestParam(value="password", required=true) String password,
                            HttpSession session){
        // Check if the Email and password match
        
        // Load the user into the session

        return "attemptLogin";
    }
    
}
