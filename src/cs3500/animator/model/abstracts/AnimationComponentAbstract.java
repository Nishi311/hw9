package cs3500.animator.model.abstracts;

import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * Provides some implementation for basic AnimationComponent methods, mostly the retrieval ones.
 * Declares a few protected parameters "shape" which is the target of the animation and "dur" which
 * is the duration of the animation. All other parameters will be stored in the actual
 * implementation of an AnimationComponentAbstract extending class.
 */
public abstract class AnimationComponentAbstract implements AnimationComponentInterface {
  protected ShapeInterface shape;
  protected Duration dur;

  @Override
  public void setShape(ShapeInterface newShape) throws IllegalArgumentException {

    if (!newShape.getShapeType().equals(shape.getShapeType())) {
      throw new IllegalArgumentException("Must be the same type of shape");
    }
    shape = newShape;
  }

  @Override
  public String getTargetName() {
    return shape.getName();
  }

  @Override
  public ShapeInterface getTarget() {
    return shape;
  }

  @Override
  public int getStartTime() {
    return dur.getStartTime();
  }

  @Override
  public int getEndTime() {
    return dur.getEndTime();
  }

  @Override
  public int compareTo(AnimationComponentInterface test) {

    if (getStartTime() < test.getStartTime()) {
      return -1;
    } else if (getStartTime() > test.getStartTime()) {
      return 1;
    } else if (getEndTime() < test.getEndTime()) {
      return -1;
    } else if (getEndTime() > test.getEndTime()) {
      return 1;
    }
    return 0;
  }
}

