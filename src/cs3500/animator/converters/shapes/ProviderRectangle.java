package cs3500.animator.converters.shapes;

import java.util.List;

import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.provider.IShape;

public class ProviderRectangle implements IShape {
  Rectangle rect;

  public ProviderRectangle(double width, double height){
    this.rect = new Rectangle();
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
    return this.rect.getShapeType();
  }

  @Override
  public String getReference() {
  }

  @Override
  public String[] getPNames() {
    List<String> temp = this.rect.getattributeLennames();
    String[] names = new String[temp.size()];
    temp.toArray(names);
    return names;
  }

  @Override
  public double[] getPValues() {
    return this.rect.allDimensions();
  }

  @Override
  public void scaleTo(double... scaleFactors) {

  }
}
