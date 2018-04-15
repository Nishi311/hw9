package cs3500.animator.converters.transforms;

import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeRR;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeWH;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ITransformation;

/**
 * Custom implementation of the Provider's ITransform interface. Manipulates the target shape's
 * scale values over a given period of time.
 */
public class ProviderScaleChange extends ProviderTransformAbstract {

  private double startingX;
  private double startingY;

  private double endingX;
  private double endingY;

  private double xIncrement;
  private double yIncrement;

  /**
   * Converting constructor. Allows us to convert between our equivalent Scale Change animation
   * components (we have two) and the provider version of a position change.
   *
   * @param amCom The animation component to convert from. MUST be either a ScaleChangeWW or
   *              a ScaleChangeWH.
   * @param shape The shape that the ProviderColorChange will be acting upon.
   * @throws IllegalArgumentException if the amCom is not a ScaleChangeWH or ScaleChangeRR.
   */
  public ProviderScaleChange(AnimationComponentInterface amCom, IAnimShape shape) {
    super(amCom, shape);
    String xParam = "";
    String yParam = "";

    String startingParams = "";
    String endingParams = "";

    if (amCom instanceof ScaleChangeWH) {
      xParam = "Width";
      yParam = "Height";
    } else if (amCom instanceof ScaleChangeRR) {
      xParam = "X Radius";
      yParam = "Y Radius";
    } else {
      throw new IllegalArgumentException("Must provide either a ScaleChangeWH or a ScaleChangeRR");
    }

    startingParams = String.format("%s: %.1f, %s, %.1f", xParam, startingX, yParam, startingY);
    endingParams = String.format("%s: %.1f, %s, %.1f", xParam, endingX, yParam, endingY);

    this.transformInfo[1] = "scales";
    this.transformInfo[2] = startingParams;
    this.transformInfo[3] = endingParams;


    this.startingX = (double) ((float) amCom.getInitialParameters().get(0));
    this.startingY = (double) ((float) amCom.getInitialParameters().get(1));

    this.endingX = (double) ((float) amCom.getFinalParameters().get(0));
    this.endingY = (double) ((float) amCom.getFinalParameters().get(1));

    this.xIncrement = (endingX - startingX) / span;
    this.yIncrement = (endingY - startingY) / span;
  }

  /**
   * More basic constructor. Takes the shape and parameters necessary to create a
   * ProviderScaleChange from scratch. Only really used in the makeCopy() method.
   *
   * @param shape        The shape that will be acted upon.
   * @param startingX    The starting horizontal value of the shape.
   * @param startingY    The ending vertical value of the shape.
   * @param startingTick The tick when the shape should start moving.
   * @param endingTick   The tick when the shape should finish moving.
   */
  public ProviderScaleChange(ProviderAnimShape shape, double startingX, double startingY,
                             double endingX, double endingY, int startingTick, int endingTick) {
    super(shape, startingTick, endingTick);

    this.startingX = startingX;
    this.startingY = startingY;

    this.endingX = endingX;
    this.endingY = endingY;

    this.xIncrement = (endingX - startingX) / span;
    this.yIncrement = (endingY - startingY) / span;


    String xParam = "";
    String yParam = "";

    String startingParams = "";
    String endingParams = "";

    if (shape.getShapeType().equals("Rectangle")) {
      xParam = "Width";
      yParam = "Height";
    } else if (shape.getShapeType().equals("Oval")) {
      xParam = "X Radius";
      yParam = "Y Radius";
    }

    startingParams = String.format("%s: %.1f, %s, %.1f", xParam, startingX, yParam, startingY);
    endingParams = String.format("%s: %.1f, %s, %.1f", xParam, endingX, yParam, endingY);

    this.transformInfo[1] = "scales";
    this.transformInfo[2] = startingParams;
    this.transformInfo[3] = endingParams;
  }

  @Override
  public ITransformation makeCopy() {
    return new ProviderScaleChange(new ProviderAnimShape(this.shape),
            this.startingX, this.startingY, this.endingX, this.endingY,
            this.startingTick, this.endingTick);
  }


  @Override
  public void transform(int tick) {

    int incrementMultiplier = tick - startingTick;

    double targetX = (xIncrement * incrementMultiplier) + startingX;
    double targetY = (yIncrement * incrementMultiplier) + startingY;

    shape.scaleTo(targetX, targetY);
  }

  @Override
  public Double[] getStart() {
    return new Double[]{startingX, startingY};
  }

  @Override
  public Double[] getEnd() {
    return new Double[]{endingX, endingY};
  }
}
