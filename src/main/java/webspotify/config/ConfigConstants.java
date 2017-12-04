package webspotify.config;

/**
 *
 * @author Cardinals
 */
public class ConfigConstants {

  public static String QUEUE_OUT_OF_BOUNDS = "No more songs in this direction.";
  public static String QUEUE_SESSION = "Queue";
  public static String NOT_IN_QUEUE = "The song is not in the queue.";

  public static String CONCERT_NO_EXIST = "Concert does not exist.";
  public static String USER_NOT_FOUND = "The user does not exist.";
  public static String COLLECTION_NO_EXIST = "Desired Collection does not exist.";
  public static String SONG_NO_EXIST = "Desired song does not exist.";
  public static String ARTIST_NO_EXIST = "Artist does not exist.";
  public static String REPORT_NO_EXIST = "Report does not exist.";
  public static String REPORT_TYPE_NO_EXIST = "The given Report type does not exist.";
  public static String ENTITY_TYPE_NO_EXIST = "There is no entities of the given type.";
  public static String ENTITY_NO_EXIST = "The given entity does not exist.";

  public static String ACCESS_DENIED = "User does not have access to this content.";
  public static String NOT_AN_ARTIST = "Only artists can access this.";
  public static String ADMIN_NO_DELETE = "Administrators cannot be deleted.";

  public static String USER_ALREADY_LOGGED = "User is already logged in.";
  public static String USER_NOT_LOGGED = "User is not logged in.";
  public static String USER_DELETED = "This user account was deleted.";
  public static String USER_BANNED = "This user account was banned.";

  public static String COULD_NOT_ADD = "Content could not be added.";
  public static String COULD_NOT_EDIT = "Content could not be edited";
  public static String COULD_NOT_REM = "Content could not be removed.";
  public static String COULD_NOT_CREATE = "Content could not be created.";
  public static String COULD_NOT_CHANGE = "Content could not be changed.";

  public static String CANNOT_UNBAN_SONG = "Cannot unban a song who's album is banned.";
  public static String CANNOT_ADD_SONG = "Cannot add a song who's album is banned.";
  public static String ORIGINALLY_BANNED = "This entity was previously banned." +
                                           " The originaly reason for banning is as follows: \n";

  public static String EMAIL_EXIST = "Desired Email already exists.";
  public static String PASSWORDS_NO_MATCH = "The passwords did not match.";
  public static String EMAIL_NO_EXIST = "An account with that Email does not exist.";
  public static String INVALID_CREDENTIALS = "The given Email/password combination is invalid.";
  public static String INVALID_PASSWORD = "Old password was incorrect.";
  public static String INVALID_EMAIL = "Email is invalid.";
  public static String UNKNOWN_TYPE = "The object type is either invalid or unknown.";

  public static String INVALID_CC = "Invalid credit card.";

  public static String USER_SESSION = "User";
  public static int MAX_SEARCH_ELEMENT_COUNT = 4;
  public static String AUDIO_TYPE = "audio/mpeg3";

  public static int NUM_ALBUMS_TO_SHOW_BROWSE = 10;
  public static int NUM_PLAYLISTS_TO_SHOW_BROWSE = 10;
  public static int NUM_ARTISTS_TO_SHOW_BROWSE = 10;
  
  public static double MONTHLY_SUBSCRIPTION_COST = 10.00;
  public static double PCT_GIVEN_TO_ARTISTS = 0.4;
  public static double PCT_GIVEN_TO_PROFITS = 1.0 - PCT_GIVEN_TO_ARTISTS;
}
