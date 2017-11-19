package webspotify.interfaces;

public interface Playable {

  public abstract boolean isAvailable();

  public abstract double getTrackLength();

  public abstract void incrementMonthlyListens();

  public abstract void resetMonthlyListens();

  public abstract void incrementTotalListens();
}
