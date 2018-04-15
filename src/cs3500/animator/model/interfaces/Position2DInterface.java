package cs3500.animator.model.interfaces;

public interface Position2DInterface {

  /**
   * Allows for the retrieval of the X coordinate.
   *
   * @return The x-coordinate.
   */
  float getX();

  /**
   * Allows for the setting of the X coordinate.
   *
   * @param newX the target x-coordinate.
   */
  void setX(float newX);

  /**
   * Allows for the retrieval of the Y coordinate.
   *
   * @return The y-coordinate.
   */
  float getY();

  /**
   * Allows for the setting of the y coordinate.
   *
   * @param newY the target y-coordinate.
   */
  void setY(float newY);

  /**
   * Standard equality override.
   *
   * @param obj The object to compare to.
   * @return True if obj is a Position2DInterface and has the same X and Y values. False otherwise.
   */
  boolean equals(Object obj);

  /**
   * Standard hashCode override.
   *
   * @return The hashcode of the Position2DInterface.
   */
  int hashCode();

  /**
   * Standard toString override. Formatted as "(X,Y)".
   *
   * @return String format of data.
   */
  String toString();
}
