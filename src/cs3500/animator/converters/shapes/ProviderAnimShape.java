package cs3500.animator.converters.shapes;

import java.util.List;

import cs3500.animator.converters.ProviderColor;
import cs3500.animator.converters.ProviderLocation;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ISColor;
import cs3500.animator.provider.ISLocation;
import cs3500.animator.provider.IShape;

public class ProviderAnimShape implements IAnimShape {
  ISColor color;
  ISLocation pos;

  String type;
  String name;
  boolean isVisible;

  int appears;
  int disappears;

  IShape shape;

  public ProviderAnimShape(IAnimShape s){
    this.pos = s.getLocation();
    this.color = s.getColor();
    this.type = s.getShapeType();
    this.isVisible = s.getVisible();
    this.name = s.getName();
    this.appears = s.getAppears();
    this.disappears = s.getDisappears();
    this.shape = s.getShape();
  }

  public ProviderAnimShape(ShapeInterface s, AnimationComponentInterface appearance,
                           AnimationComponentInterface disappearance){
    this.pos = new ProviderLocation(s.getPosition());
    this.color = new ProviderColor(s.getColor());
    this.type = s.getShapeType();
    this.isVisible = s.getVisibility();
    this.name = s.getName();
    this.appears = appearance.getStartTime();
    this.disappears = disappearance.getStartTime();

    if(this.type.equals("Oval")) {
      this.shape = new ProviderOval(s.allDimensions()[0], s.allDimensions()[1]);
    } else if (this.type.equals("Rectangle")) {
      this.shape = new ProviderRectangle(s.allDimensions()[0], s.allDimensions()[1]);
    } else {
      throw new IllegalArgumentException("Shape type does not exist!");
    }
  }

  @Override
  public boolean isActive(int tick) {
    return tick >= this.appears && tick <= this.disappears && this.isVisible;
  }

  @Override
  public void toggleVisible() {
    this.isVisible = ! this.isVisible;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public IShape getShape() {
    return this.shape;
  }

  @Override
  public String getShapeType() {
    return this.type;
  }

  public ISLocation getLocation() {
    return this.pos;
  }

  @Override
  public String[] getPNames() {
    return this.shape.getPNames();
  }

  @Override
  public double[] getPValues() {
    return this.shape.getPValues();
  }

  @Override
  public ISColor getColor() {
    return this.color;
  }

  @Override
  public int getAppears() {
    return this.appears;
  }

  @Override
  public int getDisappears() {
    return this.disappears;
  }

  @Override
  public void moveTo(ISLocation endsAt) {
    this.pos = endsAt;
  }

  @Override
  public void scaleTo(double... endsAt) {
    this.shape.scaleTo(endsAt);
  }

  @Override
  public void changeColor(ISColor endsAt) {
    this.color = endsAt;
  }

  @Override
  public boolean getVisible() {
    return this.isVisible;
  }

  @Override
  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }
}
