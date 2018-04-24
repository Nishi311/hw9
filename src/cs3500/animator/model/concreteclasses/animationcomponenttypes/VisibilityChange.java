package cs3500.animator.model.concreteclasses.animationcomponenttypes;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.animator.model.abstracts.AnimationComponentAbstract;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.DurationInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * This animation type allows the user to change the visibility of a designated object.
 * Note that this requires a duration with the same start and end time. Cannot be done over
 * a period of time.
 * INVARIANT: Duration start and end time must be the same.
 */
public class VisibilityChange extends AnimationComponentAbstract {
  private boolean targetVisibility;

  /**
   * Basic VisibilityChange constructor.
   *
   * @param shape            The target shape.
   * @param dur              The time at which the shape will change visibility.
   * @param targetVisibility The end visibility.
   * @throws IllegalArgumentException If the duration given does not have the same start
   *                                  and end time.
   */
  public VisibilityChange(ShapeInterface shape, DurationInterface dur, boolean targetVisibility)
          throws IllegalArgumentException {

    if (dur.getTotalDuration() != 1) {
      throw new IllegalArgumentException("A change in visibility must be done at one time unit");
    }

    this.shape = shape;
    this.dur = dur;

    this.targetVisibility = targetVisibility;
  }

  @Override
  public AnimationComponentInterface copy() {
    return new VisibilityChange(shape.copy(), new Duration(this.dur), this.targetVisibility);
  }

  @Override
  public void executeFull() {
    shape.editParameter(UniversalShapeParameterTypes.VISIBILITY.name(), targetVisibility);
  }

  @Override
  public void executeIncrementInitial() {
    executeFull();
  }

  @Override
  public void executeIncrement() {
    executeFull();
  }

  @Override
  public void executeDecrement() {
    executeFull();
  }

  @Override
  public String getAnimationType() {
    return "Visibility Change";
  }

  @Override
  public List<Object> getInitialParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(true);
    return temp;
  }

  @Override
  public List<Object> getFinalParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.targetVisibility);
    return temp;
  }

  @Override
  public String toString() {
    if (targetVisibility) {
      return "Appears at t=" + dur.getStartTime() + "\n";
    } else {
      return "Disappears at t=" + dur.getEndTime() + "\n";
    }
  }

  @Override
  public String toStringTick(int ticksPerSecond) {
    if (targetVisibility) {
      return "Appears at t=" + (dur.getStartTime() / (double) ticksPerSecond) + "\n";
    } else {
      return "Disappears at t=" + (dur.getEndTime() / (double) ticksPerSecond) + "\n";
    }
  }

  @Override
  public boolean equals(Object check) {
    if (!(check instanceof AnimationComponentInterface)) {
      return false;
    }

    AnimationComponentInterface test = (AnimationComponentInterface) check;

    if (!test.getAnimationType().equals("Visibility Change")) {
      return false;
    }
    //check against shape
    if (test.getTarget().equals(this.shape)) {
      //check start time
      if (test.getStartTime() == (this.dur.getStartTime())) {

        List<Object> testEndList = test.getFinalParameters();
        boolean testEndVisibility = (boolean) testEndList.get(0);
        //check ending visibility
        if (testEndVisibility == this.targetVisibility) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return shape.hashCode() + dur.hashCode() + Objects.hash(targetVisibility);
  }

  @Override
  public int countattributions() {
    return 0;
  }

  @Override
  public String getattributename(int index) {
    return null;
  }

  @Override
  public String attributeValueFrom(int index) {
    return null;
  }

  @Override
  public String attributeValueTo(int index) {
    return null;
  }

  @Override
  public String getSvg(boolean isLoopback, int ticksPerSecond) {
    return null;
  }
}
