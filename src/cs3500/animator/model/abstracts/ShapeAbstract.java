package cs3500.animator.model.abstracts;

import java.util.HashMap;
import java.util.Set;

import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.interfaces.ColorClassInterface;
import cs3500.animator.model.interfaces.Position2DInterface;
import cs3500.animator.model.interfaces.ShapeInterface;


/**
 * Provides some implementation for basic ShapeInterface methods, mostly the retrieval and
 * evaluation ones. Declares a few protected parameters "workingParameterMap" which contains all
 * the current states of the shape's parameters (open to change) and "initialParameterMap" which
 * contains all the initial states of the shape's parameters at construction.
 */
public abstract class ShapeAbstract implements ShapeInterface {
  protected HashMap<String, Object> workingParameterMap = new HashMap<String, Object>();
  protected HashMap<String, Object> initialParameterMap;

  @Override
  public Object getParameter(String key) {
    if (!workingParameterMap.containsKey(key)) {
      throw new IllegalArgumentException("A(n) " + getShapeType()
              + " does not have the parameter \"" + key + "\"");
    } else {
      return workingParameterMap.get(key);
    }
  }

  @Override
  public String getName() {
    return workingParameterMap.get(UniversalShapeParameterTypes.NAME.name()).toString();
  }

  @Override
  public ColorClassInterface getColor() {
    return (ColorClass) workingParameterMap.get(UniversalShapeParameterTypes.COLOR.name());
  }

  @Override
  public Position2DInterface getPosition() {
    return (Position2D) workingParameterMap.get(UniversalShapeParameterTypes.POSITION.name());
  }

  @Override
  public float getOrientation() {
    return (float) workingParameterMap.get(UniversalShapeParameterTypes.ORIENTATION.name());
  }

  @Override
  public boolean getVisibility() {
    return (boolean) workingParameterMap.get(UniversalShapeParameterTypes.VISIBILITY.name());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ShapeInterface)) {
      return false;
    }
    ShapeAbstract compareShape = (ShapeAbstract) obj;
    if (!initialParameterMap.keySet().equals(compareShape.initialParameterMap.keySet())) {
      return false;
    }
    Set<String> keyList = initialParameterMap.keySet();
    for (String s : keyList) {
      if (!initialParameterMap.get(s).equals(compareShape.initialParameterMap.get(s))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return workingParameterMap.hashCode() + initialParameterMap.hashCode();
  }

  /**
   * Allows the user to validate that universal parameters are being fed the proper types.
   *
   * @param key   The name of the parameter to check.
   * @param value The object that will be checked against the key.
   * @throws IllegalArgumentException If the object type does not match what is required by the
   *                                  parameter designated by the key
   */
  protected void universalKeyObjectValidation(String key, Object value)
          throws IllegalArgumentException {
    if (key.equals(UniversalShapeParameterTypes.COLOR.name())
            && !(value instanceof ColorClassInterface)) {
      throw new IllegalArgumentException("Color must be a ColorClass");
    }

    if (key.equals(UniversalShapeParameterTypes.NAME.name())
            && !(value instanceof String)) {
      throw new IllegalArgumentException("Name must be a String");
    }

    if (key.equals(UniversalShapeParameterTypes.POSITION.name())
            && !(value instanceof Position2DInterface)) {
      throw new IllegalArgumentException("Position must be a Position2D");
    }

    if (key.equals(UniversalShapeParameterTypes.ORIENTATION.name())) {
      if (!(value instanceof Float)) {
        throw new IllegalArgumentException("Orientation must be a float");
      }
      else if ((float) value < -360 || (float) value > 360) {
        throw new IllegalArgumentException("Cannot have an orientation that is negative or greater"
                + " than +/- 360 degrees");
      }
    }
    if (key.equals(UniversalShapeParameterTypes.VISIBILITY.name())
            && !(value instanceof Boolean)) {
      throw new IllegalArgumentException("Visibility must be a Boolean");
    }
  }
}
