package web_spotify.spotify;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomePageController {

    @RequestMapping("/index.html")
    public String getMainPage(Model model, HttpSession session){
        return "home";
    }
}
