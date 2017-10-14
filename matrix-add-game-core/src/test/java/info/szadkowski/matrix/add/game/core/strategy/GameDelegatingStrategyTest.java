package info.szadkowski.matrix.add.game.core.strategy;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.parser.MatrixParser;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameDelegatingStrategyTest {
  private GameMatrix matrix;
  private MatrixParser parser;
  private GameMatrixVisualizer visualizer;
  private GameStrategy strategy;

  @Nested
  class BlockedMove {

    @BeforeEach
    void setUp() throws Exception {
      init(2);
    }

    @Test
    void shouldNotMoveLeft() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "|   |   |");

      strategy.moveLeft();

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "|   |   |");
    }

    @Test
    void shouldNotMoveRight() throws Exception {
      parser.parse("|   | 2 |\n" +
                   "|   |   |");

      strategy.moveRight();

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   |   |");
    }

    @Test
    void shouldNotMoveUp() throws Exception {
      parser.parse("|   | 2 |\n" +
                   "|   |   |");

      strategy.moveUp();

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   |   |");
    }

    @Test
    void shouldNotMoveDown() throws Exception {
      parser.parse("|   |   |\n" +
                   "|   | 2 |");

      strategy.moveDown();

      assertThat(visualizer.visualize()).isEqualTo("|   |   |\n" +
                                                   "|   | 2 |");
    }
  }

  @Nested
  class SingleNumberMovement {

    @BeforeEach
    void setUp() throws Exception {
      init(2);
    }

    @Test
    void shouldMoveLeft() throws Exception {
      parser.parse("|   | 2 |\n" +
                   "|   |   |");

      strategy.moveLeft();

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "|   |   |");
    }

    @Test
    void shouldMoveRight() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "|   |   |");

      strategy.moveRight();

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   |   |");
    }

    @Test
    void shouldMoveUp() throws Exception {
      parser.parse("|   |   |\n" +
                   "| 2 |   |");

      strategy.moveUp();

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "|   |   |");
    }

    @Test
    void shouldMoveDown() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "|   |   |");

      strategy.moveDown();

      assertThat(visualizer.visualize()).isEqualTo("|   |   |\n" +
                                                   "| 2 |   |");
    }
  }

  @Nested
  class MultiNumberMovement {

    @BeforeEach
    void setUp() throws Exception {
      init(2);
    }

    @Test
    void shouldMoveLeft() throws Exception {
      parser.parse("|   | 2 |\n" +
                   "|   | 4 |");

      strategy.moveLeft();

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "| 4 |   |");
    }

    @Test
    void shouldMoveRight() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "| 4 |   |");

      strategy.moveRight();

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   | 4 |");
    }

    @Test
    void shouldMoveUp() throws Exception {
      parser.parse("|   |   |\n" +
                   "| 2 | 4 |");

      strategy.moveUp();

      assertThat(visualizer.visualize()).isEqualTo("| 2 | 4 |\n" +
                                                   "|   |   |");
    }

    @Test
    void shouldMoveDown() throws Exception {
      parser.parse("| 2 | 4 |\n" +
                   "|   |   |");

      strategy.moveDown();

      assertThat(visualizer.visualize()).isEqualTo("|   |   |\n" +
                                                   "| 2 | 4 |");
    }
  }

  @Nested
  class Listeners {
    private String out = "";

    @BeforeEach
    void setUp() throws Exception {
      init(4);
      parser.parse(
              "|     |     |   2 |     |\n" +
              "|   2 |     |   8 |     |\n" +
              "|     |   4 |     |  16 |\n" +
              "|     | 128 |     |     |");

      matrix.addFullMatrixChangeListener(event -> out += "called");
    }

    @AfterEach
    void tearDown() throws Exception {
      assertThat(out).isEqualTo("called");
    }

    @Test
    void shouldCallListenerOnlyOnceWhenMovedLeft() throws Exception {
      strategy.moveLeft();
    }

    @Test
    void shouldCallListenerOnlyOnceWhenMovedRight() throws Exception {
      strategy.moveRight();
    }

    @Test
    void shouldCallListenerOnlyOnceWhenMovedUp() throws Exception {
      strategy.moveUp();
    }

    @Test
    void shouldCallListenerOnlyOnceWhenMovedDown() throws Exception {
      strategy.moveDown();
    }
  }

  @Test
  void integration() throws Exception {
    init(4);

    parser.parse(
            "|     |     |   2 |     |\n" +
            "|   2 |     |   8 |     |\n" +
            "|     |   4 |     |  16 |\n" +
            "|     | 128 |     |     |");

    strategy.moveRight();

    assertThat(visualizer.visualize()).isEqualTo(
            "|     |     |     |   2 |\n" +
            "|     |     |   2 |   8 |\n" +
            "|     |     |   4 |  16 |\n" +
            "|     |     |     | 128 |");

    strategy.moveLeft();

    assertThat(visualizer.visualize()).isEqualTo(
            "|   2 |     |     |     |\n" +
            "|   2 |   8 |     |     |\n" +
            "|   4 |  16 |     |     |\n" +
            "| 128 |     |     |     |");

    strategy.moveUp();

    assertThat(visualizer.visualize()).isEqualTo(
            "|   2 |   8 |     |     |\n" +
            "|   2 |  16 |     |     |\n" +
            "|   4 |     |     |     |\n" +
            "| 128 |     |     |     |");

    strategy.moveDown();

    assertThat(visualizer.visualize()).isEqualTo(
            "|   2 |     |     |     |\n" +
            "|   2 |     |     |     |\n" +
            "|   4 |   8 |     |     |\n" +
            "| 128 |  16 |     |     |");
  }

  private void init(int size) {
    matrix = new GameMatrix(size);
    parser = new MatrixParser(matrix);
    visualizer = new GameMatrixVisualizer(matrix);
    strategy = new GameDelegatingStrategy(matrix, new SimpleComputingGameStrategy());
  }

  private static class SimpleComputingGameStrategy implements ComputingGameStrategy {
    @Override
    public List<Integer> moveLeft(List<Integer> row) {
      return row;
    }

    @Override
    public List<Integer> moveRight(List<Integer> row) {
      return row;
    }

    @Override
    public List<Integer> moveUp(List<Integer> column) {
      return column;
    }

    @Override
    public List<Integer> moveDown(List<Integer> column) {
      return column;
    }
  }
}
