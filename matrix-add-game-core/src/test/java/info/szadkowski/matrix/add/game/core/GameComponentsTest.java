package info.szadkowski.matrix.add.game.core;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.parser.MatrixParser;
import info.szadkowski.matrix.add.game.core.strategy.AddComputingGameStrategy;
import info.szadkowski.matrix.add.game.core.strategy.GameDelegatingStrategy;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameComponentsTest {
  private GameMatrix matrix;
  private MatrixParser parser;
  private GameMatrixVisualizer visualizer;
  private GameDelegatingStrategy strategy;

  @BeforeEach
  void setUp() throws Exception {
    matrix = new GameMatrix(4);
    parser = new MatrixParser(matrix);
    visualizer = new GameMatrixVisualizer(matrix);
    strategy = new GameDelegatingStrategy(matrix, new AddComputingGameStrategy());
  }

  @Test
  void integration() throws Exception {
    parser.parse(
            "| 2 |   | 2 |   |\n" +
            "|   |   |   |   |\n" +
            "|   | 2 |   | 4 |\n" +
            "|   |   |   | 8 |");

    strategy.moveDown();

    assertThat(visualizer.visualize()).isEqualTo(
            "|   |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "|   |   |   | 4 |\n" +
            "| 2 | 2 | 2 | 8 |");

    strategy.moveRight();

    assertThat(visualizer.visualize()).isEqualTo(
            "|   |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "|   |   |   | 4 |\n" +
            "|   | 2 | 4 | 8 |");

    strategy.moveUp();

    assertThat(visualizer.visualize()).isEqualTo(
            "|   | 2 | 4 | 4 |\n" +
            "|   |   |   | 8 |\n" +
            "|   |   |   |   |\n" +
            "|   |   |   |   |");

    strategy.moveLeft();

    assertThat(visualizer.visualize()).isEqualTo(
            "| 2 | 8 |   |   |\n" +
            "| 8 |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "|   |   |   |   |");
  }

  @Test
  void shouldAddNewNumbers() throws Exception {
    parser.parse(
            "| 2 |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "|   |   |   |   |");

    matrix.addFullMatrixChangeListener(event -> event.getGameMatrix().set(0, 0, 2));

    strategy.moveDown();

    assertThat(visualizer.visualize()).isEqualTo(
            "| 2 |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "| 2 |   |   |   |");

    strategy.moveDown();

    assertThat(visualizer.visualize()).isEqualTo(
            "| 2 |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "| 4 |   |   |   |");

    strategy.moveDown();

    assertThat(visualizer.visualize()).isEqualTo(
            "| 2 |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "| 2 |   |   |   |\n" +
            "| 4 |   |   |   |");

    strategy.moveDown();

    assertThat(visualizer.visualize()).isEqualTo(
            "| 2 |   |   |   |\n" +
            "|   |   |   |   |\n" +
            "| 4 |   |   |   |\n" +
            "| 4 |   |   |   |");
  }
}
