package web_spotify.controllers;

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
public class PlaylistController {

    /**
     * Create a playlist for the logged in user with the specified name
     * @param name The specified name.
     * @param session Current session containing the logged in user.
     * @throws SpotifyException If a playlist with that name already exists
     * @return JSON containing the information of the playlist just created.
     */
    @RequestMapping(value="/createPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String createPlaylist(@RequestParam(value = "name", required = true) String name, HttpSession session)
    throws SpotifyException
    {return "";}

    /**
     * Delete for the logged in user with the specified name
     * @param name The specified name.
     * @param session Current session containing the logged in user.
     * @throws SpotifyException If the playlist failed to be deleted
     * @return JSON containing a success message that the playlist was deleted.
     */
    @RequestMapping(value="/deletePlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String deletePlaylist(@RequestParam(value = "name", required = true) String name, HttpSession session)
    throws SpotifyException
    {return "";}

    /**
     * Get JSON containing the data of the a playlist with the specified ID.
     * @param playlistId The specified playlist ID.
     * @throws SpotifyException If the playlist information could not be retrieved.
     * @return JSON containing the information of the playlist
     */
    @RequestMapping(value="/getPlaylistData", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlaylistData(@RequestParam(value = "playlistId", required = true) int playlistId)
    throws SpotifyException
    {
        JSONObject temp = new JSONObject();
        temp.put("temporary data", "temporary data");
        return temp.toString();
    }

    /**
     * Get JSON containing the name and ID of all the playlists that the logged in user owns
     * or follows.
     * @param session Current session containing the logged in user.
     * @throws SpotifyException If the playlist information could not be retrieved
     * @return JSON containing the information of the playlists
     */
    @RequestMapping(value="/getPlaylists", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlaylists(HttpSession session) throws SpotifyException
    {
        // Throw an error if a user isn't logged in
        User user = (User) session.getAttribute("User");
        if (user == null) throw new SpotifyException("Cannot view playlists if a user not logged in.");

        // Grab the owned and followed playlists from the user
        JSONArray playlists = new JSONArray();
        Iterator<Playlist> ownedPlaylists = user.getOwnedPlaylists().iterator();
        Iterator<Playlist> followedPlaylists = user.getFollowedPlaylists().iterator();

        // Populate JSON with the playlists from the user
        while(ownedPlaylists.hasNext()){
            JSONObject playlist = new JSONObject();
            Playlist temp = ownedPlaylists.next();
            playlist.put("title", temp.getTitle());
            playlist.put("id", temp.getId());
            playlists.put(playlist);
        }
        while(followedPlaylists.hasNext()){
            JSONObject playlist = new JSONObject();
            Playlist temp = followedPlaylists.next();
            playlist.put("title", temp.getTitle());
            playlist.put("id", temp.getId());
            playlists.put(playlist);
        }

        // Return the JSON
        JSONObject toReturn = new JSONObject();
        toReturn.put("playlists", playlists);
        return toReturn.toString();
    }

    /**
     * Follow a playlist with a specified name owned by a user with the a specified ID.
     * @param name The name of the playlist to follow
     * @param ownerId The user ID of the owner of the playlist to follow
     * @param session Current session containing the logged in user.
     * @throws Error If the playlist could not be followed
     * @return JSON containing the new list of followed and owned playlists of the logged in user
     */
    @RequestMapping(value="/followPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String followPlaylist(@RequestParam(value = "name", required = true) String name,
                                 @RequestParam(value = "ownerId", required = true) int ownerId,
                                 HttpSession session) throws SpotifyException
    {return "";}

    /**
     * Unfollow a playlist with a specified name owned by a user with the a specified ID.
     * @param name The name of the playlist to unfollow
     * @param ownerId The user ID of the owner of the playlist to unfollow.
     * @param session Current session containing the logged in user.
     * @throws Error If the playlist could not be unfollowed.
     * @return JSON containing the new list of followed and owned playlists of the logged in user
     */
    @RequestMapping(value="/unfollowPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String unfollowPlaylist(@RequestParam(value = "name", required = true) String name,
                                   @RequestParam(value = "ownerId", required = true) int ownerId,
                                   HttpSession session) throws SpotifyException
    {return "";}

    /**
     * Add a song with the specified song ID to the playlist with the specified name.
     * @param songId The specified song ID
     * @param name The specified playlist name
     * @param session Current session containing the logged in user.
     * @throws SpotifyException If the song could not be added to the playlist
     * @return JSON containing the the new data of the playlist that was added to
     */
    @RequestMapping(value="/addSongToPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String addSongToPlaylist(@RequestParam(value = "songId", required = true) int songId,
                                    @RequestParam(value = "name", required = true) String name,
                                    HttpSession session) throws SpotifyException
    {return "";}

    /**
     * Remove a song with the specified song ID to the playlist with the specified name.
     * @param songId The specified song ID
     * @param playlistId The specified playlist ID
     * @param session Current session containing the logged in user.
     * @throws SpotifyException If the song could not be removed from the playlist
     * @return JSON containing the the new data of the playlist that had a song removed
     */
    @RequestMapping(value="/removeSongFromPlaylist", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String removeSongFromPlaylist(@RequestParam(value = "songId", required = true) int songId,
                                         @RequestParam(value = "playlistId", required = true) int playlistId,
                                         HttpSession session) throws SpotifyException
    {return "";}

    /**
     * Change the title of a playlist with a specified name.
     * @param newTitle The new title for the playlist
     * @param playlistName The name of the playlist to change
     * @param session Current session containing the logged in user.
     * @throws SpotifyException If the playlist title could not be changed.
     * @return JSON containing the the new data of the playlist that had its title changed
     */
    @RequestMapping(value="/changeTitle", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String changeTitle(@RequestParam(value = "newTitle", required = true) String newTitle,
                              @RequestParam(value = "playlistName", required = true) String playlistName,
                              HttpSession session) throws SpotifyException
    {return "";}

    // Leave out changeImage for now

    /**
     * Change the privacy setting of an owned playlist to either public or private.
     * @param playlistId The specified playlist ID
     * @param isPublic True to set the playlist as public, False to set it as private
     * @param session Current session containing the logged in user.
     * @throws SpotifyException If the playlist privacy could not be changed
     * @return JSON containing the the new data of the playlist that had its privacy changed
     */
    @RequestMapping(value="/changePrivacy", method= RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String changePrivacy(@RequestParam(value = "playlistId", required = true) int playlistId,
                                @RequestParam(value = "isPublic", required = true) boolean isPublic,
                                HttpSession session) throws SpotifyException
    {return "";}
}
