package cs3500.animator.model.interfaces;

/**
 * Basic color class that contains a float value for Red, Green and Blue. Also contains methods
 * to compare against other objects, and returning the value as a string and as an SVG color value.
 */
public interface ColorClassInterface {
  /**
   * Allows for the return of the color's red value.
   *
   * @return Color's red value.
   */
  float getRed();

  /**
   * Allows for the color's red value to be set.
   *
   * @param newRed new red value.
   * @throws IllegalArgumentException If the value is negative.
   */
  void setRed(float newRed);

  /**
   * Allows for the return of the color's green value.
   *
   * @return Color's green value.
   */
  float getGreen();

  /**
   * Allows for the color's green value to be set.
   *
   * @param newGreen new green value.
   * @throws IllegalArgumentException If the value is negative.
   */
  void setGreen(float newGreen);

  /**
   * Allows for the return of the color's blue value.
   *
   * @return Color's blue value.
   */
  float getBlue();

  /**
   * Allows for the color's blue value to be set.
   *
   * @param newBlue new blue value.
   * @throws IllegalArgumentException If the value is negative.
   */
  void setBlue(float newBlue);

  /**
   * Standard equality override. Takes an object and compares it against
   * RGB values.
   *
   * @param obj The object to compare against.
   * @return True if object is ColorClassInterface and contains identical RGB values.
   */
  boolean equals(Object obj);

  /**
   * Standard hashCode override.
   *
   * @return The hash value of the ColorClassInterface
   */
  int hashCode();

  /**
   * Returns the RGB values as a string of format "(R,G,B)".
   *
   * @return Formatted string of color values.
   */
  String toString();

  /**
   * Converts the RGB values into integer values suitable for SVG use. Will still be a string
   * of format "(R,G,B)"
   *
   * @return Formatted string of color values where the floats are now ints.
   */
  String tranferrbg();
}
