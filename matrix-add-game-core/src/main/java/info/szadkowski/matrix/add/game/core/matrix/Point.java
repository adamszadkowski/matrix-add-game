package info.szadkowski.matrix.add.game.core.matrix;

public class Point {
  private final int x;
  private final int y;

  private Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Point of(int x, int y) {
    return new Point(x, y);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Point point = (Point) o;

    return x == point.x && y == point.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }

  @Override
  public String toString() {
    return String.format("Point{x=%d, y=%d}", x, y);
  }
}
