package cs3500.animator.model.concreteclasses.animationcomponenttypes;

import java.util.List;
import java.util.ArrayList;

import cs3500.animator.model.abstracts.AnimationComponentAbstract;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.DurationInterface;
import cs3500.animator.model.interfaces.Position2DInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * This animation type allows the user to change the position of a designated shape over a period
 * of time.
 */
public class PositionChange extends AnimationComponentAbstract {
  private float xShiftPerTimeUnit;
  private float yShiftPerTimeUnit;
  private Position2DInterface targetPos;
  private Position2DInterface startingPos;

  /**
   * Basic PositionChange constructor.
   *
   * @param shape       The target shape.
   * @param dur         The duration of the animation.
   * @param startingPos The starting position of the shape.
   * @param targetPos   The ending positon of the shape.
   */
  public PositionChange(ShapeInterface shape, DurationInterface dur, Position2DInterface startingPos,
                        Position2DInterface targetPos) {
    this.shape = shape;
    this.dur = new Duration(dur);
    this.startingPos = new Position2D(startingPos);
    this.targetPos = new Position2D(targetPos);

    int timeToExecute = dur.getTotalDuration();

    if (targetPos.getX() <= startingPos.getX()) {
      xShiftPerTimeUnit = -getShift(startingPos.getX(), targetPos.getX());
    } else {
      xShiftPerTimeUnit = getShift(targetPos.getX(), startingPos.getX());
    }

    if (targetPos.getY() <= startingPos.getY()) {
      yShiftPerTimeUnit = -getShift(startingPos.getY(), targetPos.getY());
    } else {
      yShiftPerTimeUnit = getShift(targetPos.getY(), startingPos.getY());
    }

  } //end constructor

  @Override
  public AnimationComponentInterface copy() {
    return new PositionChange(shape.copy(), new Duration(this.dur),
            new Position2D(this.startingPos), new Position2D(this.targetPos));
  }

  @Override
  public void executeFull() {
    shape.editParameter(UniversalShapeParameterTypes.POSITION.name(), targetPos);
  }

  @Override
  public void executeIncrementInitial() {
    shape.editParameter(UniversalShapeParameterTypes.POSITION.name(), startingPos);
    executeIncrement();
  }

  @Override
  public void executeIncrement() {
    Position2DInterface currentPos = (Position2D) shape.getParameter(
            UniversalShapeParameterTypes.POSITION.name());

    Position2DInterface incrementedPos = new Position2D(currentPos.getX() + xShiftPerTimeUnit,
            currentPos.getY() + yShiftPerTimeUnit);

    shape.editParameter(UniversalShapeParameterTypes.POSITION.name(), incrementedPos);
  } //end executeIncrement()

  @Override
  public String getAnimationType() {
    return "Position Change";
  }

  @Override
  public List<Object> getInitialParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.startingPos);
    return temp;
  }

  @Override
  public List<Object> getFinalParameters() {
    List<Object> temp = new ArrayList<>();
    temp.add(this.targetPos);
    return temp;
  }

  @Override
  public String toString() {
    return "Shape " + shape.getName() + " moves from " + startingPos + " to "
            + targetPos + " from t=" + dur.getStartTime() + " to t=" + dur.getEndTime() + "\n";
  }

  @Override
  public String toStringTick(int ticksPerSecond) {
    return "Shape " + shape.getName() + " moves from " + startingPos + " to "
            + targetPos + " from t=" + (dur.getStartTime() / (double) ticksPerSecond)
            + " to t=" + (dur.getEndTime() / (double) ticksPerSecond) + "\n";
  }

  @Override
  public boolean equals(Object check) {
    if (!(check instanceof AnimationComponentInterface)) {
      return false;
    }

    AnimationComponentInterface test = (AnimationComponentInterface) check;


    if (!test.getAnimationType().equals("Position Change")) {
      return false;
    }
    //check against shape
    if (test.getTarget().equals(this.shape)) {
      //check start time
      if (test.getStartTime() == (this.dur.getStartTime())) {
        //check end time
        if (test.getEndTime() == (this.dur.getEndTime())) {

          List<Object> testStartList = test.getInitialParameters();
          Position2DInterface testStartPos = (Position2D) testStartList.get(0);
          //check staring color
          if (testStartPos.equals(this.startingPos)) {

            List<Object> testEndList = test.getFinalParameters();
            Position2D testEndPos = (Position2D) testEndList.get(0);
            //check ending color
            if (testEndPos.equals(this.targetPos)) {
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
    return shape.hashCode() + dur.hashCode() + startingPos.hashCode() + targetPos.hashCode();
  }


  @Override
  public int countattributions() {
    return 2;
  }

  @Override
  public String getattributename(int index) {
    List<String> attributenames = new ArrayList<>();
    attributenames.add(shape.getSvgShapeCodX());
    attributenames.add(shape.getSvgShapeCodY());

    return "\"" + attributenames.get(index) + "\"";
  }

  @Override
  public String attributeValueFrom(int index) {
    List<Float> attributevaluesfrom = new ArrayList<>();
    attributevaluesfrom.add(startingPos.getX());
    attributevaluesfrom.add(startingPos.getY());
    return "\"" + attributevaluesfrom.get(index) + "\"";
  }

  @Override
  public String attributeValueTo(int index) {
    List<Float> attributevaluesto = new ArrayList<>();
    attributevaluesto.add(targetPos.getX());
    attributevaluesto.add(targetPos.getY());

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

  private float getShift(float start, float end) {
    if (start < 0 && end > 0) {
      return (-start + end) / dur.getTotalDuration();
    } else if (start < 0 && end < 0) {
      return (-start - end) / dur.getTotalDuration();
    } else if (start > 0 && end > 0) {
      return (start - end) / dur.getTotalDuration();
    } else if (start > 0 && end < 0) {
      return (start - end) / dur.getTotalDuration();
    } else {
      return 0;
    }
  }
}
