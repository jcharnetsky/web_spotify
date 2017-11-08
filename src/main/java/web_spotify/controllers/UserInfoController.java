package web_spotify.controllers;

import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import web_spotify.spotify.User;
import web_spotify.spotify.Playlist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;

@Controller
public class UserInfoController {

    // Ignore get and change profile image for now


    @RequestMapping(value="/editName", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String editName(String newName, HttpSession session) throws SpotifyException{
        return "";
    }

    @RequestMapping(value="/getName", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getName(String newName, HttpSession session) throws SpotifyException{
        return "";
    }

    @RequestMapping(value="/editPassword", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String editPassword(String newPassword, HttpSession session) throws SpotifyException{
        return "";
    }


}
