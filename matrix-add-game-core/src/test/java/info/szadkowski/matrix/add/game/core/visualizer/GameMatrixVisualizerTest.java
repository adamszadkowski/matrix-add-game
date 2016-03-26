package info.szadkowski.matrix.add.game.core.visualizer;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class GameMatrixVisualizerTest {
  private GameMatrix gameMatrix;
  private GameMatrixVisualizer visualizer;

  public class SingleNumber {
    @Before
    public void setUp() throws Exception {
      init(1);
    }

    @Test
    public void shouldPrintEmptyField() throws Exception {
      assertThat(visualizer.visualize()).isEqualTo("|  |");
    }

    @Test
    public void shouldPrintSingleDigitNumber() throws Exception {
      gameMatrix.set(0, 0, 2);
      assertThat(visualizer.visualize()).isEqualTo("| 2 |");
    }

    @Test
    public void shouldPrintNumber() throws Exception {
      gameMatrix.set(0, 0, 512);
      assertThat(visualizer.visualize()).isEqualTo("| 512 |");
    }

  }

  public class SizeOfTwo {

    @Before
    public void setUp() throws Exception {
      init(2);
    }

    @Test
    public void shouldPrintEmptyField() throws Exception {
      assertThat(visualizer.visualize()).isEqualTo(
              "|  |  |\n" +
              "|  |  |");
    }

    @Test
    public void shouldPrintSingleDigitNumbers() throws Exception {
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
    public void shouldAdjustSizeToLongestNumber() throws Exception {
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
