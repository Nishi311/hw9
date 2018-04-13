package cs3500.animator.converters.shapes;

import java.util.List;

import cs3500.animator.model.concreteclasses.shapes.Oval;
import cs3500.animator.provider.IShape;

public class ProviderOval implements IShape {
  Oval oval;

  public ProviderOval(double xRadius, double yRadius) {
    this.oval = new Oval();
  }

  @Override
  public IShape makeCopy() {
    return null;
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
    List<String> temp = this.oval.getattributeLennames();
    String[] names = new String[temp.size()];
    temp.toArray(names);
    return names;
  }

  @Override
  public double[] getPValues() {
    return this.oval.allDimensions();
  }

  @Override
  public void scaleTo(double... scaleFactors) {

  }
}
