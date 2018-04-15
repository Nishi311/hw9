package cs3500.animator.converters.transforms;

import cs3500.animator.converters.ProviderColor;
import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ColorChange;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ITransformation;

/**
 * Custom implementation of the Provider's ITransform interface. Manipulates the target shape's
 * color values over a given period of time.
 */
public class ProviderColorChange extends ProviderTransformAbstract {

  private ColorClass startingColor;
  private ColorClass endingColor;

  private double redIncrement;
  private double greenIncrement;
  private double blueIncrement;

  /**
   * Converting constructor. Allows us to convert between our equivalent ColorChange animation
   * component and the provider version of a color change.
   *
   * @param amCom The ColorChange animation component to convert from.
   * @param shape The shape that the ProviderColorChange will be acting upon.
   */
  public ProviderColorChange(ColorChange amCom, IAnimShape shape) {
    super(amCom, shape);

    this.startingColor = (ColorClass) amCom.getInitialParameters().get(0);
    this.endingColor = (ColorClass) amCom.getFinalParameters().get(0);

    this.transformInfo[1] = "changes color";
    transformInfo[2] = startingColor.toString();
    transformInfo[3] = endingColor.toString();

    redIncrement = (double) ((endingColor.getRed() - startingColor.getRed()) / span);
    greenIncrement = (double) ((endingColor.getGreen() - startingColor.getGreen()) / span);
    blueIncrement = (double) ((endingColor.getBlue() - startingColor.getBlue()) / span);
  }

  /**
   * More basic constructor. Takes the shape and parameters necessary to create a
   * ProviderColorChange from scratch. Only really used in the makeCopy() method.
   *
   * @param shape         The shape that will be acted upon.
   * @param startingColor The starting color of the shape.
   * @param endingColor   The ending color of the shape.
   * @param startingTick  The tick when the shape should start changing color.
   * @param endingTick    The tick when the shape should finish changing color.
   */
  public ProviderColorChange(ProviderAnimShape shape, ColorClass startingColor, ColorClass endingColor,
                             int startingTick, int endingTick) {
    super(shape, startingTick, endingTick);

    this.startingColor = new ColorClass(startingColor);
    this.endingColor = new ColorClass(endingColor);

    this.transformInfo[1] = "changes color";
    transformInfo[2] = startingColor.toString();
    transformInfo[3] = endingColor.toString();

    redIncrement = (double) ((endingColor.getRed() - startingColor.getRed()) / span);
    greenIncrement = (double) ((endingColor.getGreen() - startingColor.getGreen()) / span);
    blueIncrement = (double) ((endingColor.getBlue() - startingColor.getBlue()) / span);
  }

  @Override
  public ITransformation makeCopy() {
    return new ProviderColorChange(new ProviderAnimShape(this.shape),
            new ColorClass(this.startingColor), new ColorClass(this.endingColor),
            this.startingTick, this.endingTick);
  }

  @Override
  public void transform(int tick) {

    int incrementMultiplier = tick - startingTick;

    double targetRed = (redIncrement * incrementMultiplier) + startingColor.getRed();
    double targetGreen = (greenIncrement * incrementMultiplier) + startingColor.getGreen();
    double targetBlue = (blueIncrement * incrementMultiplier) + startingColor.getBlue();

    shape.changeColor(new ProviderColor(targetRed, targetGreen, targetBlue));
  }

  @Override
  public Double[] getStart() {
    return new Double[]{(double) startingColor.getRed(),
            (double) startingColor.getGreen(),
            (double) startingColor.getBlue()};
  }

  @Override
  public Double[] getEnd() {
    return new Double[]{(double) endingColor.getRed(),
            (double) endingColor.getGreen(),
            (double) endingColor.getBlue()};
  }

}
