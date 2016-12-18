package info.szadkowski.matrix.add.game.core.listener;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.matrix.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomNumberAppender implements NumberAppender {
  @Override
  public void update(MatrixChangeEvent event) {
    if (event == null)
      throw new IllegalArgumentException();

    if (event.getGameMatrix().getSize() == 0)
      throw new MatrixFullException();

    List<Point> emptyCells = findEmptyCells(event.getGameMatrix());

    if (emptyCells.isEmpty())
      throw new MatrixFullException();
    else {
      Collections.shuffle(emptyCells);
      Point p = emptyCells.get(0);
      event.getGameMatrix().set(p.getX(), p.getY(), 2);
    }
  }

  private List<Point> findEmptyCells(GameMatrix matrix) {
    List<Point> cells = new ArrayList<>();

    for (int x = 0; x < matrix.getSize(); x++)
      for (int y = 0; y < matrix.getSize(); y++)
        if (matrix.get(x, y) == 0)
          cells.add(Point.of(x, y));

    return cells;
  }
}
