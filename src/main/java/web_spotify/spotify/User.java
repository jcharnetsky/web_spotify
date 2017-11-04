package web_spotify.spotify;

import java.util.Date;

/**
 * Represents the base user functionality for the spotify project
 *
 * @author Cardinals
 */
public class User implements Viewable {

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
     * A boolean which determines if the user is public. DEFAULT FALSE.
     */
    private boolean isPublic;

    /**
     * A boolean which determines if the user is banned. DEFAULT FALSE.
     */
    private boolean isBanned;

    /**
     * The Constructor for the User Object
     *
     * @param id The unique identifier for a particular user
     * @param name THe name of the User
     * @param email The current email of the user
     * @param address The physical address of the user
     * @param birthday The birthday of the user
     */
    public User(int id, String name, String email, String address, Date birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }

    /* OVERRIDES FOR VIEWABLE BELOW */
    @Override
    public boolean isBanned() {
        return this.isBanned;
    }

    @Override
    public boolean isPublic() {
        return this.isPublic;
    }

    @Override
    public int ownedBy() {
        return -1;
    }

    @Override
    public boolean setBanned(boolean value) {
        this.isBanned = value;
        return true;
    }

    @Override
    public boolean setPublic(boolean value) {
        this.isPublic = value;
        return true;
    }

    /* GETTERS BELOW */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthday() {
        return birthday;
    }
    
    /**
     * Compare User objects to determine equivalence
     *
     * @param object to compare
     * @return True if object is an instance of User and has the same id; False otherwise
     */
    @Override
    public boolean equals(Object u) {
        if((u != null) && (u instanceof User)) {
            return ((User) u).getId() == this.getId();
        } else return false;
    }
}
