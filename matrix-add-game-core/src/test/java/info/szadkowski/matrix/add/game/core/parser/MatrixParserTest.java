package info.szadkowski.matrix.add.game.core.parser;

import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MatrixParserTest {
  private GameMatrix gameMatrix;
  private MatrixParser parser;

  @Nested
  class IncorrectInput {

    @BeforeEach
    void setUp() throws Exception {
      init(1);
    }

    @Test
    void givenNullInput_willThrow() throws Exception {
      Assertions.assertThrows(MatrixParser.CorruptedSyntaxException.class, () -> {
        parser.parse(null);
      });
    }

    @Test
    void givenEmptyInput_willThrow() throws Exception {
      Assertions.assertThrows(MatrixParser.CorruptedSyntaxException.class, () -> {
        parser.parse("");
      });
    }

    @Test
    void givenInputWithNotEnoughPipes_willThrow() throws Exception {
      Assertions.assertThrows(MatrixParser.CorruptedSyntaxException.class, () -> {
        parser.parse("|");
      });
    }

    @Test
    void givenInputWithToManyPipes_willThrow() throws Exception {
      Assertions.assertThrows(MatrixParser.CorruptedSyntaxException.class, () -> {
        parser.parse(" | | | ");
      });
    }
  }

  @Nested
  class SingleNumber {
    private GameMatrixVisualizer visualizer;

    @BeforeEach
    void setUp() throws Exception {
      init(1);
      visualizer = new GameMatrixVisualizer(gameMatrix);
    }

    @Test
    void shouldParseEmptySingleFieldMatrix() throws Exception {
      parser.parse("|  | ");

      assertThat(visualizer.visualize()).isEqualTo("|  |");
    }

    @Test
    void shouldParseSingleDigit() throws Exception {
      parser.parse("| 2 |");

      assertThat(visualizer.visualize()).isEqualTo("| 2 |");
    }
  }

  @Nested
  class SizeOfTwo {
    private GameMatrixVisualizer visualizer;

    @BeforeEach
    void setUp() throws Exception {
      init(2);
      visualizer = new GameMatrixVisualizer(gameMatrix);
    }

    @Test
    void shouldParseEmptyFieldsMatrix() throws Exception {
      parser.parse("|  |  |\n" +
                   "|  |  |");

      assertThat(visualizer.visualize()).isEqualTo("|  |  |\n" +
                                                   "|  |  |");
    }

    @Test
    void shouldParseFieldsMatrix() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "| 4 | 8 |");

      assertThat(visualizer.visualize()).isEqualTo("| 2 |   |\n" +
                                                   "| 4 | 8 |");
    }

    @Test
    void shouldParseFieldsWithoutStrictFormatMatrix() throws Exception {
      parser.parse("|2 ||  \n" +
                   " | |   2048   |");

      assertThat(visualizer.visualize()).isEqualTo("|    2 |      |\n" +
                                                   "|      | 2048 |");
    }

    @Test
    void givenNonEmptyGameMatrix_willClearEmptyCells() throws Exception {
      parser.parse("| 2 |   |\n" +
                   "| 4 | 8 |");

      parser.parse("|   | 2 |\n" +
                   "|   |   |");

      assertThat(visualizer.visualize()).isEqualTo("|   | 2 |\n" +
                                                   "|   |   |");
    }
  }

  @Nested
  class Listeners {
    String out = "";

    @BeforeEach
    void setUp() throws Exception {
      init(2);
      gameMatrix.addFullMatrixChangeListener(event -> out += "called");
    }

    @Test
    void shouldCallListenerOnlyOnceAfterParsing() throws Exception {
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
