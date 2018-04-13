package cs3500.animator.converters.shapes;

import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.provider.IShape;

public class IShapeAdaptor implements IShape {
  ShapeInterface s;

  public IShapeAdaptor(ShapeInterface s) {
    this.s = s;
  }

  @Override
  public IShape makeCopy() {
  }

  @Override
  public boolean checkValid() throws IllegalArgumentException {
    return false;
  }

  @Override
  public String getType() {
    return null;
  }

  @Override
  public String getReference() {
    return null;
  }

  @Override
  public String[] getPNames() {
    return new String[0];
  }

  @Override
  public double[] getPValues() {
    return new double[0];
  }

  @Override
  public void scaleTo(double... scaleFactors) {

  }
}
