package info.szadkowski.matrix.add.game.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Game {
  private final int[][] gameMatrix;

  @JsonCreator
  public Game(@JsonProperty("gameMatrix") int[][] gameMatrix) {
    this.gameMatrix = gameMatrix;
  }

  public int[][] getGameMatrix() {
    return gameMatrix;
  }
}
