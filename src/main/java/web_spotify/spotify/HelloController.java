package web_spotify.spotify;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/meme")
    public String start(Model model){
        return "home";
    }
}
