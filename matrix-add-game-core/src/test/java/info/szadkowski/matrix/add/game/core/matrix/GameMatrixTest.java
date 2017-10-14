package info.szadkowski.matrix.add.game.core.matrix;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameMatrixTest {
  private GameMatrix matrix;

  @BeforeEach
  void setUp() throws Exception {
    matrix = new GameMatrix(4);
  }

  @Test
  void canGetSize() throws Exception {
    assertThat(matrix.getSize()).isEqualTo(4);
  }

  @Nested
  class SettingCorrectValues {

    @AfterEach
    void tearDown() throws Exception {
      assertThat(matrix.get(0, 0)).isEqualTo(2);
      assertThat(matrix.get(2, 1)).isEqualTo(4);
    }

    @Test
    void canSetByField() throws Exception {
      matrix.set(0, 0, 2);
      matrix.set(2, 1, 4);
    }

    @Test
    void canSetUsingTransaction() throws Exception {
      matrix.createTransaction()
              .set(0, 0, 2)
              .set(2, 1, 4)
              .finalizeTransaction();
    }
  }

  @Nested
  class ListenerNotification {
    private List<GameMatrix> result;

    @BeforeEach
    void setUp() throws Exception {
      result = new ArrayList<>();
      matrix.addFullMatrixChangeListener(event -> result.add(event.getGameMatrix()));
    }

    @Test
    void shouldNotNotifyOnEmptyTransaction() throws Exception {
      matrix.createTransaction().finalizeTransaction();

      assertThat(result).isEmpty();
    }

    @Test
    void givenTransactionWhichDoesNotChangeMatrix_willNotNotify() throws Exception {
      matrix.createTransaction()
              .set(0, 0, matrix.get(0, 0))
              .set(0, 1, matrix.get(0, 1))
              .set(1, 1, matrix.get(1, 1))
              .finalizeTransaction();

      assertThat(result).isEmpty();
    }

    @Test
    void givenTransactionWhichChangesMatrix_willNotify() throws Exception {
      matrix.createTransaction()
              .set(0, 0, matrix.get(0, 0) + 1)
              .set(0, 1, matrix.get(0, 1) + 1)
              .set(1, 1, matrix.get(1, 1) + 1)
              .finalizeTransaction();

      assertThat(result).containsExactly(matrix);
    }

    @Test
    void shouldNotifyAllListeners() throws Exception {
      matrix.addFullMatrixChangeListener(event -> result.add(event.getGameMatrix()));

      matrix.createTransaction()
              .set(0, 0, matrix.get(0, 0) + 1)
              .finalizeTransaction();

      assertThat(result).containsExactly(matrix, matrix);
    }
  }

  @Nested
  class Comparison {
    final GameMatrix matrixToCompare = new GameMatrix(4);

    @BeforeEach
    void setUp() throws Exception {
      setValues(matrix);
    }

    @Test
    void shouldBeDifferent() throws Exception {
      matrixToCompare.set(0, 1, 2);
      assertThat(matrixToCompare).isNotEqualTo(matrix);
    }

    @Test
    void shouldBeTheSame() throws Exception {
      setValues(matrixToCompare);
      assertThat(matrixToCompare).isEqualTo(matrix);
    }

    private void setValues(GameMatrix matrix) {
      matrix.createTransaction()
              .set(0, 1, 2)
              .set(1, 2, 4)
              .set(2, 3, 8)
              .finalizeTransaction();
    }
  }
}
