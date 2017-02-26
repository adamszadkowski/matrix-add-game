package info.szadkowski.matrix.add.game.core.matrix;

import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;

import java.util.*;

public class GameMatrix {
  private final List<FullMatrixChangeListener> listeners = new ArrayList<>();

  private final int[][] matrix;
  private final int size;

  public GameMatrix(int size) {
    this.matrix = new int[size][size];
    this.size = size;
  }

  public int[][] getMatrix() {
    return matrix.clone();
  }

  //used in tests
  public void setMatrix(int[][] gameMatrix) {
    for (int i = 0; i < matrix.length; i++)
      System.arraycopy(gameMatrix[i], 0, matrix[i], 0, matrix[i].length);
  }

  public int get(int x, int y) {
    return matrix[x][y];
  }

  public void set(int x, int y, int n) {
    matrix[x][y] = n;
  }

  public MatrixTransaction createTransaction() {
    return new MatrixTransaction();
  }

  public void addFullMatrixChangeListener(FullMatrixChangeListener listener) {
    listeners.add(listener);
  }

  public int getSize() {
    return size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GameMatrix gameMatrix = (GameMatrix) o;

    return Arrays.deepEquals(matrix, gameMatrix.matrix);
  }

  private void applyChanges(Map<Point, Integer> changes) {
    boolean shouldNotify = false;
    
    for (Map.Entry<Point, Integer> entry : changes.entrySet()) {
      Point coords = entry.getKey();
      Integer value = entry.getValue();
      shouldNotify = shouldNotify || matrix[coords.getX()][coords.getY()] != value;
      matrix[coords.getX()][coords.getY()] = value;
    }

    if (shouldNotify)
      notifyAboutChange();
  }

  private void notifyAboutChange() {
    FullMatrixChangeListener.MatrixChangeEvent event = new FullMatrixChangeListener.MatrixChangeEvent(this);
    listeners
            .forEach(listener -> listener.update(event));
  }

  public class MatrixTransaction {
    private final Map<Point, Integer> changes = new HashMap<>();

    public MatrixTransaction set(int x, int y, int n) {
      changes.put(Point.of(x, y), n);
      return this;
    }

    public void finalizeTransaction() {
      applyChanges(changes);
    }
  }
}
