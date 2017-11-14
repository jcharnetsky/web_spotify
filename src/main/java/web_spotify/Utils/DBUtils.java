package web_spotify.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import web_spotify.spotify.Playlist;
import web_spotify.spotify.Report;
import web_spotify.spotify.User;

/**
 *
 * @author cardinals
 */
public class DBUtils {

    private static DBUtils utilSingleton;

    private Connection conn;

    private DBUtils() throws SQLException {
        String dbname = ConfigSettings.DB_NAME;
        String dbhost = ConfigSettings.DB_HOST;
        String dbuser = ConfigSettings.DB_USER;
        String dbpass = ConfigSettings.DB_PASS;
        String format = ConfigSettings.DB_FORM;

        String correctForm = String.format(format, dbhost, dbname, dbuser, dbpass);
        this.conn = DriverManager.getConnection(correctForm);
    }

    public static User getUser(int id) throws SQLException {
        String format = String.format(ConfigSettings.DB_USERS, id);
        ResultSet rs = utilSingleton.conn.createStatement().executeQuery(format);
        User user = null;
        if (rs.next()) {
            String name = rs.getString("name");
            String address = rs.getString("address");
            Date birthdate = rs.getDate("birthday");
            Object obj = rs.getObject("image");
            String email = rs.getString("email");
            user = new User(id, name, email, address, birthdate, null);
            user.getFollowedPlaylists().addAll(DBUtils.getPlaylists(user));
        }
        return user;
    }

    public static List<Playlist> getPlaylists(User user) throws SQLException {
        List<Playlist> playlistsToReturn = new ArrayList<Playlist>();
        Connection conn = utilSingleton.conn;
        ResultSet playlistResults = conn.createStatement().executeQuery(
                String.format(ConfigSettings.DB_PLAYLIST, user.getId())
        );
        while (playlistResults.next()) {
            int creatorId = playlistResults.getInt("creator");
            User creator = creatorId == user.getId() ? user : DBUtils.getUser(creatorId);
            int id = playlistResults.getInt("ID");
            String name = playlistResults.getString("name");
            Date created = playlistResults.getDate("dateCreated");
            String description = playlistResults.getString("description");
            boolean isPublic = playlistResults.getBoolean("isPublic");
            boolean isDeleted = playlistResults.getBoolean("isDeleted");

            Playlist playlist = new Playlist(id, name, creator, null, null, description);
            playlistsToReturn.add(playlist);
        }
        return playlistsToReturn;
    }

    public static User getUser(String email, String password) {
        User userToFind = null;
        try {
            if (utilSingleton == null) {
                utilSingleton = new DBUtils();
            }
            String format = String.format(ConfigSettings.DB_LOGIN, email, password);
            ResultSet rs = utilSingleton.conn.createStatement().executeQuery(format);
            if (rs.next()) {
                int id = rs.getInt("ID");
                userToFind = DBUtils.getUser(id);
            }
        } catch (SQLException e) {
            System.out.println("Error On getUser: " + e.getMessage());
        }
        return userToFind;
    }

    public static boolean putUser(User user, String password) {
        try {
            if (utilSingleton == null) {
                utilSingleton = new DBUtils();
            }
            int id = user.getId();
            String name = user.getName();
            String email = user.getEmail();
            String addr = user.getAddress();
            boolean isPublic = user.isPublic();
            boolean isBanned = user.isBanned();
            String form = String.format(ConfigSettings.DB_USR_IN, 
                    email, password, name, addr, isPublic, isBanned);
            utilSingleton.conn.createStatement().executeUpdate(form);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void testReport() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("spotify_web_persistence");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Report report = new Report();
        report.setDescription("HI");
        report.setSubject("hello");

        entitymanager.persist(report);
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();

    }
}
