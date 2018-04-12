package cs3500.animator.provider.view;

import java.util.List;

import cs3500.animator.animshape.IAnimShape;

public abstract class AView implements IViewable {

  protected List<IAnimShape> shapes;
  protected int speed;
  //protected List<ITransformation> transformations;

  /**
   * A generic view, an abstraction of all that IViewables have in common.
   * @param speed   The speed at which the animation will run, in frames per second
   */
  public AView(int speed) { //, List<ITransformation> t) {
    if(speed < 1) {
      throw new IllegalStateException("The speed must be positive.");
    }
    this.speed = speed;
  }

  /**
   * Set the list of shapes for the view to display.
   */
  @Override
  public void setShapes(List<IAnimShape> shapeList) {
    if(shapeList == null) {
      throw new IllegalStateException("The list of shapes cannot be null.");
    }
    this.shapes = shapeList;
  }
}
