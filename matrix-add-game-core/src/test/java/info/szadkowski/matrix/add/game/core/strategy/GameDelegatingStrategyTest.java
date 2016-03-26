package info.szadkowski.matrix.add.game.core.strategy;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.parser.MatrixParser;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class GameDelegatingStrategyTest {
  private GameMatrix matrix;
  private MatrixParser parser;
  private GameMatrixVisualizer visualizer;
  private GameStrategy strategy;

  public class BlockedMove {

    @Before
    public void setUp() throws Exception {
      init(2);
    }

    @Test
    public void shouldNotMoveLeft() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "|   |   |");

      strategy.moveLeft();

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "|   |   |");
    }

    @Test
    public void shouldNotMoveRight() throws Exception {
      parser.parse("|   | 2 |\n" +
                   "|   |   |");

      strategy.moveRight();

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   |   |");
    }

    @Test
    public void shouldNotMoveUp() throws Exception {
      parser.parse("|   | 2 |\n" +
                   "|   |   |");

      strategy.moveUp();

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   |   |");
    }

    @Test
    public void shouldNotMoveDown() throws Exception {
      parser.parse("|   |   |\n" +
                   "|   | 2 |");

      strategy.moveDown();

      assertThat(visualizer.visualize()).isEqualTo("|   |   |\n" +
                                                   "|   | 2 |");
    }

  }

  public class SingleNumberMovement {

    @Before
    public void setUp() throws Exception {
      init(2);
    }

    @Test
    public void shouldMoveLeft() throws Exception {
      parser.parse("|   | 2 |\n" +
                   "|   |   |");

      strategy.moveLeft();

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "|   |   |");
    }

    @Test
    public void shouldMoveRight() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "|   |   |");

      strategy.moveRight();

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   |   |");
    }

    @Test
    public void shouldMoveUp() throws Exception {
      parser.parse("|   |   |\n" +
                   "| 2 |   |");

      strategy.moveUp();

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "|   |   |");
    }

    @Test
    public void shouldMoveDown() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "|   |   |");

      strategy.moveDown();

      assertThat(visualizer.visualize()).isEqualTo("|   |   |\n" +
                                                   "| 2 |   |");
    }

  }

  public class MultiNumberMovement {

    @Before
    public void setUp() throws Exception {
      init(2);
    }

    @Test
    public void shouldMoveLeft() throws Exception {
      parser.parse("|   | 2 |\n" +
                   "|   | 4 |");

      strategy.moveLeft();

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "| 4 |   |");
    }

    @Test
    public void shouldMoveRight() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "| 4 |   |");

      strategy.moveRight();

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   | 4 |");
    }

    @Test
    public void shouldMoveUp() throws Exception {
      parser.parse("|   |   |\n" +
                   "| 2 | 4 |");

      strategy.moveUp();

      assertThat(visualizer.visualize()).isEqualTo("| 2 | 4 |\n" +
                                                   "|   |   |");
    }

    @Test
    public void shouldMoveDown() throws Exception {
      parser.parse("| 2 | 4 |\n" +
                   "|   |   |");

      strategy.moveDown();

      assertThat(visualizer.visualize()).isEqualTo("|   |   |\n" +
                                                   "| 2 | 4 |");
    }

  }

  public class Listeners {
    private String out = "";

    @Before
    public void setUp() throws Exception {
      init(4);
      parser.parse(
              "|     |     |   2 |     |\n" +
              "|   2 |     |   8 |     |\n" +
              "|     |   4 |     |  16 |\n" +
              "|     | 128 |     |     |");

      matrix.addFullMatrixChangeListener(event -> out += "called");
    }

    @After
    public void tearDown() throws Exception {
      assertThat(out).isEqualTo("called");
    }

    @Test
    public void shouldCallListenerOnlyOnceWhenMovedLeft() throws Exception {
      strategy.moveLeft();
    }

    @Test
    public void shouldCallListenerOnlyOnceWhenMovedRight() throws Exception {
      strategy.moveRight();
    }

    @Test
    public void shouldCallListenerOnlyOnceWhenMovedUp() throws Exception {
      strategy.moveUp();
    }

    @Test
    public void shouldCallListenerOnlyOnceWhenMovedDown() throws Exception {
      strategy.moveDown();
    }

  }

  @Test
  public void integration() throws Exception {
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
