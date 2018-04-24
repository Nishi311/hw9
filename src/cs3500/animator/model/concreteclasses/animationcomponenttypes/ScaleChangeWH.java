package cs3500.animator.model.concreteclasses.animationcomponenttypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.animator.model.abstracts.AnimationComponentAbstract;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.DurationInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * This animation type allows the user to change the scale of a designated shape over a period
 * of time. Note that this type of scale change is only valid for shapes with width and height.
 * INVARIANT: Cannot have negative width or height.
 */
public class ScaleChangeWH extends AnimationComponentAbstract {

  private float widthShiftPerTimeUnit;
  private float heightShiftPerTimeUnit;

  private float startingWidth;
  private float startingHeight;

  private float targetWidth;
  private float targetHeight;

  /**
   * Basic ScaleChangeWH constructor.
   *
   * @param shape          The target shape.
   * @param dur            The duration of the animation.
   * @param staringWidth   The starting width of the shape.
   * @param startingHeight The starting height of the shape.
   * @param widthTarget    The end width;
   * @param heightTarget   The end height;
   */
  public ScaleChangeWH(ShapeInterface shape,
                       DurationInterface dur,
                       float staringWidth,
                       float startingHeight,
                       float widthTarget,
                       float heightTarget) {
    this.dur = dur;
    this.shape = shape;

    this.startingWidth = staringWidth;
    this.startingHeight = startingHeight;

    this.targetWidth = widthTarget;
    this.targetHeight = heightTarget;
    int timeToExecute = dur.getTotalDuration();

    widthShiftPerTimeUnit = (widthTarget - staringWidth) / timeToExecute;
    heightShiftPerTimeUnit = (heightTarget - startingHeight) / timeToExecute;
  }

  @Override
  public AnimationComponentInterface copy() {
    return new ScaleChangeWH(shape.copy(), new Duration(this.dur), this.startingWidth,
            this.startingHeight, this.targetWidth, this.targetHeight);
  }

  @Override
  public void executeFull() {
    shape.editParameter("width", targetWidth);
    shape.editParameter("height", targetHeight);
  }

  @Override
  public void executeIncrementInitial() {
    shape.editParameter("width", startingWidth);
    shape.editParameter("height", startingHeight);

    executeIncrement();
  }

  @Override
  public void executeIncrement() {

    float currentWidth = (float) shape.getParameter("width");
    float currentHeight = (float) shape.getParameter("height");

    shape.editParameter("width", currentWidth + widthShiftPerTimeUnit);
    shape.editParameter("height", currentHeight + heightShiftPerTimeUnit);
  }

  @Override
  public void executeDecrement() {

    float currentWidth = (float) shape.getParameter("width");
    float currentHeight = (float) shape.getParameter("height");

    shape.editParameter("width", currentWidth - widthShiftPerTimeUnit);
    shape.editParameter("height", currentHeight - heightShiftPerTimeUnit);
  }

  @Override
  public String getAnimationType() {
    return "Scale Change WH";
  }

  @Override
  public List<Object> getInitialParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.startingWidth);
    temp.add(this.startingHeight);
    return temp;
  }

  @Override
  public List<Object> getFinalParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.targetWidth);
    temp.add(this.targetHeight);
    return temp;
  }

  @Override
  public String toString() {
    return "Shape " + shape.getName() + " scales from Width: "
            + startingWidth
            + ", Height: " + startingHeight
            + " to Width: " + targetWidth
            + ", Height: " + targetHeight + " from t=" + dur.getStartTime() + " to t="
            + dur.getEndTime() + "\n";
  }

  @Override
  public String toStringTick(int ticksPerSecond) {
    return "Shape " + shape.getName() + " scales from Width: "
            + startingWidth
            + ", Height: " + startingHeight
            + " to Width: " + targetWidth
            + ", Height: " + targetHeight + " from t="
            + (dur.getStartTime() / (double) ticksPerSecond)
            + " to t="
            + (dur.getEndTime() / (double) ticksPerSecond) + "\n";
  }

  @Override
  public boolean equals(Object check) {
    if (!(check instanceof AnimationComponentInterface)) {
      return false;
    }

    AnimationComponentInterface test = (AnimationComponentInterface) check;

    if (!test.getAnimationType().equals("Scale Change WH")) {
      return false;
    }
    //check against shape
    if (test.getTarget().equals(this.shape)) {
      //check start time
      if (test.getStartTime() == (this.dur.getStartTime())) {
        //check end time
        if (test.getEndTime() == (this.dur.getEndTime())) {

          List<Object> testStartList = test.getInitialParameters();
          float testStartWidth = (float) testStartList.get(0);
          float testStartHeight = (float) testStartList.get(1);
          //check staring parameters
          if (testStartWidth == (this.startingWidth) && testStartHeight == (this.startingHeight)) {

            List<Object> testEndList = test.getFinalParameters();
            float testEndWidth = (float) testEndList.get(0);
            float testEndHeight = (float) testEndList.get(1);
            //check ending parameters
            if (testEndWidth == this.targetWidth && testEndHeight == this.targetHeight) {
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
    return shape.hashCode() + dur.hashCode() + Objects.hash(startingWidth + startingHeight
            + targetWidth + targetHeight);
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
    scalefrom.add(startingWidth);
    scalefrom.add(startingHeight);
    return "\"" + scalefrom.get(index) + "\"";
  }

  @Override
  public String attributeValueTo(int index) {
    List<Float> scaleto = new ArrayList<>();
    scaleto.add(targetWidth);
    scaleto.add(targetHeight);
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