package cs3500.animator.converters.shapes;

import java.util.List;

import cs3500.animator.converters.ProviderColor;
import cs3500.animator.converters.ProviderLocation;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ISColor;
import cs3500.animator.provider.ISLocation;
import cs3500.animator.provider.IShape;

public class ProviderAnimShape implements IAnimShape {
  ShapeInterface s;

  public ProviderAnimShape(ShapeInterface s){
    this.s = s;
  }

  @Override
  public boolean isActive(int tick) {
    // To Be Added
    return false;
  }

  @Override
  public void toggleVisible() {
    // To Be Added
  }

  @Override
  public String getName() {
    return this.s.getName();
  }

  @Override
  public IShape getShape() {
  }

  @Override
  public String getShapeType() {
    return this.s.getShapeType();
  }

  public ISLocation getLocation() {
    return new ProviderLocation(this.s.getPosition());
  }

  @Override
  public String[] getPNames() {
    List<String> temp = this.s.getattributeLennames();
    String[] names = new String[temp.size()];
    temp.toArray(names);
    return names;
  }

  @Override
  public double[] getPValues() {
    return this.s.allDimensions();
  }

  @Override
  public ISColor getColor() {
    return new ProviderColor(this.s.getColor());
  }

  @Override
  public int getAppears() {

  }

  @Override
  public int getDisappears() {
    return 0;
  }

  @Override
  public void moveTo(ISLocation endsAt) {

  }

  @Override
  public void scaleTo(double... endsAt) {

  }

  @Override
  public void changeColor(ISColor endsAt) {
  }

  @Override
  public boolean getVisible() {
    return this.s.getVisibility();
  }

  @Override
  public void setVisible(boolean isVisible) {

  }
}
