package web_spotify.controllers;

import Utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogInController {

    @RequestMapping(value="/attemptLogin", method= RequestMethod.GET)
    public String loginPage(HttpSession session){
        if(session.getAttribute("User") == null)
            return "logIn.html";
        return "homepage";
    }

    @RequestMapping(value="/login", method= RequestMethod.GET, produces = "application/json")
	@ResponseBody
    public String loginUser(@RequestParam(value="email", required=true) String email,
                            @RequestParam(value="password", required=true) String password,
                            HttpSession session){
        

        final String validEmail = "user@yahoo.com";
        final String validPassword = "password";

        String errorMessage = "";

        boolean valid = false;

        // Load the user into the session
        if(validEmail.equals(email) && validPassword.equals(password)) {
            valid = true;
            session.setAttribute("User", "HI");
        } else {
            errorMessage = "Invalid Username/Password";
        }

        return valid ? JsonUtils.createBlankSuccess() : JsonUtils.createBlankError(errorMessage);
    }
    
}
