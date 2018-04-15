package cs3500.animator.converters.transforms;

import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ITransformation;

/**
 * Abstract class that implements some basic, communal functions for all ITransform implementations.
 * This includes active checks, tick retrievals, information retrievals and so on.
 */
public abstract class ProviderTransformAbstract implements ITransformation {

  protected IAnimShape shape;
  protected String[] transformInfo = new String[6];
  protected int span;


  private String shapeName;
  protected int startingTick;
  protected int endingTick;

  /**
   * Conversion constructor. Used to take universal information about an animation component (think
   * shape name, starting ending ticks) and sets it up for an extending class to build upon with
   * more specific information (color / position / scale change values).
   *
   * @param amCom The AnimationComponentInterface to be converted.
   * @param shape The IAnimShape that the Transform will act on.
   */
  public ProviderTransformAbstract(AnimationComponentInterface amCom, IAnimShape shape) {
    this.shape = shape;
    this.shapeName = amCom.getTargetName();
    this.startingTick = amCom.getStartTime();
    this.endingTick = amCom.getEndTime();
    this.span = endingTick - startingTick;

    this.transformInfo[0] = this.shapeName;

    //transform[1],[2], and [3] will be filled in by individual implementation
    this.transformInfo[4] = Integer.toString(startingTick);
    this.transformInfo[5] = Integer.toString(endingTick);
  }

  /**
   * A more basic constructor used to help build a Transform from scratch. Used in the copy method
   * implementations.
   *
   * @param shape        The shape to be acted upon.
   * @param startingTick The starting tick of the transform.
   * @param endingTick   The ending tick of the transform.
   */
  public ProviderTransformAbstract(ProviderAnimShape shape, int startingTick, int endingTick) {
    this.shape = shape;
    this.shapeName = shape.getName();
    this.startingTick = startingTick;
    this.endingTick = endingTick;
    this.span = endingTick - startingTick;

    this.transformInfo[0] = this.shapeName;

    //transform[1],[2], and [3] will be filled in by individual implementation
    this.transformInfo[4] = Integer.toString(startingTick);
    this.transformInfo[5] = Integer.toString(endingTick);
  }

  @Override
  public boolean isActive(int tick) {
    if (tick >= startingTick) {
      if (tick < endingTick) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getShapeName() {
    return shapeName;
  }

  @Override
  public void setShape(IAnimShape shape) {
    this.shape = shape;
  }

  @Override
  public int getStartTick() {
    return startingTick;
  }

  @Override
  public int getEndTick() {
    return endingTick;
  }

  @Override
  public boolean noConflict(ITransformation other) {
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public String[] buildStrings() {
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public String[] getInfo() {
    return transformInfo;
  }

}


