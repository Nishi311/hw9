package cs3500.animator.converters.shapes;

import cs3500.animator.provider.IShape;

/**
 * Custom implementation of the IShape Interface. Contains only information necessary to
 * make an oval (no color, position, etc).
 */
public class ProviderOval implements IShape {
  protected String type;
  protected String reference;

  protected String[] pNames = {"X radius", "Y radius"};
  protected double[] pValues;

  /**
   * Standard constructor. Creates an oval using the xRadius as it's horizontal length and yRadius
   * as its vertical length.
   *
   * @param xRadius The horizontal length of the oval.
   * @param yRadius The vertical length of the oval.
   */
  public ProviderOval(double xRadius, double yRadius) {
    double[] pValues = {xRadius, yRadius};
    if (pNames.length != pValues.length || pNames.length == 0) {
      throw new IllegalArgumentException("Parameters number not legal!");
    }

    this.pValues = pValues;
    this.type = "oval";
    this.reference = "Center";

    checkValid();
  }

  @Override
  public IShape makeCopy() {
    return new ProviderOval(pValues[0], pValues[1]);
  }

  @Override
  public boolean checkValid() throws IllegalArgumentException {
    if (pValues[0] < 0 || pValues[1] < 0) {
      throw new IllegalArgumentException("Width and Height cannot be negative!");
    }
    return true;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public String getReference() {
    return reference;
  }

  @Override
  public String[] getPNames() {
    return pNames;
  }

  @Override
  public double[] getPValues() {
    return pValues;
  }

  @Override
  public void scaleTo(double... scaleFactors) {
    if (scaleFactors.length != pValues.length) {
      throw new IllegalArgumentException("Parameter number does not match!");
    }
    for (int i = 0; i < scaleFactors.length; i++) {
      pValues[i] = scaleFactors[i];
    }
    checkValid();
  }

  @Override
  public String toString() {
    String result = String.format("%s: %.1f", pNames[0], pValues[0]);
    for (int i = 1; i < pNames.length; i++) {
      result += String.format(", %s: %.1f", pNames[i], pValues[i]);
    }
    return result;
  }
}
