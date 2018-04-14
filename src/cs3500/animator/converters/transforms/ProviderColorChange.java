package cs3500.animator.converters.transforms;

import cs3500.animator.converters.ProviderColor;
import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ITransformation;

public class ProviderColorChange extends ProviderTransformAbstract {

  private ColorClass startingColor;
  private ColorClass endingColor;

  private double redIncrement;
  private double greenIncrement;
  private double blueIncrement;

  public ProviderColorChange(AnimationComponentInterface amCom, IAnimShape shape){
    super(amCom, shape);

    this.startingColor = (ColorClass) amCom.getInitialParameters().get(0);
    this.endingColor = (ColorClass) amCom.getFinalParameters().get(0);

    this.transformInfo[1] = "changes color";
    transformInfo[2] = startingColor.toString();
    transformInfo[3] = endingColor.toString();

    redIncrement = (double) ((endingColor.getRed()-startingColor.getRed()) / span);
    greenIncrement = (double) ((endingColor.getGreen()-startingColor.getGreen()) / span);
    blueIncrement = (double) ((endingColor.getBlue()-startingColor.getBlue()) / span);
  }

  public ProviderColorChange(ProviderAnimShape shape, ColorClass startingColor, ColorClass endingColor,
                             int startingTick, int endingTick){
    super(shape, startingTick, endingTick);

    this.startingColor = new ColorClass(startingColor);
    this.endingColor = new ColorClass(endingColor);

    this.transformInfo[1] = "changes color";
    transformInfo[2] = startingColor.toString();
    transformInfo[3] = endingColor.toString();

    redIncrement = (double) ((endingColor.getRed()-startingColor.getRed()) / span);
    greenIncrement = (double) ((endingColor.getGreen()-startingColor.getGreen()) / span);
    blueIncrement = (double) ((endingColor.getBlue()-startingColor.getBlue()) / span);
  }

  @Override
  public ITransformation makeCopy(){
    return new ProviderColorChange(new ProviderAnimShape(this.shape),
            new ColorClass(this.startingColor), new ColorClass(this.endingColor),
            this.startingTick, this.endingTick);
  }

  @Override
  public void transform (int tick){

    int incrementMultiplier = tick-startingTick;

    double targetRed = (redIncrement * incrementMultiplier) + startingColor.getRed();
    double targetGreen = (greenIncrement * incrementMultiplier) + startingColor.getGreen();
    double targetBlue = (blueIncrement * incrementMultiplier) + startingColor.getBlue();

    shape.changeColor(new ProviderColor(targetRed, targetGreen, targetBlue));
  }

  @Override
  public Double[] getStart(){
    return new Double[]{(double) startingColor.getRed(),
            (double)startingColor.getGreen(),
            (double)startingColor.getBlue()};
  }

  @Override
  public Double[] getEnd(){
    return new Double[]{(double) endingColor.getRed(),
            (double)endingColor.getGreen(),
            (double)endingColor.getBlue()};
  }

}
