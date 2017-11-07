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

    /**
     * Get JSON containing the name and ID of all the playlilsts that the logged in user owns
     * or follows.
     * @param session Current session containing the logged in user.
     * @return JSON containing the information of the playlists
     */
    @RequestMapping(value="/getPlaylists", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlaylists(HttpSession session){return "";}

    /**
     * Follow a playlist with a specified name owned by a user with the a specified ID.
     * @param name The name of the playlist to follow
     * @param ownerId The user ID of the owner of the playlist to follow
     * @param session Current session containing the logged in user.
     * @return JSON containing the new list of followed and owned playlists of the logged in user, or
     * an error message if the playlist could not be followed.
     */
    @RequestMapping(value="/followPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String followPlaylist(@RequestParam(value = "name", required = true) String name,
                                 @RequestParam(value = "ownerId", required = true) int ownerId,
                                 HttpSession session)
    {return "";}

    /**
     * Unfollow a playlist with a specified name owned by a user with the a specified ID.
     * @param name The name of the playlist to unfollow
     * @param ownerId The user ID of the owner of the playlist to unfollow
     * @param session Current session containing the logged in user.
     * @return JSON containing the new list of followed and owned playlists of the logged in user, or
     * an error message if the playlist could not be unfollowed.
     */
    @RequestMapping(value="/unfollowPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String unfollowPlaylist(@RequestParam(value = "name", required = true) String name,
                                   @RequestParam(value = "ownerId", required = true) int ownerId,
                                   HttpSession session)
    {return "";}

    /**
     * Add a song with the specified song ID to the playlist with the specified name.
     * @param songId The specified song ID
     * @param name The specified playlist name
     * @param session Current session containing the logged in user.
     * @return JSON containing the the new data of the playlist that was added to, or an error message
     * if the song could not be added.
     */
    @RequestMapping(value="/addSongToPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String addSongToPlaylist(@RequestParam(value = "songId", required = true) int songId,
                                    @RequestParam(value = "name", required = true) String name,
                                    HttpSession session)
    {return "";}

    /**
     * Remove a song with the specified song ID to the playlist with the specified name.
     * @param songId The specified song ID
     * @param playlistId The specified playlist ID
     * @param session Current session containing the logged in user.
     * @return JSON containing the the new data of the playlist that had a song removed, or an error message
     * if the song could not be added.
     */
    @RequestMapping(value="/removeSongFromPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String removeSongFromPlaylist(@RequestParam(value = "songId", required = true) int songId,
                                         @RequestParam(value = "playlistId", required = true) int playlistId,
                                         HttpSession session)
    {return "";}

    /**
     * Change the title of a playlist with a specified name.
     * @param newTitle The new title for the playlist
     * @param playlistName The name of the playlist to change
     * @param session Current session containing the logged in user.
     * @return JSON containing the the new data of the playlist that had its title changed, or an error message
     * if the song could not be added.
     */
    @RequestMapping(value="/changeTitle", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String changeTitle(@RequestParam(value = "newTitle", required = true) String newTitle,
                              @RequestParam(value = "playlistName", required = true) String playlistName,
                              HttpSession session)
    {return "";}

    // Leave out changeImage for now

    /**
     * Change the privacy setting of an owned playlist to either public or private.
     * @param playlistId The specified playlist ID
     * @param isPublic True to set the playlist as public, False to set it as private
     * @param session Current session containing the logged in user.
     * @return JSON containing the the new data of the playlist that had its privacy changed, or an error message
     * if the song could not be added.
     */
    @RequestMapping(value="/changePrivacy", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String changePrivacy(@RequestParam(value = "playlistId", required = true) int playlistId,
                                @RequestParam(value = "isPublic", required = true) boolean isPublic,
                                HttpSession session)
    {return "";}
}
