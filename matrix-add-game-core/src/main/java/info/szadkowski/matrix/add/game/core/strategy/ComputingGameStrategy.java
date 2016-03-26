package info.szadkowski.matrix.add.game.core.strategy;

import java.util.List;

public interface ComputingGameStrategy {
  List<Integer> moveLeft(List<Integer> row);
  List<Integer> moveRight(List<Integer> row);
  List<Integer> moveUp(List<Integer> column);
  List<Integer> moveDown(List<Integer> column);
}
