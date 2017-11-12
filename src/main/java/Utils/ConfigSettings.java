package Utils;

/**
 *
 * @author Cardinals
 */
class ConfigSettings {
    public static final String DB_NAME = "cardinals";
    public static final String DB_HOST = "mysql2.cs.stonybrook.edu";
    public static final String DB_USER = "cardinals";
    public static final String DB_PASS = "cardinals4life";
    public static final String DB_FORM = "jdbc:mysql://%s/%s?user=%s&password=%s";
    
    public static final String DB_LOGIN= "SELECT * FROM Users WHERE email='%s' AND password='%s';";
    public static final String DB_PLAYLIST = "select p.* from Playlists p, PlaylistFollowers f WHERE f.userId = %d AND p.ID=f.playlistID AND NOT(p.isDeleted) AND (p.isPublic OR (p.creator=f.userId));";
    public static final String DB_USERS="SELECT * FROM Users WHERE ID=%d;";
    public static final String DB_USR_IN= "INSERT INTO  Users(accStatus,email,password,salt,name,address,isPublic,isBanned,birthday,image) VALUES ('BASE', '%s', '%s', '', '%s', '%s', %s, %s, NOW(), '');";
}
