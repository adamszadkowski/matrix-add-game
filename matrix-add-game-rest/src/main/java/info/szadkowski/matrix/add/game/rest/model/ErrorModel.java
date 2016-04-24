package info.szadkowski.matrix.add.game.rest.model;

public class ErrorModel {
  private final String reason;
  private final String message;
  private final String url;

  public ErrorModel(String reason, String message, String url) {
    this.reason = reason;
    this.message = message;
    this.url = url;
  }

  public String getReason() {
    return reason;
  }

  public String getMessage() {
    return message;
  }

  public String getUrl() {
    return url;
  }
}
