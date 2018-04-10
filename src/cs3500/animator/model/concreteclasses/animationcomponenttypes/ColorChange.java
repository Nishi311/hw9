package cs3500.animator.model.concreteclasses.animationcomponenttypes;

import java.util.List;
import java.util.ArrayList;

import cs3500.animator.model.abstracts.AnimationComponentAbstract;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * This animation type allows the user to Change the color a designated shape over a period
 * of time.
 */
public class ColorChange extends AnimationComponentAbstract {

  private float redShiftPerTimeUnit;
  private float greenShiftPerTimeUnit;
  private float blueShiftPerTimeUnit;
  private ColorClass targetColor;
  private ColorClass startingColor;

  /**
   * Basic ColorChange constructor.
   *
   * @param shape         The target shape.
   * @param dur           The duration of the animation
   * @param startingColor The staring color of the shape.
   * @param targetColor   The ending color of the shape.
   */
  public ColorChange(ShapeInterface shape, Duration dur, ColorClass startingColor,
                     ColorClass targetColor) {

    this.shape = shape;
    this.dur = new Duration(dur);
    this.startingColor = new ColorClass(startingColor);
    this.targetColor = new ColorClass(targetColor);

    int timeToExecute = dur.getTotalDuration();

    redShiftPerTimeUnit = (targetColor.getRed() - startingColor.getRed()) / timeToExecute;
    greenShiftPerTimeUnit = (targetColor.getGreen() - startingColor.getGreen()) / timeToExecute;
    blueShiftPerTimeUnit = (targetColor.getBlue() - startingColor.getBlue()) / timeToExecute;
  }

  @Override
  public AnimationComponentInterface copy() {
    return new ColorChange(this.shape.copy(), new Duration(this.dur),
            new ColorClass(this.startingColor), new ColorClass(this.targetColor));
  }

  @Override
  public void executeFull() {
    shape.editParameter(UniversalShapeParameterTypes.COLOR.name(), targetColor);
  }

  @Override
  public void executeIncrementInitial() {
    shape.editParameter(UniversalShapeParameterTypes.COLOR.name(), startingColor);
    executeIncrement();
  }

  @Override
  public void executeIncrement() {

    ColorClass currentColor = (ColorClass) shape.getParameter(
            UniversalShapeParameterTypes.COLOR.name());

    float newRed = currentColor.getRed() + redShiftPerTimeUnit;
    float newGreen = currentColor.getGreen() + greenShiftPerTimeUnit;
    float newBlue = currentColor.getBlue() + blueShiftPerTimeUnit;

    //clamps any overflow
    if (newRed < 0) {
      newRed = 0;
    } else if (newRed > 1) {
      newRed = 1;
    }

    //clamps any overflow
    if (newGreen < 0) {
      newGreen = 0;
    } else if (newGreen > 1) {
      newGreen = 1;
    }

    //clamps any overflow
    if (newBlue < 0) {
      newBlue = 0;
    } else if (newBlue > 1) {
      newBlue = 1;
    }

    ColorClass incrementedColor = new ColorClass(newRed, newGreen, newBlue);

    shape.editParameter(UniversalShapeParameterTypes.COLOR.name(), incrementedColor);
  }

  @Override
  public String getAnimationType() {
    return "Color Change";
  }

  @Override
  public List<Object> getInitialParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.startingColor);
    return temp;
  }

  @Override
  public List<Object> getFinalParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.targetColor);
    return temp;
  }

  @Override
  public String toString() {
    return "Shape " + shape.getName() + " changes color from " + startingColor
            + " to " + targetColor + " from t=" + dur.getStartTime() + " to t=" + dur.getEndTime()
            + "\n";
  }

  @Override
  public boolean equals(Object check) {
    if (!(check instanceof AnimationComponentInterface)) {
      return false;
    }

    AnimationComponentInterface test = (AnimationComponentInterface) check;

    if (!test.getAnimationType().equals("Color Change")) {
      return false;
    }
    //check against shape
    if (test.getTarget().equals(this.shape)) {
      //check start time
      if (test.getStartTime() == (this.dur.getStartTime())) {
        //check end time
        if (test.getEndTime() == (this.dur.getEndTime())) {

          List<Object> testStartList = test.getInitialParameters();
          ColorClass testStartColor = (ColorClass) testStartList.get(0);
          //check staring color
          if (testStartColor.equals(this.startingColor)) {

            List<Object> testEndList = test.getFinalParameters();
            ColorClass testEndColor = (ColorClass) testEndList.get(0);
            //check ending color
            if (testEndColor.equals(this.targetColor)) {
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
    return shape.hashCode() + dur.hashCode() + startingColor.hashCode() + targetColor.hashCode();
  }

  @Override
  public String toStringTick(int ticksPerSecond) {
    return "Shape " + shape.getName() + " changes color from " + startingColor
            + " to " + targetColor
            + " from t=" + (dur.getStartTime() / (double) ticksPerSecond)
            + " to t=" + (dur.getEndTime() / (double) ticksPerSecond) + "\n";
  }

  @Override
  public int countattributions() {
    return 1;
  }

  @Override
  public String getattributename(int index) {
    return "\"" + "fill" + "\"";
  }

  @Override
  public String attributeValueFrom(int index) {
    List<String> attributevaluesfrom = new ArrayList<>();
    attributevaluesfrom.add(startingColor.tranferrbg());
    return "\"" + "rgb" + attributevaluesfrom.get(index) + "\"";
  }

  @Override
  public String attributeValueTo(int index) {
    List<String> attributevaluesto = new ArrayList<>();
    attributevaluesto.add(targetColor.tranferrbg());

    return "\"" + "rgb" + attributevaluesto.get(index) + "\"";
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
                + ((double) dur.getStartTime() / (double) ticksPerSecond) * 1000
                + unit + "\" dur=\""
                + (((double) dur.getEndTime() / (double) ticksPerSecond)
                - ((double) dur.getStartTime() / (double) ticksPerSecond)) * 1000
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
        svgtext += "begin=" + "\"" + ((double) dur.getStartTime() / (double) ticksPerSecond) * 1000
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
