package web_spotify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class SignUpController {

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getMainPage(HttpSession session){
        if(session.getAttribute("User") == null) {
            return "signUp.html";
        }
        return "home.html";
    }
}
