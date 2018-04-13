package cs3500.animator.converters.transforms;

import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeRR;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeWH;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ITransformation;

public class ProviderScaleChange extends ProviderTransformAbstract {

  private double startingX;
  private double startingY;

  private double endingX;
  private double endingY;

  private double xIncrement;
  private double yIncrement;

  public ProviderScaleChange(AnimationComponentInterface amCom, IAnimShape shape) {
    super(amCom, shape);

    this.startingX = (double) amCom.getInitialParameters().get(0);
    this.startingY = (double) amCom.getInitialParameters().get(1);

    this.endingX = (double) amCom.getFinalParameters().get(0);
    this.endingY = (double) amCom.getFinalParameters().get(1);

    this.xIncrement = (endingX - startingX) / span;
    this.yIncrement = (endingY - startingY) / span;


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
    }

    startingParams = String.format("%s: %.1f, %s, %.1f", xParam, startingX, yParam, startingY);
    endingParams = String.format("%s: %.1f, %s, %.1f", xParam, endingX, yParam, endingY);

    this.transformInfo[1] = "scales";
    this.transformInfo[2] = startingParams;
    this.transformInfo[3] = endingParams;
  }

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
