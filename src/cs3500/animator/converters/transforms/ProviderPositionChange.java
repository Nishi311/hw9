package cs3500.animator.converters.transforms;

import cs3500.animator.converters.ProviderLocation;
import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.PositionChange;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.interfaces.Position2DInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ITransformation;

/**
 * Custom implementation of the Provider's ITransform interface. Manipulates the target shape's
 * position values over a given period of time.
 */
public class ProviderPositionChange extends ProviderTransformAbstract {

  private Position2DInterface startingPos;
  private Position2DInterface endingPos;

  private double xIncrement;
  private double yIncrement;

  /**
   * Converting constructor. Allows us to convert between our equivalent PositionChange animation
   * component and the provider version of a position change.
   *
   * @param amCom The PositionChange animation component to convert from.
   * @param shape The shape that the ProviderColorChange will be acting upon.
   */
  public ProviderPositionChange(PositionChange amCom, IAnimShape shape) {
    super(amCom, shape);

    this.startingPos = (Position2D) amCom.getInitialParameters().get(0);
    this.endingPos = (Position2D) amCom.getFinalParameters().get(0);

    this.transformInfo[1] = "moves";
    transformInfo[2] = startingPos.toString();
    transformInfo[3] = endingPos.toString();

    xIncrement = (double) ((endingPos.getX() - startingPos.getX()) / span);
    yIncrement = (double) ((endingPos.getY() - startingPos.getY()) / span);
  }

  /**
   * More basic constructor. Takes the shape and parameters necessary to create a
   * ProviderPositionChange from scratch. Only really used in the makeCopy() method.
   *
   * @param shape        The shape that will be acted upon.
   * @param startingPos  The starting position of the shape.
   * @param endingPos    The ending ending position of the shape.
   * @param startingTick The tick when the shape should start moving.
   * @param endingTick   The tick when the shape should finish moving.
   */
  public ProviderPositionChange(ProviderAnimShape shape, Position2DInterface startingPos,
                                Position2DInterface endingPos, int startingTick, int endingTick) {
    super(shape, startingTick, endingTick);

    this.startingPos = new Position2D(startingPos);
    this.endingPos = new Position2D(endingPos);

    this.transformInfo[1] = "moves";
    transformInfo[2] = startingPos.toString();
    transformInfo[3] = endingPos.toString();

    xIncrement = (double) ((endingPos.getX() - startingPos.getX()) / span);
    yIncrement = (double) ((endingPos.getY() - startingPos.getY()) / span);

  }

  @Override
  public ITransformation makeCopy() {
    return new ProviderPositionChange(new ProviderAnimShape(this.shape),
            new Position2D(this.startingPos), new Position2D(this.endingPos),
            this.startingTick, this.endingTick);
  }

  @Override
  public void transform(int tick) {

    int incrementMultiplier = tick - startingTick;

    double targetX = (xIncrement * incrementMultiplier) + startingPos.getX();
    double targetY = (yIncrement * incrementMultiplier) + startingPos.getY();

    shape.moveTo(new ProviderLocation(targetX, targetY));
  }

  @Override
  public Double[] getStart() {
    double x = (double) startingPos.getX();
    double y = (double) startingPos.getY();
    return new Double[]{x, y};
  }

  @Override
  public Double[] getEnd() {
    double x = (double) endingPos.getX();
    double y = (double) endingPos.getY();
    return new Double[]{x, y};
  }

}

