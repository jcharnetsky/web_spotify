package web_spotify.spotify;

public interface Viewable {
	/**
	 * Checks whether the current viewable has been registered as a banned
	 * entity
	 *
	 * @return True if the element is banned and False if the element is not
	 * banned
	 */
	public abstract boolean isBanned();
	/**
	 * Checks whether the current viewable is viewable to the world.
	 *
	 * @return True if the viewable is a public resource and false if the
	 * viewable is private
	 */
	public abstract boolean isPublic();
	/**
	 * Gets the id of the User that owns the viewable.
	 *
	 * @return The id of the owner.
	 */
	public abstract int ownedBy();
	/**
	 * Sets the viewable banned attribute
	 *
	 * @param value True if the viewable is now banned and false if the viewable
	 * is unbanned
	 */
	public abstract void setBanned(boolean value);
	/**
	 * Sets the viewable public attribute
	 *
	 * @param value True if the viewable is now public and false if the viewable
	 * is private
	 */
	public abstract void setPublic(boolean value);
	/**
	 * Gets the id of this object
	 *
	 * @return The id of this object
	 */
	public abstract int getId();
}
