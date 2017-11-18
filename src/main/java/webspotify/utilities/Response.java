package webspotify.Utilities;

import java.io.Serializable;

/**
 * @author Cardinals
 */
public class Response implements Serializable {
  private String errorMessage;
  private boolean error;
  private Object content;

  public Response(boolean error, String errorMessage, Object content) {
    this.errorMessage = errorMessage;
    this.error = error;
    this.content = content;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public boolean isError() {
    return error;
  }

  public Object getContent() {
    return content;
  }
}
