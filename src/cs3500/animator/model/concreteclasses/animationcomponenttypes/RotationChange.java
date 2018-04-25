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
  public void executeDecrement() {
    float currentOrient = shape.getOrientation();
    float newOrient = currentOrient - rotationPerTimeUnit;

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
          //check staring orientation
          if (testOrient == this.startingOrient) {

            List<Object> testEndList = test.getFinalParameters();
            testOrient = (float) testEndList.get(0);
            //check ending orientation
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
    return 1;
  }

  @Override
  public String getattributename(int index) {
    List<String> attributenames = new ArrayList<>();
    attributenames.add("transform");

    return "\"" + attributenames.get(index) + "\"";
  }

  @Override
  public String attributeValueFrom(int index) {
    List<String> attributevaluesfrom = new ArrayList<>();
    float center_x = shape.getPosition().getX();
    float center_y = shape.getPosition().getY();
    float width = 0;
    float height = 0;
    if (shape.getShapeType().equals("Rectangle")) {
      width = (float) shape.getParameter("width");
      height = (float) shape.getParameter("height");
    } else {
      width = (float) shape.getParameter("xRadius")/2;
      height = (float) shape.getParameter("yRadius")/2;
    }
    float temp_x = center_x + width/2;
    float temp_y = center_y + height/2;
    attributevaluesfrom.add(Float.toString(startingOrient) + " " + temp_x + " " + temp_y);
    return "\"" + attributevaluesfrom.get(index) + "\"";
  }

  @Override
  public String attributeValueTo(int index) {
    List<String> attributevaluesto = new ArrayList<>();
    float center_x = shape.getPosition().getX();
    float center_y = shape.getPosition().getY();
    float width = 0;
    float height = 0;
    if (shape.getShapeType().equals("Rectangle")) {
      width = (float) shape.getParameter("width");
      height = (float) shape.getParameter("height");
    } else {
      width = (float) shape.getParameter("xRadius")/2;
      height = (float) shape.getParameter("yRadius")/2;
    }
    float temp_x = center_x + width/2;
    float temp_y = center_y + height/2;
    attributevaluesto.add(Float.toString(targetOrient) + " " + temp_x + " " + temp_y);

    return "\"" + attributevaluesto.get(index) + "\"";
  }

  @Override
  public String getSvg(boolean isLoopback, int ticksPerSecond) {

    String unit = "ms";
    String svgtext = "";
    for (int i = 0; i < this.countattributions(); i++) {
      double duration = (((double) dur.getEndTime()
              / (double) ticksPerSecond) - ((double) dur.getStartTime()
              / (double) ticksPerSecond)) * 1000;

      if (isLoopback) {
        svgtext = svgtext + "<animateTransform ";
        svgtext = svgtext + "attributeType= \"xml\" ";
        svgtext = svgtext + "begin=\"base.begin+"
                + ((double) dur.getStartTime() / (double) ticksPerSecond) * 1000
                + unit + "\" dur=\""
                + (((double) dur.getEndTime() / (double) ticksPerSecond)
                - ((double) dur.getStartTime() / (double) ticksPerSecond)) * 1000
                + unit
                + "\" "
                + "type="
                + "\""
                + "rotate"
                + "\""
                + " attributeName="
                + getattributename(i)
                + " from="
                + attributeValueFrom(i)
                + " to="
                + attributeValueTo(i)
                + " fill=" + "\"freeze\" />\n";
        svgtext = svgtext + "<animate ";
        svgtext = svgtext + "attributeType= \"xml\" ";
        svgtext = svgtext + "begin=\"base.end\" dur=\"1" + unit + "\" attributeName="
                + this.getattributename(i) + " to=" + this.attributeValueFrom(i)
                + " fill=\"freeze\" />\n";
      } else {
        svgtext += "<animateTransform ";
        svgtext += "attributeType= \"xml\" ";
        svgtext += "begin=" + "\"" + ((double) dur.getStartTime() / (double) ticksPerSecond) * 1000
                + unit + "\" "
                + "type="
                + "\""
                + "rotate"
                + "\""
                + " dur=" + "\""
                + duration
                + unit + "\" " + "attributeName="
                + getattributename(i)
                + " from="
                + attributeValueFrom(i)
                + " to="
                + attributeValueTo(i)
                + " fill=" + "\"freeze\" />\n";
      }
    }

    System.out.println("svgtext" + svgtext);

    return svgtext;
  }
}
