package info.szadkowski.matrix.add.game.core.listener;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;

public interface FullMatrixChangeListener {
  void update(MatrixChangeEvent event);

  class MatrixChangeEvent {
    private final GameMatrix gameMatrix;

    public MatrixChangeEvent(GameMatrix gameMatrix) {
      this.gameMatrix = gameMatrix;
    }

    public GameMatrix getGameMatrix() {
      return gameMatrix;
    }
  }
}
