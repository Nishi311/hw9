package cs3500.animator.converters.transforms;

import cs3500.animator.converters.ProviderLocation;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeRR;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeWH;
import cs3500.animator.model.interfaces.AnimationComponentInterface;

public class ProviderScaleChange extends ProviderTransformAbstract{

  private double startingX;
  private double startingY;

  private double endingX;
  private double endingY;

  private double xIncrement;
  private double yIncrement;

  public ProviderScaleChange(AnimationComponentInterface amCom){
    super(amCom);

    startingX = (double) amCom.getInitialParameters().get(0);
    startingY = (double) amCom.getInitialParameters().get(1);

    endingX = (double) amCom.getFinalParameters().get(0);
    endingY = (double) amCom.getFinalParameters().get(1);

    xIncrement = (endingX - startingX) / span;
    yIncrement = (endingY - startingY) / span;


    String xParam = "";
    String yParam = "";

    String startingParams = "";
    String endingParams = "";

    if (amCom instanceof ScaleChangeWH){
      xParam = "Width";
      yParam = "Height";
    } else if (amCom instanceof ScaleChangeRR){
      xParam = "X Radius";
      yParam = "Y Radius";
    }

    startingParams = String.format("%s: %.1f, %s, %.1f", xParam, startingX, yParam, startingY);
    endingParams = String.format("%s: %.1f, %s, %.1f", xParam, endingX, yParam, endingY);

    this.transformInfo[1] = "scales";
    transformInfo[2] = startingParams;
    transformInfo[3] = endingParams;
  }

  @Override
  public void transform (int tick){

    int incrementMultiplier = tick-startingTick;

    double targetX = (xIncrement * incrementMultiplier) + startingX;
    double targetY = (yIncrement * incrementMultiplier) + startingY;

    shape.scaleTo(targetX, targetY);
  }

  @Override
  public Double[] getStart(){
    return new Double[]{startingX, startingY};
  }

  @Override
  public Double[] getEnd(){
    return new Double[]{endingX, endingY};
  }
}
