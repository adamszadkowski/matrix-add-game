package info.szadkowski.matrix.add.game.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "game")
public class GameProperties {
  private int size;
  private int maxGameCount;
  private Duration expirationTimeout;

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getMaxGameCount() {
    return maxGameCount;
  }

  public void setMaxGameCount(int maxGameCount) {
    this.maxGameCount = maxGameCount;
  }

  public Duration getExpirationTimeout() {
    return expirationTimeout;
  }

  public void setExpirationTimeout(Duration expirationTimeout) {
    this.expirationTimeout = expirationTimeout;
  }
}
