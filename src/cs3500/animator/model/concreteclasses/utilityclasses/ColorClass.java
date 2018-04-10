package cs3500.animator.model.concreteclasses.utilityclasses;

import java.util.Objects;

/**
 * Basic class that contains RGB color values.
 * INVARIANT: color values cannot be negative.
 */
public class ColorClass {
  private float red;
  private float green;
  private float blue;

  /**
   * Basic ColorClass constructor.
   *
   * @param red   red value.
   * @param green green value.
   * @param blue  blue value.
   * @throws IllegalArgumentException If any of the color values are negative.
   */
  public ColorClass(float red, float green, float blue) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("Cannot have negative color values");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Default ColorClass constructor. Sets color to black (no values).
   */
  public ColorClass() {
    this.red = 0;
    this.green = 0;
    this.blue = 0;
  }

  /**
   * Copy ColorClass Constructor.
   *
   * @param copyColor The color to be copied.
   */
  public ColorClass(ColorClass copyColor) {
    this.red = copyColor.red;
    this.green = copyColor.green;
    this.blue = copyColor.blue;
  }

  /**
   * Allows for the return of the color's red value.
   *
   * @return Color's red value.
   */
  public float getRed() {
    return red;
  }

  /**
   * Allows for the color's red value to be set.
   *
   * @param newRed new red value.
   * @throws IllegalArgumentException If the value is negative.
   */
  public void setRed(float newRed) throws IllegalArgumentException {
    if (newRed < 0) {
      throw new IllegalArgumentException("Cannot have negative color value.");
    }
    red = newRed;
  }

  /**
   * Allows for the return of the color's green value.
   *
   * @return Color's green value.
   */
  public float getGreen() {
    return green;
  }

  /**
   * Allows for the color's green value to be set.
   *
   * @param newGreen new green value.
   * @throws IllegalArgumentException If the value is negative.
   */
  public void setGreen(float newGreen) throws IllegalArgumentException {
    if (newGreen < 0) {
      throw new IllegalArgumentException("Cannot have negative color value.");
    }
    green = newGreen;
  }

  /**
   * Allows for the return of the color's blue value.
   *
   * @return Color's blue value.
   */
  public float getBlue() {
    return blue;
  }

  /**
   * Allows for the color's blue value to be set.
   *
   * @param newBlue new blue value.
   * @throws IllegalArgumentException If the value is negative.
   */
  public void setBlue(float newBlue) throws IllegalArgumentException {
    if (newBlue < 0) {
      throw new IllegalArgumentException("Cannot have negative color value.");
    }
    blue = newBlue;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ColorClass)) {
      return false;
    }
    ColorClass compareColor = (ColorClass) obj;

    if (red == compareColor.getRed()) {
      if (green == compareColor.getGreen()) {
        if (blue == compareColor.getBlue()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, blue, green);
  }

  @Override
  public String toString() {
    return "(" + red + "," + green + "," + blue + ")";
  }

  /**
   * transfer the rbg to the svg.
   *
   * @return the String for svg format.
   */
  public String tranferrbg() {
    return "(" + (int) (red * 255) + ","
            + (int) (green * 255) + "," + (int) (blue * 255) + ")";

  }
}
