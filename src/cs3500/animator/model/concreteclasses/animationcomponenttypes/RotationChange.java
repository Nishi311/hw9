package cs3500.animator.model.concreteclasses.animationcomponenttypes;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.abstracts.AnimationComponentAbstract;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.interfaces.AnimationComponentInterface;

import cs3500.animator.model.interfaces.DurationInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

public class RotationChange extends AnimationComponentAbstract {
  private float rotationPerTimeUnit;

  private float startingOrient;
  private float targetOrient;

  /**
   * Basic RotationChange constructor.
   *
   * @param shape          The target shape.
   * @param dur            The duration of the animation
   * @param startingOrient The staring orientation of the shape.
   * @param targetOrient   The ending orientation of the shape.
   */
  public RotationChange(ShapeInterface shape, DurationInterface dur, float startingOrient,
                        float targetOrient) {

    this.shape = shape;
    this.dur = new Duration(dur);
    this.startingOrient = startingOrient;
    this.targetOrient = targetOrient;

    int timeToExecute = dur.getTotalDuration();

    rotationPerTimeUnit = (targetOrient - startingOrient) / timeToExecute;
  }

  @Override
  public AnimationComponentInterface copy() {
    return new RotationChange(this.shape.copy(), new Duration(this.dur),
            startingOrient, targetOrient);
  }

  @Override
  public void executeFull() {
    shape.editParameter(UniversalShapeParameterTypes.ORIENTATION.name(), targetOrient);
  }

  @Override
  public void executeIncrementInitial() {
    shape.editParameter(UniversalShapeParameterTypes.ORIENTATION.name(), startingOrient);
    executeIncrement();
  }

  @Override
  public void executeIncrement() {
    float currentOrient = shape.getOrientation();
    float newOrient = currentOrient + rotationPerTimeUnit;

    if (newOrient < -360) {
      newOrient = -360;
    } else if (newOrient > 360) {
      newOrient = 360;
    }

    shape.editParameter(UniversalShapeParameterTypes.ORIENTATION.name(), newOrient);
  }

  @Override
  public String getAnimationType() {
    return "Rotation Change";
  }

  @Override
  public List<Object> getInitialParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.startingOrient);
    return temp;
  }

  @Override
  public List<Object> getFinalParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.targetOrient);
    return temp;
  }

  @Override
  public String toString() {
    return "Shape " + shape.getName() + " rotates from " + startingOrient
            + " to " + targetOrient + " from t=" + dur.getStartTime() + " to t=" + dur.getEndTime()
            + "\n";
  }

  @Override
  public boolean equals(Object check) {
    if (!(check instanceof AnimationComponentInterface)) {
      return false;
    }

    AnimationComponentInterface test = (AnimationComponentInterface) check;

    if (!test.getAnimationType().equals("Rotation Change")) {
      return false;
    }
    //check against shape
    if (test.getTarget().equals(this.shape)) {
      //check start time
      if (test.getStartTime() == (this.dur.getStartTime())) {
        //check end time
        if (test.getEndTime() == (this.dur.getEndTime())) {

          List<Object> testStartList = test.getInitialParameters();
          double testOrient = (float) testStartList.get(0);
          //check staring color
          if (testOrient == this.startingOrient) {

            List<Object> testEndList = test.getFinalParameters();
            testOrient = (float) testEndList.get(0);
            //check ending color
            if (testOrient == this.targetOrient) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return shape.hashCode() + dur.hashCode() + ((Float) startingOrient).hashCode() +
            ((Float) targetOrient).hashCode();
  }

  @Override
  public String toStringTick(int ticksPerSecond) {
    return "TO DO";
  }

  @Override
  public int countattributions() {
    return 0;
  }

  @Override
  public String getattributename(int index) {
    return "TO DO";
  }

  @Override
  public String attributeValueFrom(int index) {
    return "TO DO";
  }

  @Override
  public String attributeValueTo(int index) {
    return "TO DO";
  }

  @Override
  public String getSvg(boolean isLoopback, int ticksPerSecond) {
    return "TO DO";
  }
}
