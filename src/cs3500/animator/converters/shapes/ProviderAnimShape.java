package cs3500.animator.converters.shapes;


import cs3500.animator.converters.ProviderColor;
import cs3500.animator.converters.ProviderLocation;

import cs3500.animator.model.concreteclasses.animationcomponenttypes.VisibilityChange;

import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ISColor;
import cs3500.animator.provider.ISLocation;
import cs3500.animator.provider.IShape;

/**
 * Custom implementation of the IAnimShape. Enables us to convert from our
 * shapes into the provider's required classes. Contains the basic shape
 * as well as all animation information necessary to manipulate and display
 * that shape (Color, position, visibility, etc).
 */
public class ProviderAnimShape implements IAnimShape {
  //Shape and Color and Position
  IShape shape;
  ISColor color;
  ISLocation pos;

  String type;
  String name;

  //visibility infromation
  boolean isVisible = true;
  int appears;
  int disappears;

  /**
   * The constructor used to convert one of our ShapeInterfaces into an instance of the
   * provider's IAnimShape. Requires the Visibility Change AnimationComponents detailing when
   * the shape appears and disappears because this is not information usually contained within the
   * ShapeInterface itself.
   *
   * @param s             The shape to be converted.
   * @param appearance    The Visibility Change where "s" is made visible.
   * @param disappearance The Visbility Change were "s" is made invisible.
   */
  public ProviderAnimShape(ShapeInterface s, VisibilityChange appearance,
                           VisibilityChange disappearance) {

    this.appears = appearance.getStartTime();
    this.disappears = disappearance.getStartTime();

    this.pos = new ProviderLocation(s.getPosition());
    this.color = new ProviderColor(s.getColor());
    this.type = s.getShapeType();

    this.name = s.getName();


    if (this.type.equals("Oval")) {
      this.shape = new ProviderOval(s.allDimensions()[0], s.allDimensions()[1]);
      this.type = "oval";
    } else if (this.type.equals("Rectangle")) {
      this.shape = new ProviderRectangle(s.allDimensions()[0], s.allDimensions()[1]);
      this.type = "rectangle";
    } else {
      throw new IllegalArgumentException("Shape type does not exist!");
    }
  }

  /**
   * Copy Constructor. Creates a deep copy of the provided IAnimShape.
   *
   * @param s The IAnimShape to be copied.
   */
  public ProviderAnimShape(IAnimShape s) {
    this.pos = new ProviderLocation(s.getLocation());
    this.color = new ProviderColor(s.getColor());
    this.type = s.getShapeType();
    this.isVisible = s.getVisible();
    this.name = s.getName();
    this.appears = s.getAppears();
    this.disappears = s.getDisappears();
    this.shape = s.getShape().makeCopy();
  }

  @Override
  public boolean isActive(int tick) {
    return tick >= this.appears && tick <= this.disappears && this.isVisible;
  }

  @Override
  public void toggleVisible() {
    this.isVisible = !this.isVisible;
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

  @Override
  public String toString() {
    return String.format("Name: %s\n"
                    + "Type: %s\n"
                    + "%s: %s, %s, Color: %s\n"
                    + "Appears at t=%d\n"
                    + "Disappears at t=%d",
            name, shape.getType(), shape.getReference(), getLocation(),
            shape, color, appears, disappears);
  }
}
