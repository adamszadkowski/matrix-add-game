package info.szadkowski.matrix.add.game.core.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddComputingGameStrategy implements ComputingGameStrategy {
  @Override
  public List<Integer> moveLeft(List<Integer> row) {
    return mergeToTheLeft(row);
  }

  @Override
  public List<Integer> moveRight(List<Integer> row) {
    return mergeToTheRight(row);
  }

  @Override
  public List<Integer> moveUp(List<Integer> column) {
    return mergeToTheLeft(column);
  }

  @Override
  public List<Integer> moveDown(List<Integer> column) {
    return mergeToTheRight(column);
  }

  private List<Integer> mergeToTheLeft(List<Integer> input) {
    List<Integer> computed = new ArrayList<>();

    for (int firstInPair = 0; firstInPair < input.size(); firstInPair++) {
      Integer first = input.get(firstInPair);
      int secondInPair = firstInPair + 1;
      if (secondInPair < input.size()) {
        Integer second = input.get(secondInPair);

        if (first.equals(second)) {
          computed.add(first + second);
          firstInPair++;
        } else {
          computed.add(first);
        }
      } else {
        computed.add(first);
      }
    }

    return computed;
  }

  private List<Integer> mergeToTheRight(List<Integer> input) {
    List<Integer> reversed = new ArrayList<>(input);
    Collections.reverse(reversed);
    List<Integer> computed = mergeToTheLeft(reversed);
    Collections.reverse(computed);
    return computed;
  }
}
