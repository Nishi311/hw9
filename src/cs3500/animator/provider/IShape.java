package cs3500.animator.provider;
/**
 * This is an interface for classes representing shapes, i.e. rectangle, oval, etc.
 * An object of this class has no knowledge of its presence, appearance, or location in the
 * animation.
 */
public interface IShape {

  /**
   * Virtual copy constructor, implementing the prototype pattern.
   *
   * @return A deep copy of this shape
   */
  Shape makeCopy();

  /**
   * Check if the shape that has been created meets whatever standards the class sets for it.
   *
   * @return Returns true if the shape is valid
   * @throws IllegalArgumentException Throws exception if the shape is not valid
   */
  boolean checkValid() throws IllegalArgumentException;

  /**
   * A static method necessary to allow an arbitrary number of parameters to create a double[]
   * to be used in the super method of a subclass of ShapeType.
   *
   * @param parameters Parameters to take in
   * @return double[] containing the parameters
   */
  static double[] buildArray(double... parameters) {
    return parameters;
  }

  /**
   * Getter for the name of a shape, i.e. "Rectangle", "Oval", etc.
   *
   * @return The type of the shape.
   */
  String getType();

  /**
   * Getter for the name of the reference point of a shape,
   * i.e. "Lower-left corner", "Center", etc.
   *
   * @return The name of the reference point for the shape
   */
  String getReference();

  /**
   * Getter for the parameter names, the names of the parameters that describe the shape.
   *
   * @return The names of the parameters that describe the shape
   */
  String[] getPNames();

  /**
   * Getter for the parameter values, the parameters that describe the shape.
   *
   * @return The parameters that describe the shape
   */
  double[] getPValues();

  /**
   * Scale an existing shape in size by some scale factors.
   * Note: If not all parameters of the shape are scalable, this method should be overridden.
   *
   * @param scaleFactors Scales each parameter of the shape by this factor.
   * @throws IllegalArgumentException Throws exception if the number of scaleFactors doesn't match
   *                                  the number of scalable parameters for the shape.
   */
  void scaleTo(double... scaleFactors);

}
