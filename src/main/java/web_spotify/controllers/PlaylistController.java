package web_spotify.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlaylistController {

    /**
     * Create a playlist for the logged in user with the specified name
     * @param name The specified name.
     * @param session Current session containing the logged in user.
     * @return JSON containing the information of the playlist just created or an error message
     * if a playlist with that name already exists.
     */
    @RequestMapping(value="/createPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String createPlaylist(@RequestParam(value = "name", required = true)String name, HttpSession session)
    {return "";}

    /**
     * Delete for the logged in user with the specified name
     * @param name The specified name.
     * @param session Current session containing the logged in user.
     * @return JSON containing either a success or failure message if the playlist was deleted.
     */
    @RequestMapping(value="/deletePlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String deletePlaylist(@RequestParam(value = "name", required = true) String name, HttpSession session)
    {return "";}

    /**
     * Get JSON containing the data of the a playlist with the specified ID.
     * @param playlistId The specified playlist ID.
     * @return JSON containing the information of the playlist
     */
    @RequestMapping(value="/getPlaylistData", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlaylistData(@RequestParam(value = "playlistId", required = true) int playlistId)
    {return "";}

    
    @RequestMapping(value="/getPlaylists", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlaylists(){return "";}

    @RequestMapping(value="/followPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String followPlaylist(@RequestParam(value = "name", required = true) String name,
                                 @RequestParam(value = "ownerId", required = true) int ownerId)
    {return "";}

    @RequestMapping(value="/unfollowPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String unfollowPlaylist(@RequestParam(value = "name", required = true) String name,
                                   @RequestParam(value = "ownerId", required = true) int ownerId)
    {return "";}

    @RequestMapping(value="/addSongToPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String addSongToPlaylist(@RequestParam(value = "songId", required = true) int songId,
                                    @RequestParam(value = "name", required = true) String name)
    {return "";}

    @RequestMapping(value="/removeSongFromPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String removeSongFromPlaylist(@RequestParam(value = "songId", required = true) int songId,
                                         @RequestParam(value = "playlistId", required = true) int playlistId)
    {return "";}

    @RequestMapping(value="/changeTitle", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String changeTitle(@RequestParam(value = "newTitle", required = true) String newTitle,
                              @RequestParam(value = "playlistName", required = true) String playlistName)
    {return "";}

    // Leave out changeImage for now

    @RequestMapping(value="/changePrivacy", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String changePrivacy(@RequestParam(value = "playlistId", required = true) int playlistId,
                                @RequestParam(value = "isPublic", required = true) boolean isPublic)
    {return "";}
}
