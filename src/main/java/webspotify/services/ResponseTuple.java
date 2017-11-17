package webspotify.services;
import webspotify.Utilities.Response;
import webspotify.models.media.SongQueue;
import webspotify.models.users.User;

public class ResponseTuple {
  Response response;
  User user;
  SongQueue songQueue;
  
  public ResponseTuple() {
  }
  
  public Response getResponse() {
    return response;
  }
  
  public void setResponse(Response response) {
    this.response = response;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public SongQueue getSongQueue() {
    return songQueue;
  }
  
  public void setSongQueue(SongQueue songQueue) {
    this.songQueue = songQueue;
  }
}
