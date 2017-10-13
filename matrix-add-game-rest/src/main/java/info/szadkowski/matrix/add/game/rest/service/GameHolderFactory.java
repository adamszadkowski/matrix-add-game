package info.szadkowski.matrix.add.game.rest.service;

import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;
import info.szadkowski.matrix.add.game.rest.properties.GameProperties;

import java.time.Duration;

public class GameHolderFactory {
  private final GameProperties gameProperties;
  private final FullMatrixChangeListener changeListener;

  public GameHolderFactory(GameProperties gameProperties,
                           FullMatrixChangeListener changeListener) {
    this.gameProperties = gameProperties;
    this.changeListener = changeListener;
  }

  public GameHolder create(Duration expirationTime) {
    return GameHolder.builder()
            .withChangeListener(changeListener)
            .withSize(gameProperties.getSize())
            .withExpirationTime(expirationTime)
            .build();
  }
}
