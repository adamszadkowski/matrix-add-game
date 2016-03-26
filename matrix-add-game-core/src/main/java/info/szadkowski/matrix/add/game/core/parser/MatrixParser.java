package info.szadkowski.matrix.add.game.core.parser;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;

public class MatrixParser {
  private static final int NUMBER_OF_FIELDS_TO_SKIP = 2;

  private final GameMatrix gameMatrix;

  public MatrixParser(GameMatrix gameMatrix) {
    this.gameMatrix = gameMatrix;
  }

  public void parse(String matrix) {
    validateTrueOrThrow(matrix != null && !matrix.isEmpty());

    String[] lines = matrix.split("\n");
    GameMatrix.MatrixTransaction transaction = gameMatrix.createTransaction();
    for (int y = 0; y < lines.length; y++)
      parseLine(y, lines[y], transaction);

    transaction.finalizeTransaction();
  }

  private void parseLine(int y, String line, GameMatrix.MatrixTransaction transaction) {
    String[] fieldsInLine = line.trim().split("\\|", -1);
    int numberCount = fieldsInLine.length - NUMBER_OF_FIELDS_TO_SKIP;

    validateTrueOrThrow(numberCount == gameMatrix.getSize());

    for (int i = 1; i <= numberCount; i++) {
      String n = fieldsInLine[i].trim();
      if (!n.isEmpty()) {
        int x = i - 1;
        transaction.set(x, y, Integer.parseInt(n));
      }
    }
  }

  private static void validateTrueOrThrow(boolean predicate) {
    if (!predicate)
      throw new CorruptedSyntaxException();
  }

  public static class CorruptedSyntaxException extends RuntimeException {
  }
}
