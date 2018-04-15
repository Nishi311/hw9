package cs3500.animator.model.interfaces;

/**
 * This interface is used to guide any shape interface factories built. The sole method,
 * create(), is all that's needed for the factory.
 */
public interface ShapeFactoryInterface {
  /**
   * The create method is responsible for creating ShapeInterface objects from the given parameters.
   * The implementation of this method will determine what kinds of shapes the user can make.
   *
   * @param shapeName  The name of the shape to be made.
   * @param shapeType  The type of shape to be made.
   * @param color      The color of the shape to be made.
   * @param pos        The position of the shape to be made.
   * @param parameters Any additional parameters needed to create the shape. Dictated by shape type.
   * @return A ShapeInterface of the type designated by the parameters.
   * @throws IllegalArgumentException if the shapeType is not known or if any of the parameters
   *                                  are invalid for that shapeType.
   */
  ShapeInterface create(String shapeName, String shapeType, ColorClassInterface color,
                        Position2DInterface pos, float orient, Object... parameters)
          throws IllegalArgumentException;
}
