package webspotify.interfaces;

public interface Viewable {
	public abstract boolean isBanned();
	public abstract boolean isPublic();
	public abstract int ownedBy();
	public abstract void setBanned(boolean value);
	public abstract void setPublic(boolean value);
	public abstract Integer getId();
}
