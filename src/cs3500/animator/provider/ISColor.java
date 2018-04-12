package cs3500.animator.provider;


public interface ISColor {
  /**
   * Getter for the red value of the color.
   *
   * @return The red value, between 0.0 and 1.0.
   */
  double getRed();

  /**
   * Setter for the red value of the color.
   *
   * @param red Change the red value to this.
   * @throws IllegalArgumentException Cannot change the color to an invalid color.
   */
  void setRed(double red);

  /**
   * Getter for the green value of the color.
   *
   * @return The green value, between 0.0 and 1.0.
   */
  double getGreen();

  /**
   * Setter for the green value of the color.
   *
   * @param green Change the green value to this.
   * @throws IllegalArgumentException Cannot change the color to an invalid color.
   */
  void setGreen(double green);

  /**
   * Getter for the blue value of the color.
   *
   * @return The blue value, between 0.0 and 1.0.
   */
  double getBlue();

  /**
   * Setter for the blue value of the color.
   *
   * @param blue Change the blue value to this.
   * @throws IllegalArgumentException Cannot change the color to an invalid color.
   */
  void setBlue(double blue);
}
