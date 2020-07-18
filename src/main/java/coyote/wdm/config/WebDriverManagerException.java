package coyote.wdm.config;

public class WebDriverManagerException extends RuntimeException {

  private static final long serialVersionUID = -8613372967839249437L;

  public WebDriverManagerException(String message) {
    super(message);
  }

  public WebDriverManagerException(Throwable cause) {
    super(cause);
  }

  public WebDriverManagerException(String message, Throwable cause) {
    super(message, cause);
  }

}
