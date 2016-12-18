package info.szadkowski.matrix.add.game.core.listener;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.parser.MatrixParser;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class RandomNumberAppenderTest {
  private NumberAppender appender;
  private FullMatrixChangeListener.MatrixChangeEvent event;

  @Before
  public void setUp() throws Exception {
    appender = new RandomNumberAppender();
  }


  @Test(expected = IllegalArgumentException.class)
  public void givenNullEvent_willThrow() throws Exception {
    appender.update(null);
  }

  public class ZeroSize {
    @Before
    public void setUp() throws Exception {
      init(0);
    }

    @Test(expected = NumberAppender.MatrixFullException.class)
    public void shouldThrow() throws Exception {
      appender.update(event);
    }
  }

  public class SingleNumber {
    private MatrixParser parser;
    private GameMatrixVisualizer visualizer;

    @Before
    public void setUp() throws Exception {
      GameMatrix matrix = init(1);
      parser = new MatrixParser(matrix);
      visualizer = new GameMatrixVisualizer(matrix);
    }

    @Test(expected = NumberAppender.MatrixFullException.class)
    public void givenFullMatrix_willThrow() throws Exception {
      parser.parse("| 2 |");

      appender.update(event);
    }

    @Test
    public void givenEmptyMatrix_willFill() throws Exception {
      parser.parse("|  |");

      appender.update(event);

      assertThat(visualizer.visualize()).isIn("| 2 |");
    }
  }

  public class SizeOfTwo {
    private MatrixParser parser;
    private GameMatrixVisualizer visualizer;

    @Before
    public void setUp() throws Exception {
      GameMatrix matrix = init(2);
      parser = new MatrixParser(matrix);
      visualizer = new GameMatrixVisualizer(matrix);
    }

    @Test(expected = NumberAppender.MatrixFullException.class)
    public void givenFullMatrix_willThrow() throws Exception {
      parser.parse("| 2 | 4 |\n" +
                   "| 8 | 2 |");

      appender.update(event);
    }

    @Test
    public void givenOneCellEmpty_willFill() throws Exception {
      parser.parse("| 2 | 4 |\n" +
                   "| 8 |   |");

      appender.update(event);

      assertThat(visualizer.visualize()).isIn(
              "| 2 | 4 |\n" +
              "| 8 | 2 |");
    }

    @Test
    public void givenAllEmptyCells_willFillOnlyOne() throws Exception {
      parser.parse("|   |   |\n" +
                   "|   |   |");

      appender.update(event);

      assertThat(visualizer.visualize()).isIn(
              "| 2 |   |\n" +
              "|   |   |",

              "|   | 2 |\n" +
              "|   |   |",

              "|   |   |\n" +
              "| 2 |   |",

              "|   |   |\n" +
              "|   | 2 |");
    }
  }

  public class Randomization {
    private MatrixParser parser;
    private GameMatrixVisualizer visualizer;

    @Before
    public void setUp() throws Exception {
      GameMatrix matrix = init(2);
      parser = new MatrixParser(matrix);
      visualizer = new GameMatrixVisualizer(matrix);
    }

    @Test
    public void givenTwoEmptyCells_willFillWithEqualProbability() throws Exception {
      Map<String, Integer> possibilities = randomize("|   |   |\n" +
                                                     "| 2 | 4 |", 1000);

      assertThat(possibilities).hasSize(2);
      assertThat(possibilities.get("| 2 |   |\n" +
                                   "| 2 | 4 |")).isBetween(400, 600);
      assertThat(possibilities.get("|   | 2 |\n" +
                                   "| 2 | 4 |")).isBetween(400, 600);
    }

    @Test
    public void givenAllEmptyCells_willFillWithEqualProbability() throws Exception {
      Map<String, Integer> possibilities = randomize("|   |   |\n" +
                                                     "|   |   |", 1000);

      assertThat(possibilities).hasSize(4);
      assertThat(possibilities.get("| 2 |   |\n" +
                                   "|   |   |")).isBetween(200, 300);
      assertThat(possibilities.get("|   | 2 |\n" +
                                   "|   |   |")).isBetween(200, 300);
      assertThat(possibilities.get("|   |   |\n" +
                                   "| 2 |   |")).isBetween(200, 300);
      assertThat(possibilities.get("|   |   |\n" +
                                   "|   | 2 |")).isBetween(200, 300);
    }

    private Map<String, Integer> randomize(String initialMatrix, int count) {
      Map<String, Integer> possibilities = new HashMap<>();

      for (int i = 0; i < count; i++) {
        String visualized = randomize(initialMatrix);
        possibilities.computeIfAbsent(visualized, v -> 0);
        possibilities.computeIfPresent(visualized, (v, n) -> n + 1);
      }

      return possibilities;
    }

    private String randomize(String matrix) {
      parser.parse(matrix);
      appender.update(event);
      return visualizer.visualize();
    }
  }

  private GameMatrix init(int size) {
    GameMatrix matrix = new GameMatrix(size);
    event = new FullMatrixChangeListener.MatrixChangeEvent(matrix);
    return matrix;
  }
}
