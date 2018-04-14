package cs3500.animator.converters.shapes;

import java.util.List;

import cs3500.animator.model.concreteclasses.shapes.Oval;
import cs3500.animator.provider.IShape;

public class ProviderOval implements IShape {
  protected String type;
  protected String reference;

  protected String[] pNames = {"X radius", "Y radius"};
  protected double[] pValues;


  public ProviderOval(double xRadius, double yRadius) {
    if (pNames.length != pValues.length || pNames.length == 0) {
      throw new IllegalArgumentException("Parameters number not legal!");
    }

    double[] pValues = {xRadius, yRadius};

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
}
