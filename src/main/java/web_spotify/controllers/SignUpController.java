package web_spotify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class SignUpController {

    /**
     * Redirect the user to the sign up page. If they are already logged in,
     * send them to the homepage.
     * @param session Current session that may contain the logged in user
     * @return The sign up page html if the user is logged in. The homepage html otherwise.
     */
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String signUpPage(HttpSession session){
        if(session.getAttribute("User") == null) {
            return "signUp.html";
        }
        return "home.html";
    }
}
