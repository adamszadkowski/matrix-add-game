package info.szadkowski.matrix.add.game.core.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AddComputingGameStrategyTest {
  private ComputingGameStrategy strategy;
  private List<Integer> input;

  @BeforeEach
  void setUp() throws Exception {
    strategy = new AddComputingGameStrategy();
  }

  @Nested
  class Empty {

    @BeforeEach
    void setUp() throws Exception {
      input = input();
    }

    @Test
    void shouldNotChangeOnMovement() throws Exception {
      assertMoveLeft();
      assertMoveRight();
      assertMoveUp();
      assertMoveDown();
    }
  }

  @Nested
  class SingleNumber {

    @BeforeEach
    void setUp() throws Exception {
      input = input(2);
    }

    @Test
    void shouldNotChangeOnMovement() throws Exception {
      assertMoveLeft(2);
      assertMoveRight(2);
      assertMoveUp(2);
      assertMoveDown(2);
    }
  }

  @Nested
  class DifferentNumbers {

    @BeforeEach
    void setUp() throws Exception {
      input = input(2, 4, 8);
    }

    @Test
    void shouldNotChangeOnMovement() throws Exception {
      assertMoveLeft(2, 4, 8);
      assertMoveRight(2, 4, 8);
      assertMoveUp(2, 4, 8);
      assertMoveDown(2, 4, 8);
    }
  }

  @Nested
  class TheSameNumbers {

    @BeforeEach
    void setUp() throws Exception {
      input = input(2, 2);
    }

    @Test
    void shouldMergeTwoNumbers() throws Exception {
      assertMoveLeft(4);
      assertMoveRight(4);
      assertMoveUp(4);
      assertMoveDown(4);
    }
  }

  @Nested
  class CorrectOrderAdding {

    @BeforeEach
    void setUp() throws Exception {
      input = input(2, 2, 2);
    }

    @Test
    void shouldMergeTwoNumbers() throws Exception {
      assertMoveLeft(4, 2);
      assertMoveRight(2, 4);
      assertMoveUp(4, 2);
      assertMoveDown(2, 4);
    }
  }

  @Nested
  class Complex {

    @BeforeEach
    void setUp() throws Exception {
      input = input(2, 2, 2, 2, 2, 4, 4, 2, 8, 8);
    }

    @Test
    void shouldMergeNumbers() throws Exception {
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
