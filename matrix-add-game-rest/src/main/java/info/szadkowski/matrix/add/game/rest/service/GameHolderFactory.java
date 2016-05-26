package info.szadkowski.matrix.add.game.rest.service;

import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;

public class GameHolderFactory {
  private final int size;
  private final FullMatrixChangeListener changeListener;

  public GameHolderFactory(FullMatrixChangeListener changeListener, int size) {
    this.size = size;
    this.changeListener = changeListener;
  }

  public GameHolder create() {
    return GameHolder.builder()
            .withChangeListener(changeListener)
            .withSize(size)
            .build();
  }
}
