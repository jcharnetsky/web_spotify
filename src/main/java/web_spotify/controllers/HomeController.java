package web_spotify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("/homepage")
    public String getHomePage(){
        return "home.html";
    }
}
