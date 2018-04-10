package cs3500.animator.model.concreteclasses.animationcomponenttypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.animator.model.abstracts.AnimationComponentAbstract;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * This animation type allows the user to change the scale of a designated shape over a period
 * of time. Note that this type of scale change is only valid for shapes with two radii.
 * INVARIANT: Cannot have negative target radii.
 */
public class ScaleChangeRR extends AnimationComponentAbstract {

  private float radiusLShiftPerTimeUnit;
  private float radiusSShiftPerTimeUnit;

  private float startingRadiusX;
  private float startingRadiusY;


  private float targetRadiusX;
  private float targetRadiusY;

  /**
   * Basic ScaleChangeRR constructor.
   *
   * @param shape           The target shape.
   * @param dur             The duration of the animation.
   * @param startingRadiusX The starting X radius.
   * @param startingRadiusY The starting Y radius.
   * @param targetRadiusX   The end X radius.
   * @param targetRadiusY   The end Y radius.
   */
  public ScaleChangeRR(ShapeInterface shape, Duration dur, float startingRadiusX,
                       float startingRadiusY, float targetRadiusX, float targetRadiusY) {
    this.shape = shape;
    this.dur = dur;
    int timeToExecute = dur.getTotalDuration();
    this.startingRadiusX = startingRadiusX;
    this.startingRadiusY = startingRadiusY;
    this.targetRadiusX = targetRadiusX;
    this.targetRadiusY = targetRadiusY;


    this.radiusLShiftPerTimeUnit = (targetRadiusX - startingRadiusX) / timeToExecute;
    this.radiusSShiftPerTimeUnit = (targetRadiusY - startingRadiusY) / timeToExecute;
  }

  @Override
  public AnimationComponentInterface copy() {
    return new ScaleChangeRR(shape.copy(), new Duration(this.dur), this.startingRadiusX,
            this.startingRadiusY, this.targetRadiusX, this.targetRadiusY);
  }

  @Override
  public void executeFull() {
    shape.editParameter("xRadius", targetRadiusX);
    shape.editParameter("yRadius", targetRadiusY);
  }

  @Override
  public void executeIncrementInitial() {
    shape.editParameter("xRadius", startingRadiusX);
    shape.editParameter("yRadius", startingRadiusY);
    executeIncrement();
  }

  @Override
  public void executeIncrement() {

    float currentRadiusL = (float) shape.getParameter("xRadius");
    float currentRadiusS = (float) shape.getParameter("yRadius");

    shape.editParameter("xRadius", currentRadiusL + radiusLShiftPerTimeUnit);
    shape.editParameter("yRadius", currentRadiusS + radiusSShiftPerTimeUnit);
  }

  @Override
  public String getAnimationType() {
    return "Scale Change RR";
  }

  @Override
  public List<Object> getInitialParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.startingRadiusX);
    temp.add(this.startingRadiusY);
    return temp;
  }

  @Override
  public List<Object> getFinalParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.targetRadiusX);
    temp.add(this.targetRadiusY);
    return temp;
  }

  @Override
  public String toString() {
    return "Shape " + shape.getName() + " scales from X radius: "
            + startingRadiusX + ", Y radius: " + startingRadiusY
            + " to X radius: " + targetRadiusX + ", Y radius: " + targetRadiusY
            + " from t=" + dur.getStartTime() + " to t=" + dur.getEndTime() + "\n";
  }

  @Override
  public String toStringTick(int ticksPerSecond) {
    return "Shape " + shape.getName() + " scales from X radius: "
            + startingRadiusX + ", Y radius: " + startingRadiusY
            + " to X radius: " + targetRadiusX + ", Y radius: " + targetRadiusY
            + " from t=" + (dur.getStartTime() / (double) ticksPerSecond)
            + " to t=" + (dur.getEndTime() / (double) ticksPerSecond) + "\n";
  }

  @Override
  public boolean equals(Object check) {
    if (!(check instanceof AnimationComponentInterface)) {
      return false;
    }

    AnimationComponentInterface test = (AnimationComponentInterface) check;

    if (!test.getAnimationType().equals("Scale Change RR")) {
      return false;
    }
    //check against shape
    if (test.getTarget().equals(this.shape)) {
      //check start time
      if (test.getStartTime() == (this.dur.getStartTime())) {
        //check end time
        if (test.getEndTime() == (this.dur.getEndTime())) {

          List<Object> testStartList = test.getInitialParameters();
          float testStartX = (float) testStartList.get(0);
          float testStartY = (float) testStartList.get(1);
          //check staring parameters
          if (testStartX == (this.startingRadiusX) && testStartY == (this.startingRadiusY)) {

            List<Object> testEndList = test.getFinalParameters();
            float testEndX = (float) testEndList.get(0);
            float testEndY = (float) testEndList.get(1);
            //check ending parameters
            if (testEndX == this.targetRadiusX && testEndY == this.targetRadiusY) {
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
    return shape.hashCode() + dur.hashCode() + Objects.hash(startingRadiusX + startingRadiusY
            + targetRadiusX + targetRadiusY);
  }

  @Override
  public int countattributions() {
    return 2;
  }

  @Override
  public String getattributename(int index) {
    List<String> attributenames = this.shape.getattributeLennames();

    return "\"" + attributenames.get(index) + "\"";
  }

  @Override
  public String attributeValueFrom(int index) {
    List<Float> scalefrom = new ArrayList<>();
    scalefrom.add(startingRadiusX);
    scalefrom.add(startingRadiusY);
    return "\"" + scalefrom.get(index) + "\"";
  }

  @Override
  public String attributeValueTo(int index) {
    List<Float> scaleto = new ArrayList<>();
    scaleto.add(targetRadiusX);
    scaleto.add(targetRadiusY);
    return "\"" + scaleto.get(index) + "\"";
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
        svgtext = svgtext + "<animate ";
        svgtext = svgtext + "attributeType= \"xml\" ";
        svgtext = svgtext + "begin=\"base.begin+"
                + ((double) dur.getStartTime() / (double) ticksPerSecond) * 1000f
                + unit + "\" dur=\""
                + (((double) dur.getEndTime() / (double) ticksPerSecond)
                - ((double) dur.getStartTime() / (double) ticksPerSecond)) * 1000f
                + unit + "\" attributeName=" + this.getattributename(i)
                + " from=" + this.attributeValueFrom(i) + " to=" + this.attributeValueTo(i)
                + " fill=\"freeze\" />\n";
        svgtext = svgtext + "<animate ";
        svgtext = svgtext + "attributeType= \"xml\" ";
        svgtext = svgtext + "begin=\"base.end\" dur=\"1" + unit + "\" attributeName="
                + this.getattributename(i) + " to=" + this.attributeValueFrom(i)
                + " fill=\"freeze\" />\n";
      } else {
        svgtext += "<animate ";
        svgtext += "attributeType= \"xml\" ";
        svgtext += "begin=" + "\"" + ((double) dur.getStartTime() / (double) ticksPerSecond) * 1000f
                + unit + "\" "
                + "dur=" + "\""
                + duration
                + unit + "\" " + "attributeName="
                + getattributename(i)
                + " from="
                + attributeValueFrom(i) + " to=" + attributeValueTo(i)
                + " fill=" + "\"freeze\" />\n";
      }
    }

    return svgtext;
  }


}