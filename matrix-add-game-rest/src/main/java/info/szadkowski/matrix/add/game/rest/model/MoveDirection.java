package info.szadkowski.matrix.add.game.rest.model;

import info.szadkowski.matrix.add.game.rest.service.GameHolder;

import java.util.function.Consumer;

public enum MoveDirection {
  RIGHT(GameHolder::moveRight),
  DOWN(GameHolder::moveDown),
  LEFT(GameHolder::moveLeft),
  UP(GameHolder::moveUp),
  NONE(g -> {
  });

  private final Consumer<GameHolder> move;

  MoveDirection(Consumer<GameHolder> move) {
    this.move = move;
  }

  public void move(GameHolder gameHolder) {
    this.move.accept(gameHolder);
  }
}
