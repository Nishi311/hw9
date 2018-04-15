package cs3500.animator.converters;

import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.provider.ISColor;

/**
 * Custom implementation of the provider's ISColor interface. Contains RGB values necessary to
 * display a color for a shape. Basically just wraps around our own ColorClass.
 */
public class ProviderColor implements ISColor {
  ColorClass color;

  /**
   * Conversion Constructor. Allows us to create a provider compatible ISColor with our own
   * ColorClass.
   *
   * @param color The colorClass to wrap.
   */
  public ProviderColor(ColorClass color) {
    this.color = color;
  }

  /**
   * Copy Constructor. Necessary for makeCopy() methods.
   *
   * @param toCopy The ISColor to copy.
   */
  public ProviderColor(ISColor toCopy) {
    this.color = new ColorClass((float) toCopy.getRed(), (float) toCopy.getGreen(),
            (float) toCopy.getBlue());
  }

  /**
   * The basic constructor needed to create a new ISColor from scratch.
   *
   * @param red   The red value of the color.
   * @param green The green value of the color.
   * @param blue  The blue value of the color.
   */
  public ProviderColor(double red, double green, double blue) {
    this.color = new ColorClass((float) red, (float) green, (float) blue);
  }

  @Override
  public double getRed() {
    return this.color.getRed();
  }

  @Override
  public void setRed(double red) {
    this.color.setRed((float) red);
  }

  @Override
  public double getGreen() {
    return this.color.getGreen();
  }

  @Override
  public void setGreen(double green) {
    this.color.setGreen((float) green);
  }

  @Override
  public double getBlue() {
    return this.color.getBlue();
  }

  @Override
  public void setBlue(double blue) {
    this.color.setBlue((float) blue);
  }

  @Override
  public String toString() {
    return String.format("(%.1f,%.1f,%.1f)", this.color.getRed(), this.color.getGreen(),
            this.color.getBlue());
  }
}
