package web_spotify.controllers;

import org.json.JSONObject;

public class SpotifyException extends Exception {

    /**
     * Convert the error message of this exception to a string in JSON.
     * @return The JSON string of the error message
     */
    public String toJSONString(){

        JSONObject errorJSON = new JSONObject();
        errorJSON.put("error", this.toString());
        return errorJSON.toString();

    }
}
