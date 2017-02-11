package info.szadkowski.matrix.add.game.rest.service;

import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;
import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.strategy.AddComputingGameStrategy;
import info.szadkowski.matrix.add.game.core.strategy.ComputingGameStrategy;
import info.szadkowski.matrix.add.game.core.strategy.GameDelegatingStrategy;
import info.szadkowski.matrix.add.game.core.strategy.GameStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class GameHolder {
  private final GameMatrix gameMatrix;
  private final FullMatrixChangeListener changeListener;
  private final ComputingGameStrategy addStrategy;
  private final GameStrategy strategy;
  private final LocalDateTime creationTime;
  private final Duration expirationTime;

  public GameHolder(Builder builder) {
    this.gameMatrix = new GameMatrix(builder.size);
    this.changeListener = builder.changeListener;
    this.gameMatrix.addFullMatrixChangeListener(changeListener);
    this.addStrategy = new AddComputingGameStrategy();
    this.strategy = new GameDelegatingStrategy(gameMatrix, addStrategy);
    this.creationTime = getCurrentTimeInUTC();
    this.expirationTime = builder.expirationTime;
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
    changeListener.update(new FullMatrixChangeListener.MatrixChangeEvent(gameMatrix));
  }

  public boolean hasExpired() {
    return creationTime
            .plus(expirationTime)
            .isBefore(getCurrentTimeInUTC());
  }

  private LocalDateTime getCurrentTimeInUTC() {
    return LocalDateTime.now(ZoneId.of("UTC"));
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private FullMatrixChangeListener changeListener;
    private Duration expirationTime;
    private int size;

    public Builder withChangeListener(FullMatrixChangeListener changeListener) {
      this.changeListener = changeListener;
      return this;
    }

    public Builder withSize(int size) {
      this.size = size;
      return this;
    }

    public Builder withExpirationTime(Duration expirationTime) {
      this.expirationTime = expirationTime;
      return this;
    }

    public GameHolder build() {
      return new GameHolder(this);
    }
  }
}
