package Utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import web_spotify.spotify.User;

/**
 *
 * @author cardinals
 */
public class DBUtils {
    
    private static DBUtils utilSingleton;
    
    private Connection conn;
    
    private DBUtils () throws SQLException {
        String dbname = ConfigSettings.DB_NAME;
        String dbhost = ConfigSettings.DB_HOST;
        String dbuser = ConfigSettings.DB_USER;
        String dbpass = ConfigSettings.DB_PASS;
        String format = ConfigSettings.DB_FORM;
        
        String correctForm = String.format(format, dbhost, dbname, dbuser, dbpass);
        this.conn = DriverManager.getConnection(correctForm);
    }
        
    public static User getUser (String email, String password) {
        User userToFind = null;
        try{
            if(utilSingleton == null) {
                utilSingleton = new DBUtils();
            }
            String format = String.format(ConfigSettings.DB_LOGIN,email,password);
            ResultSet rs = utilSingleton.conn.createStatement().executeQuery(format);
            rs.next();
            int id = rs.getInt("ID");
            String name    = rs.getString("name");
            String address = rs.getString("address");
            Date birthdate = rs.getDate("birthday");
            Object obj     = rs.getObject("image");
            userToFind = new User(id, name, email, address, birthdate, new BufferedImage(1,1,BufferedImage.TYPE_3BYTE_BGR));
        } catch (SQLException e) {
            System.out.println("Error On getUser: " + e.getMessage());
        }
        return userToFind;
    }
    
}
