package info.szadkowski.matrix.add.game.core.parser;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class MatrixParserTest {
  private GameMatrix gameMatrix;
  private MatrixParser parser;

  public class IncorrectInput {
    @Before
    public void setUp() throws Exception {
      init(1);
    }

    @Test(expected = MatrixParser.CorruptedSyntaxException.class)
    public void givenNullInput_willThrow() throws Exception {
      parser.parse(null);
    }

    @Test(expected = MatrixParser.CorruptedSyntaxException.class)
    public void givenEmptyInput_willThrow() throws Exception {
      parser.parse("");
    }

    @Test(expected = MatrixParser.CorruptedSyntaxException.class)
    public void givenInputWithNotEnoughPipes_willThrow() throws Exception {
      parser.parse("|");
    }

    @Test(expected = MatrixParser.CorruptedSyntaxException.class)
    public void givenInputWithToManyPipes_willThrow() throws Exception {
      parser.parse(" | | | ");
    }
  }

  public class SingleNumber {
    private GameMatrixVisualizer visualizer;

    @Before
    public void setUp() throws Exception {
      init(1);
      visualizer = new GameMatrixVisualizer(gameMatrix);
    }

    @Test
    public void shouldParseEmptySingleFieldMatrix() throws Exception {
      parser.parse("|  | ");

      assertThat(visualizer.visualize()).isEqualTo("|  |");
    }

    @Test
    public void shouldParseSingleDigit() throws Exception {
      parser.parse("| 2 |");

      assertThat(visualizer.visualize()).isEqualTo("| 2 |");
    }
  }

  public class SizeOfTwo {
    private GameMatrixVisualizer visualizer;

    @Before
    public void setUp() throws Exception {
      init(2);
      visualizer = new GameMatrixVisualizer(gameMatrix);
    }

    @Test
    public void shouldParseEmptyFieldsMatrix() throws Exception {
      parser.parse("|  |  |\n" +
                   "|  |  |");

      assertThat(visualizer.visualize()).isEqualTo("|  |  |\n" +
                                                   "|  |  |");
    }

    @Test
    public void shouldParseFieldsMatrix() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "| 4 | 8 |");

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "| 4 | 8 |");
    }

    @Test
    public void shouldParseFieldsWithoutStrictFormatMatrix() throws Exception {
      parser.parse("|2 ||  \n" +
                   " | |   2048   |");

      assertThat(visualizer.visualize()).isEqualTo("|    2 |      |\n" +
                                                   "|      | 2048 |");
    }
  }

  public class Listeners {
    String out = "";

    @Before
    public void setUp() throws Exception {
      init(2);
      gameMatrix.addFullMatrixChangeListener(event -> out += "called");
    }

    @Test
    public void shouldCallListenerOnlyOnceAfterParsing() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "| 4 | 8 |");

      assertThat(out).isEqualTo("called");
    }
  }

  private void init(int size) {
    gameMatrix = new GameMatrix(size);
    parser = new MatrixParser(gameMatrix);
  }
}
