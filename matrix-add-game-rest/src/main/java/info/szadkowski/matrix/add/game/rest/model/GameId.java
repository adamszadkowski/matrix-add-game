package info.szadkowski.matrix.add.game.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameId {
  private final String id;

  @JsonCreator
  public GameId(@JsonProperty("id") String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
