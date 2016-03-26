package info.szadkowski.matrix.add.game.core.strategy;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class AddComputingGameStrategyTest {
  private ComputingGameStrategy strategy;
  private List<Integer> input;

  @Before
  public void setUp() throws Exception {
    strategy = new AddComputingGameStrategy();
  }

  public class Empty {
    @Before
    public void setUp() throws Exception {
      input = input();
    }

    @Test
    public void shouldNotChangeOnMovement() throws Exception {
      assertMoveLeft();
      assertMoveRight();
      assertMoveUp();
      assertMoveDown();
    }
  }

  public class SingleNumber {
    @Before
    public void setUp() throws Exception {
      input = input(2);
    }

    @Test
    public void shouldNotChangeOnMovement() throws Exception {
      assertMoveLeft(2);
      assertMoveRight(2);
      assertMoveUp(2);
      assertMoveDown(2);
    }
  }

  public class DifferentNumbers {
    @Before
    public void setUp() throws Exception {
      input = input(2, 4, 8);
    }

    @Test
    public void shouldNotChangeOnMovement() throws Exception {
      assertMoveLeft(2, 4, 8);
      assertMoveRight(2, 4, 8);
      assertMoveUp(2, 4, 8);
      assertMoveDown(2, 4, 8);
    }
  }

  public class TheSameNumbers {
    @Before
    public void setUp() throws Exception {
      input = input(2, 2);
    }

    @Test
    public void shouldMergeTwoNumbers() throws Exception {
      assertMoveLeft(4);
      assertMoveRight(4);
      assertMoveUp(4);
      assertMoveDown(4);
    }
  }

  public class CorrectOrderAdding {
    @Before
    public void setUp() throws Exception {
      input = input(2, 2, 2);
    }

    @Test
    public void shouldMergeTwoNumbers() throws Exception {
      assertMoveLeft(4, 2);
      assertMoveRight(2, 4);
      assertMoveUp(4, 2);
      assertMoveDown(2, 4);
    }
  }

  public class Complex {
    @Before
    public void setUp() throws Exception {
      input = input(2, 2, 2, 2, 2, 4, 4, 2, 8, 8);
    }

    @Test
    public void shouldMergeNumbers() throws Exception {
      assertMoveLeft(4, 4, 2, 8, 2, 16);
      assertMoveRight(2, 4, 4, 8, 2, 16);
      assertMoveUp(4, 4, 2, 8, 2, 16);
      assertMoveDown(2, 4, 4, 8, 2, 16);
    }
  }

  private List<Integer> input(Integer... values) {
    return Arrays.asList(values);
  }

  private void assertMoveLeft(Integer... expected) {
    List<Integer> computed = strategy.moveLeft(input);
    assertThat(computed).containsExactly(expected);
  }

  private void assertMoveRight(Integer... expected) {
    List<Integer> computed = strategy.moveRight(input);
    assertThat(computed).containsExactly(expected);
  }

  private void assertMoveUp(Integer... expected) {
    List<Integer> computed = strategy.moveUp(input);
    assertThat(computed).containsExactly(expected);
  }

  private void assertMoveDown(Integer... expected) {
    List<Integer> computed = strategy.moveDown(input);
    assertThat(computed).containsExactly(expected);
  }
}
