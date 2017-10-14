package info.szadkowski.matrix.add.game.core.listener;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.parser.MatrixParser;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RandomNumberAppenderTest {
  private NumberAppender appender;
  private FullMatrixChangeListener.MatrixChangeEvent event;

  @BeforeEach
  void setUp() throws Exception {
    appender = new RandomNumberAppender();
  }

  @Test
  void givenNullEvent_willThrow() throws Exception {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      appender.update(null);
    });
  }

  @Nested
  class ZeroSize {

    @BeforeEach
    void setUp() throws Exception {
      init(0);
    }

    @Test
    void shouldThrow() throws Exception {
      Assertions.assertThrows(NumberAppender.MatrixFullException.class, () -> {
        appender.update(event);
      });
    }
  }

  @Nested
  class SingleNumber {
    private MatrixParser parser;
    private GameMatrixVisualizer visualizer;

    @BeforeEach
    void setUp() throws Exception {
      GameMatrix matrix = init(1);
      parser = new MatrixParser(matrix);
      visualizer = new GameMatrixVisualizer(matrix);
    }

    @Test
    void givenFullMatrix_willThrow() throws Exception {
      parser.parse("| 2 |");

      Assertions.assertThrows(NumberAppender.MatrixFullException.class, () -> {
        appender.update(event);
      });
    }

    @Test
    void givenEmptyMatrix_willFill() throws Exception {
      parser.parse("|  |");

      appender.update(event);

      assertThat(visualizer.visualize()).isIn("| 2 |");
    }
  }

  @Nested
  class SizeOfTwo {
    private MatrixParser parser;
    private GameMatrixVisualizer visualizer;

    @BeforeEach
    void setUp() throws Exception {
      GameMatrix matrix = init(2);
      parser = new MatrixParser(matrix);
      visualizer = new GameMatrixVisualizer(matrix);
    }

    @Test
    void givenFullMatrix_willThrow() throws Exception {
      parser.parse("| 2 | 4 |\n" +
                   "| 8 | 2 |");


      Assertions.assertThrows(NumberAppender.MatrixFullException.class, () -> {
        appender.update(event);
      });
    }

    @Test
    void givenOneCellEmpty_willFill() throws Exception {
      parser.parse("| 2 | 4 |\n" +
                   "| 8 |   |");

      appender.update(event);

      assertThat(visualizer.visualize()).isIn(
              "| 2 | 4 |\n" +
              "| 8 | 2 |");
    }

    @Test
    void givenAllEmptyCells_willFillOnlyOne() throws Exception {
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

  @Nested
  class Randomization {
    private MatrixParser parser;
    private GameMatrixVisualizer visualizer;

    @BeforeEach
    void setUp() throws Exception {
      GameMatrix matrix = init(2);
      parser = new MatrixParser(matrix);
      visualizer = new GameMatrixVisualizer(matrix);
    }

    @Test
    void givenTwoEmptyCells_willFillWithEqualProbability() throws Exception {
      Map<String, Integer> possibilities = randomize("|   |   |\n" +
                                                     "| 2 | 4 |", 1000);

      assertThat(possibilities).hasSize(2);
      assertThat(possibilities.get("| 2 |   |\n" +
                                   "| 2 | 4 |")).isBetween(400, 600);
      assertThat(possibilities.get("|   | 2 |\n" +
                                   "| 2 | 4 |")).isBetween(400, 600);
    }

    @Test
    void givenAllEmptyCells_willFillWithEqualProbability() throws Exception {
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
