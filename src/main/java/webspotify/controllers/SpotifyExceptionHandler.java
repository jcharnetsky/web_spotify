package webspotify.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SpotifyExceptionHandler {

  /**
   * Handle a thrown SpotifyException by returning a JSON object containing the
   * error message for the client to display.
   * @param e The exception to handle
   * @return A JSON Object containing the error data in the exception
   */
  @ExceptionHandler(value = SpotifyException.class)
  @RequestMapping(produces = "application/json")
  @ResponseBody
  public String spotifyErrorHandler(SpotifyException e) {
    return e.toJSONString();
  }
}
