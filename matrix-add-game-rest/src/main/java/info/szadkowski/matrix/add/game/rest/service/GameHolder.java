package info.szadkowski.matrix.add.game.rest.service;

import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;
import info.szadkowski.matrix.add.game.core.listener.MatrixChangeEvent;
import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.strategy.AddComputingGameStrategy;
import info.szadkowski.matrix.add.game.core.strategy.ComputingGameStrategy;
import info.szadkowski.matrix.add.game.core.strategy.GameDelegatingStrategy;
import info.szadkowski.matrix.add.game.core.strategy.GameStrategy;

public class GameHolder {
  private final GameMatrix gameMatrix;
  private final FullMatrixChangeListener changeListener;
  private final ComputingGameStrategy addStrategy;
  private final GameStrategy strategy;

  public GameHolder(Builder builder) {
    this.gameMatrix = new GameMatrix(builder.size);
    this.changeListener = builder.changeListener;
    this.gameMatrix.addFullMatrixChangeListener(changeListener);
    this.addStrategy = new AddComputingGameStrategy();
    this.strategy = new GameDelegatingStrategy(gameMatrix, addStrategy);
  }

  public int[][] getMatrix() {
    return gameMatrix.getMatrix();
  }

  public void moveLeft() {
    strategy.moveLeft();
  }

  public void moveDown() {
    strategy.moveDown();
  }

  public void moveRight() {
    strategy.moveRight();
  }

  public void moveUp() {
    strategy.moveUp();
  }

  public void generateRandom() {
    changeListener.update(new MatrixChangeEvent(gameMatrix));
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private FullMatrixChangeListener changeListener;
    private int size;

    public Builder withChangeListener(FullMatrixChangeListener changeListener) {
      this.changeListener = changeListener;
      return this;
    }

    public Builder withSize(int size) {
      this.size = size;
      return this;
    }

    public GameHolder build() {
      return new GameHolder(this);
    }
  }
}
