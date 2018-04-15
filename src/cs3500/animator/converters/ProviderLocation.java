package cs3500.animator.converters;

import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.interfaces.Position2DInterface;
import cs3500.animator.provider.ISLocation;

/**
 * Custom implementation of the provider's ISLocation interface. Contains 2D position values
 * necessary to display a shape's position. Basically just wraps around our own Position2D class.
 */
public class ProviderLocation implements ISLocation {
  public Position2DInterface pos;

  /**
   * Conversion Constructor. Allows us to create a provider compatible ISLocation with our own
   * Position2D class.
   *
   * @param pos The colorClass to wrap.
   */
  public ProviderLocation(Position2DInterface pos) {
    this.pos = pos;
  }

  /**
   * Copy Constructor. Necessary for makeCopy() methods.
   *
   * @param toCopy The ISLocation to copy.
   */
  public ProviderLocation(ISLocation toCopy) {
    this.pos = new Position2D((float) toCopy.getX(), (float) toCopy.getY());
  }

  /**
   * The basic constructor needed to create a new ISLocation from scratch.
   *
   * @param x The x value of the position.
   * @param y The y value of the position.
   */
  public ProviderLocation(double x, double y) {
    this.pos = new Position2D((float) x, (float) y);
  }

  @Override
  public double getX() {
    return this.pos.getX();
  }

  @Override
  public double getY() {
    return this.pos.getY();
  }

  @Override
  public void moveTo(ISLocation s) {
    this.pos.setX((float) s.getX());
    this.pos.setY((float) s.getY());
  }

  @Override
  public String toString() {
    return String.format("(%.1f,%.1f)", this.getX(), this.getY());
  }
}
