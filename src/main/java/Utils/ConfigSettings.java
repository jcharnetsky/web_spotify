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
}
