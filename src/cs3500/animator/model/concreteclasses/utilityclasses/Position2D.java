package cs3500.animator.model.concreteclasses.utilityclasses;

import java.util.Objects;

/**
 * Basic class representing a position.
 */
public class Position2D {
  private float xRef;
  private float yRef;

  /**
   * Basic Position2D constructor.
   *
   * @param xRef X coordinate.
   * @param yRef Y coordinate.
   */
  public Position2D(float xRef, float yRef) throws IllegalArgumentException {

    this.xRef = xRef;
    this.yRef = yRef;
  }

  /**
   * Copy Position2D constructor.
   *
   * @param copyPos The position to be copied.
   */
  public Position2D(Position2D copyPos) {
    this.xRef = copyPos.getX();
    this.yRef = copyPos.getY();
  }

  /**
   * Default Postition2D constructor. Will set coordinates to (0,0).
   */
  public Position2D() {
    this.xRef = 0;
    this.yRef = 0;
  }

  /**
   * Allows for the retrieval of the X coordinate.
   *
   * @return The x-coordinate.
   */
  public float getX() {
    return xRef;
  }

  /**
   * Allows for the setting of the X coordinate.
   *
   * @param newX the target x-coordinate.
   */
  public void setX(float newX) throws IllegalArgumentException {
    xRef = newX;
  }

  /**
   * Allows for the retrieval of the Y coordinate.
   *
   * @return The y-coordinate.
   */
  public float getY() {
    return yRef;
  }

  /**
   * Allows for the setting of the y coordinate.
   *
   * @param newY the target y-coordinate.
   */
  public void setY(float newY) throws IllegalArgumentException {
    yRef = newY;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Position2D)) {
      return false;
    }
    Position2D comparePos = (Position2D) obj;

    if (xRef == comparePos.getX()) {
      if (yRef == comparePos.getY()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(xRef, yRef);
  }

  @Override
  public String toString() {
    String output = "(" + xRef + "," + yRef + ")";
    return output;
  }
}
