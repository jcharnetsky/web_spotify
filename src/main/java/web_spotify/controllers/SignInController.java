package web_spotify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class SignInController {

    @RequestMapping("/")
    public String getMainPage(HttpSession session){
        if(session.getAttribute("User") == null)
            return "signIn.html";
        return "homepage";
    }
}
