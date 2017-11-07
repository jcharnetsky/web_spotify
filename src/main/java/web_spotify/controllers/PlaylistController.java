package web_spotify.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlaylistController {

    @RequestMapping(value="/", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String createPlaylist(String name){return "";}

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String deletePlaylist(String name){return "";}

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlaylistData(int playlistId){return "";}

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlaylists(){return "";}

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String followPlaylist(String name, int ownerId){return "";}

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String unfollowPlaylist(String name, int ownerId){return "";}

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String addSongToPlaylist(int songId, String name){return "";}

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String removeSongFromPlaylist(int songId, int playlistId){return "";}

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String changeTitle(String newTitle, String playlistName){return "";}

    // Leave out changeImage for now

    @RequestMapping(value="/overview", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String changePrivacy(int playlistId, boolean isPublic){return "";}
}
