package cs3500.animator.provider;

/**
 * This interface is for classes representing an animated shape which is given various attributes
 * that describe its appearance and location, and when it appears and disappears.
 * Such an object has no knowledge of any transformations that are occurring to it from
 * tick to tick. It lives in the moment.
 */
public interface IAnimShape {

  /**
   * Check if the shape should be displayed in the animation, given what TIME it is and whether
   * the shape is visible.
   *
   * @param tick Time in the animation
   * @return True if the animation is active, false if it is not
   */
  boolean isActive(int tick);

  /**
   * Make the shape visible or invisible.
   */
  void toggleVisible();

  /**
   * Getter for name, the name of the shape as the animation refers to it.
   *
   * @return String representing the shape's name
   */
  String getName();

  /**
   * Getter for the shape, it's type, size, and parameters.
   *
   * @return The shape object
   */
  IShape getShape();

  /**
   * Getter for the shape type, i.e. rectangle, oval, etc.
   *
   * @return The name of the shape type
   */
  String getShapeType();

  /**
   * Getter for location, the reference point where the shape is considered to be.
   *
   * @return Point where the shape is
   */
  ISLocation getLocation();

  /**
   * Getter for the parameter names, the names of the parameters that describe the shape.
   *
   * @return The names of the parameters that describe the shape
   */
  String[] getPNames();

  /**
   * Getter for the parameter values, the parameters that describe the shape.
   *
   * @return The parameters that describe the shape
   */
  double[] getPValues();

  /**
   * Getter for the shape's color.
   *
   * @return The shape's current color
   */
  ISColor getColor();

  /**
   * Getter for appears, the point in the animation where the shape appears.
   *
   * @return Time at which the shape appears
   */
  int getAppears();

  /**
   * Getter for disappears, the point in the animation where the shape disappears.
   *
   * @return Time at which the shape disappears
   */
  int getDisappears();

  /**
   * Move the shape to a precise location.
   *
   * @param endsAt Translate the shape to this location
   */
  void moveTo(ISLocation endsAt);

  /**
   * Scale an existing shape in size by some scale factors.
   * Note: If not all parameters of the shape are scalable, this method should be overridden
   *
   * @param endsAt Scales each parameter of the shape by this factor
   * @throws IllegalArgumentException Throws exception if the number of scaleFactors doesn't match
   *                                  the number of scalable parameters for the shape
   */
  void scaleTo(double... endsAt);

  /**
   * Setter for the shape's color to the given color.
   *
   * @param endsAt Change to this color
   */
  void changeColor(ISColor endsAt);

  /**
   * Gets the visible boolean flag of the shape.
   *
   * @return the boolean whether the shape is moving
   */
  boolean getVisible();

  /**
   * Sets the visible boolean flag of the shape.
   *
   * @param isVisible Set to true to show the shape, set to false to show the shape
   */
  void setVisible(boolean isVisible);
}
