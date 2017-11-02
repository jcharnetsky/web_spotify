package web_spotify.controllers;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web_spotify.spotify.Focus;
import web_spotify.spotify.Chart;

import javax.servlet.http.HttpSession;


@Controller
public class HomeController {

    @RequestMapping(value="/homepage", method= RequestMethod.GET)
    public String getHomePage(Model model, HttpSession session){
        // Check to make sure that the user is logged in to view this page


        // Grab content for page

        return "home.html";
    }
}
