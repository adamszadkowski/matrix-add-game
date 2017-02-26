package info.szadkowski.matrix.add.game.core.matrix;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class GameMatrixTest {
  private GameMatrix matrix;

  @Before
  public void setUp() throws Exception {
    matrix = new GameMatrix(4);
  }

  @Test
  public void canGetSize() throws Exception {
    assertThat(matrix.getSize()).isEqualTo(4);
  }

  public class SettingCorrectValues {
    @Test
    public void canSetByField() throws Exception {
      matrix.set(0, 0, 2);
      matrix.set(2, 1, 4);
    }

    @Test
    public void canSetUsingTransaction() throws Exception {
      matrix.createTransaction()
                .set(0, 0, 2)
                .set(2, 1, 4)
              .finalizeTransaction();
    }

    @After
    public void tearDown() throws Exception {
      assertThat(matrix.get(0, 0)).isEqualTo(2);
      assertThat(matrix.get(2, 1)).isEqualTo(4);
    }
  }

  public class ListenerNotification {
    private List<GameMatrix> result;

    @Before
    public void setUp() throws Exception {
      result = new ArrayList<>();
      matrix.addFullMatrixChangeListener(event -> result.add(event.getGameMatrix()));
    }

    @Test
    public void shouldNotNotifyOnEmptyTransaction() throws Exception {
      matrix.createTransaction().finalizeTransaction();

      assertThat(result).isEmpty();
    }

    @Test
    public void givenTransactionWhichDoesNotChangeMatrix_willNotNotify() throws Exception {
      matrix.createTransaction()
                .set(0, 0, matrix.get(0, 0))
                .set(0, 1, matrix.get(0, 1))
                .set(1, 1, matrix.get(1, 1))
              .finalizeTransaction();

      assertThat(result).isEmpty();
    }


    @Test
    public void givenTransactionWhichChangesMatrix_willNotify() throws Exception {
      matrix.createTransaction()
                .set(0, 0, matrix.get(0, 0) + 1)
                .set(0, 1, matrix.get(0, 1) + 1)
                .set(1, 1, matrix.get(1, 1) + 1)
              .finalizeTransaction();

      assertThat(result).containsExactly(matrix);
    }

    @Test
    public void shouldNotifyAllListeners() throws Exception {
      matrix.addFullMatrixChangeListener(event -> result.add(event.getGameMatrix()));

      matrix.createTransaction()
                .set(0, 0, matrix.get(0, 0) + 1)
              .finalizeTransaction();

      assertThat(result).containsExactly(matrix, matrix);
    }
  }

  public class Comparison {
    final GameMatrix matrixToCompare = new GameMatrix(4);

    @Before
    public void setUp() throws Exception {
      setValues(matrix);
    }

    @Test
    public void shouldBeDifferent() throws Exception {
      matrixToCompare.set(0, 1, 2);
      assertThat(matrixToCompare).isNotEqualTo(matrix);
    }

    @Test
    public void shouldBeTheSame() throws Exception {
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
