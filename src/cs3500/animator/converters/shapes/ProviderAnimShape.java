package cs3500.animator.converters.shapes;

import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.provider.IAnimShape;


public class ProviderAnimShape implements IAnimShape {
  /**
   * Constructor that allows the user to create an IAnimShape from a ShapeInterface and
   * the AmComs that represent when that shape appears and disappears.
   * @param s The basic shape.
   * @param appearance The AnimationComponent representing when the shape appears.
   * @param disappearance The AnimationComponent representing when the shape disappears.
   */
  public ProviderAnimShape(ShapeInterface s, AnimationComponentInterface appearance,
                           AnimationComponentInterface disappearance){

  }

  public ProviderAnimShape(IAnimShape s){

  }
}
