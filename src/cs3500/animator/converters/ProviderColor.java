package cs3500.animator.converters;

import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.provider.ISColor;

public class ProviderColor implements ISColor {
  ColorClass color;

  public ProviderColor(ColorClass color){
    this.color = color;
  }

  public ProviderColor(ISColor toCopy){
    this.color = new ColorClass((float) toCopy.getRed(), (float) toCopy.getGreen(),
            (float) toCopy.getBlue());
  }

  public ProviderColor(double red, double green, double blue){
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
    return String.format("(%.1f,%.1f,%.1f)",this.color.getRed(), this.color.getGreen(),
            this.color.getBlue());
  }
}
