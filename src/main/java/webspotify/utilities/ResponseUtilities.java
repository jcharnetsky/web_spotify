package webspotify.utilities;

/**
 * @author Cardinals
 */
public class ResponseUtilities {
  public static Response emptySuccess() {
    Response r = new Response(false, "", null);
    return r;
  }

  public static Response filledSuccess(Object content) {
    Response r = new Response(false, "", content);
    return r;
  }

  public static Response emptyFailure() {
    Response r = new Response(true, "", null);
    return r;
  }

  public static Response filledFailure(String errorMessage) {
    Response r = new Response(true, errorMessage, null);
    return r;
  }
}
