package Utils;

import org.json.JSONObject;

/**
 * A utility for json
 *
 * @author Cardinals
 */
public class JsonUtils {

    /**
     * Creates a blank error response with the error flag set and the error
     * message
     *
     * @param errorMessage The error message
     * @return A string representation of the json
     */
    static public String createBlankError(String errorMessage) {
        return JsonUtils.createJson(true, errorMessage, "");
    }

    /**
     * Creates a blank success response with the error flag and nothing else
     *
     * @return A string representation of the json
     */
    static public String createBlankSuccess() {
        return JsonUtils.createJson(false, "", "");
    }

    /**
     * Creates a default json object with the given parameters
     *
     * @param error A boolean representing an error occurred
     * @param errorMessage A String representing the description of the error
     * @param content A string representing the content of the body
     * @return The json representation
     */
    static private String createJson(boolean error, String errorMessage, String content) {
        JSONObject obj = new JSONObject()
                .accumulate("error", error)
                .accumulate("errorMessage", errorMessage)
                .accumulate("content", content);
        return obj.toString();
    }

}
