package cs3500.animator.converters.shapes;

import java.util.List;

import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.provider.IShape;

public class ProviderRectangle implements IShape {
  protected String type;
  protected String reference;

  protected String[] pNames = {"Width", "Height"};
  protected double[] pValues;

  public ProviderRectangle(double width, double height){
    if (pNames.length != pValues.length || pNames.length == 0) {
      throw new IllegalArgumentException("Parameters number not legal!");
    }

    double[] pValues = {width, height};

    this.pValues = pValues;
    this.type = "rectangle";
    this.reference = "Min-corner";

    checkValid();
  }

  @Override
  public IShape makeCopy() {
    return new ProviderRectangle(pValues[0], pValues[1]);
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
