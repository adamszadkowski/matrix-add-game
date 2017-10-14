package info.szadkowski.matrix.add.game.core.matrix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PointTest {
  private Point point;

  @BeforeEach
  void setUp() throws Exception {
    point = Point.of(2, 3);
  }

  @Test
  void canGetValues() throws Exception {
    assertThat(point.getX()).isEqualTo(2);
    assertThat(point.getY()).isEqualTo(3);
  }

  @Test
  void givenDifferentPoint_willNotBeEqual() throws Exception {
    Point pointToCompare = Point.of(0, 0);

    assertThat(point).isNotEqualTo(pointToCompare);
  }

  @Test
  void givenTheSamePoint_willBeEqual() throws Exception {
    Point pointToCompare = Point.of(2, 3);

    assertThat(point).isEqualTo(pointToCompare);
  }
}
