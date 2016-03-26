package info.szadkowski.matrix.add.game.core.strategy;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class GameDelegatingStrategy implements GameStrategy {
  private final GameMatrix gameMatrix;
  private final ComputingGameStrategy computingGameStrategy;

  public GameDelegatingStrategy(GameMatrix gameMatrix,
                                ComputingGameStrategy computingGameStrategy) {
    this.gameMatrix = gameMatrix;
    this.computingGameStrategy = computingGameStrategy;
  }

  @Override
  public void moveLeft() {
    processRows(row -> {
      List<Integer> computed = computingGameStrategy.moveLeft(row);
      List<Integer> completeRow = new ArrayList<>();
      completeRow.addAll(computed);
      completeRow.addAll(createComplementTo(computed.size()));
      return completeRow;
    });
  }

  @Override
  public void moveRight() {
    processRows(row -> {
      List<Integer> computed = computingGameStrategy.moveRight(row);
      List<Integer> completeRow = new ArrayList<>();
      completeRow.addAll(createComplementTo(computed.size()));
      completeRow.addAll(computed);
      return completeRow;
    });
  }

  @Override
  public void moveUp() {
    processColumns(column -> {
      List<Integer> computed = computingGameStrategy.moveUp(column);
      List<Integer> completeColumn = new ArrayList<>();
      completeColumn.addAll(computed);
      completeColumn.addAll(createComplementTo(computed.size()));
      return completeColumn;
    });
  }

  @Override
  public void moveDown() {
    processColumns(column -> {
      List<Integer> computed = computingGameStrategy.moveDown(column);
      List<Integer> completeColumn = new ArrayList<>();
      completeColumn.addAll(createComplementTo(computed.size()));
      completeColumn.addAll(computed);
      return completeColumn;
    });
  }

  private void processRows(Function<List<Integer>, List<Integer>> function) {
    GameMatrix.MatrixTransaction transaction = gameMatrix.createTransaction();

    for (int y = 0; y < gameMatrix.getSize(); y++) {
      List<Integer> row = function.apply(getNumbersForRow(y));

      for (int x = 0; x < row.size(); x++)
        transaction.set(x, y, row.get(x));
    }

    transaction.finalizeTransaction();
  }

  private List<Integer> getNumbersForRow(int y) {
    return IntStream.range(0, gameMatrix.getSize())
            .map(x -> gameMatrix.get(x, y))
            .filter(n -> n != 0)
            .boxed()
            .collect(toList());
  }

  private void processColumns(Function<List<Integer>, List<Integer>> function) {
    GameMatrix.MatrixTransaction transaction = gameMatrix.createTransaction();

    for (int x = 0; x < gameMatrix.getSize(); x++) {
      List<Integer> column = function.apply(getNumbersForColumn(x));

      for (int y = 0; y < column.size(); y++)
        transaction.set(x, y, column.get(y));
    }

    transaction.finalizeTransaction();
  }

  private List<Integer> getNumbersForColumn(int x) {
    return IntStream.range(0, gameMatrix.getSize())
            .map(y -> gameMatrix.get(x, y))
            .filter(n -> n != 0)
            .boxed()
            .collect(toList());
  }

  private List<Integer> createComplementTo(int size) {
    return IntStream.range(0, gameMatrix.getSize() - size)
            .map(i -> 0)
            .boxed()
            .collect(toList());
  }
}
