package web_spotify.spotify;

import java.util.Date;

/**
 * Represents the base user functionality for the spotify project
 * @author Cardinals
 */
public class User {
    /**
     * The unique identifier of the user. CANNOT be changed.
     */
    private final int id;
    
    /**
     * The name of the user
     */
    private String name;
    
    /**
     * The email address associated with this user
     */
    private String email;
    
    /**
     * The physical address associated with the user
     */
    private String address;
    
    /**
     * The date of birth of the user
     */
    private Date birthday;
    
    /**
     * The Constructor for the User Object
     * @param id The unique identifier for a particular user
     * @param name THe name of the User
     * @param email The current email of the user
     * @param address The physical address of the user
     * @param birthday The birthday of the user
     */
    public User (int id, String name, String email, String address, Date birthday) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.address  = address;
        this.birthday = birthday;
    }
}
