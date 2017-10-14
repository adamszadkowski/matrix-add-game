package info.szadkowski.matrix.add.game.core.visualizer;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameMatrixVisualizerTest {
  private GameMatrix gameMatrix;
  private GameMatrixVisualizer visualizer;

  @Nested
  class SingleNumber {

    @BeforeEach
    void setUp() throws Exception {
      init(1);
    }

    @Test
    void shouldPrintEmptyField() throws Exception {
      assertThat(visualizer.visualize()).isEqualTo("|  |");
    }

    @Test
    void shouldPrintSingleDigitNumber() throws Exception {
      gameMatrix.set(0, 0, 2);
      assertThat(visualizer.visualize()).isEqualTo("| 2 |");
    }

    @Test
    void shouldPrintNumber() throws Exception {
      gameMatrix.set(0, 0, 512);
      assertThat(visualizer.visualize()).isEqualTo("| 512 |");
    }
  }

  @Nested
  class SizeOfTwo {

    @BeforeEach
    void setUp() throws Exception {
      init(2);
    }

    @Test
    void shouldPrintEmptyField() throws Exception {
      assertThat(visualizer.visualize()).isEqualTo(
              "|  |  |\n" +
              "|  |  |");
    }

    @Test
    void shouldPrintSingleDigitNumbers() throws Exception {
      gameMatrix.createTransaction()
              .set(0, 0, 2)
              .set(0, 1, 4)
              .set(1, 0, 2)
              .set(1, 1, 8)
              .finalizeTransaction();
      assertThat(visualizer.visualize()).isEqualTo(
              "| 2 | 2 |\n" +
              "| 4 | 8 |");
    }

    @Test
    void shouldAdjustSizeToLongestNumber() throws Exception {
      gameMatrix.createTransaction()
              .set(0, 0, 2048)
              .set(0, 1, 4)
              .set(1, 1, 8)
              .finalizeTransaction();
      assertThat(visualizer.visualize()).isEqualTo(
              "| 2048 |      |\n" +
              "|    4 |    8 |");

    }
  }

  private void init(int size) {
    gameMatrix = new GameMatrix(size);
    visualizer = new GameMatrixVisualizer(gameMatrix);
  }
}
