package info.szadkowski.matrix.add.game.core.visualizer;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;

public class GameMatrixVisualizer {
  private final GameMatrix gameMatrix;
  private int maxFieldSize;

  public GameMatrixVisualizer(GameMatrix gameMatrix) {
    this.gameMatrix = gameMatrix;
  }

  public String visualize() {
    setValuesMaxLength();

    String out = "";
    for (int y = 0; y < gameMatrix.getSize(); y++)
      out += createLine(y) + newLine(y);

    return out;
  }

  private void setValuesMaxLength() {
    int matrixSize = gameMatrix.getSize();

    int maxSize = 0;
    for (int x = 0; x < matrixSize; x++) {
      for (int y = 0; y < matrixSize; y++) {
        int n = gameMatrix.get(x, y);
        if (!isEmpty(n))
          maxSize = Integer.max(getIntLength(n), maxSize);
      }
    }

    maxFieldSize = maxSize;
  }

  private static boolean isEmpty(int n) {
    return n == 0;
  }

  private int getIntLength(int n) {
    return String.valueOf(n).length();
  }

  private String createLine(int y) {
    int matrixSize = gameMatrix.getSize();

    String line = "";
    for (int x = 0; x < matrixSize; x++) {
      int n = gameMatrix.get(x, y);
      line += String.format("| %s ", isEmpty(n) ? formattedSpaces() : formattedValue(n));
    }

    return String.format("%s|", line);
  }

  private String formattedValue(int n) {
    // for maxFieldSize=2: %2d
    return String.format(String.format("%%%dd", maxFieldSize), n);
  }

  private String newLine(int y) {
    return y < gameMatrix.getSize() - 1 ? "\n" : "";
  }

  private String formattedSpaces() {
    String out = "";
    for (int i = 0; i < maxFieldSize; i++)
      out += " ";

    return out;
  }
}
