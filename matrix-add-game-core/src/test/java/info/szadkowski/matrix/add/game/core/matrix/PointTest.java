package info.szadkowski.matrix.add.game.core.matrix;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {
  private Point point;

  @Before
  public void setUp() throws Exception {
    point = Point.of(2, 3);
  }

  @Test
  public void canGetValues() throws Exception {
    assertThat(point.getX()).isEqualTo(2);
    assertThat(point.getY()).isEqualTo(3);
  }

  @Test
  public void givenDifferentPoint_willNotBeEqual() throws Exception {
    Point pointToCompare = Point.of(0, 0);

    assertThat(point).isNotEqualTo(pointToCompare);
  }

  @Test
  public void givenTheSamePoint_willBeEqual() throws Exception {
    Point pointToCompare = Point.of(2, 3);

    assertThat(point).isEqualTo(pointToCompare);
  }
}
