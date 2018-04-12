package cs3500.animator.provider.view;

import java.util.List;

import cs3500.animator.provider.ITransformation;

public abstract class APrintableView extends AView implements IPrintableView {

  protected final Appendable output;
  protected List<ITransformation> transformations;

  /**
   * Build a Text View object, which describes the shapes and transformations as text,
   * allowing the viewer to see when shapes exist and how they change.
   * @param output    The appendable object to put the results in
   */
  public APrintableView(int speed, Appendable output) {
    super(speed);
    if(output == null) {
      throw new IllegalStateException("The output file cannot be null.");
    }
    this.output = output;
  }

  /**
   * Get the list of shapes for the view to display.
   */
  @Override
  public void setTransformations(List<Transformation> transformationList) {
    if(transformationList == null) {
      throw new IllegalStateException("The list of transformations cannot be null.");
    }
    this.transformations = transformationList;
  }

  /**
   * Set the speed of the animation.
   */
  @Override
  public void setSpeed(int speed) {
    if (speed < 0) {
      throw new IllegalArgumentException("The speed cannot be negative.");
    }
    this.speed = speed;
  }
}
