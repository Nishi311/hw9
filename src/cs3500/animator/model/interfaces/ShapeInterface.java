package cs3500.animator.model.interfaces;

import java.util.List;
import java.awt.Graphics;

import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;

/**
 * This interface governs the implementation of shape interface. These objects are fundamental
 * elements of both AnimationComponents (which act on shapes) and AnimationModels (which are
 * composed of AnimationComponents).
 */
public interface ShapeInterface {
  /**
   * Allows the user to modify any parameter of the Shape.
   *
   * @param key   The parameter to change.
   * @param value The new value of the parameter.
   * @throws IllegalArgumentException if there is no parameter by that name or if the value
   *                                  is not a valid type for the key.
   */
  void editParameter(String key, Object value) throws IllegalArgumentException;

  /**
   * Allows the user to retrieve a designated parameter. Must be further cast by user when received
   * to be useful (i.e, user must know what type of object they will receive)
   *
   * @param key The name of the parameter the user is looking for.
   * @return A generic object containing the parameter.
   */
  Object getParameter(String key);

  /**
   * Allows the user to retrieve the type of shape.
   *
   * @return The type of shape as a string.
   */
  String getShapeType();

  /**
   * Allows the user to retrieve the name of the shape. This is a universal parameter so this is
   * available as its own function. Can do the same with getParameter("NAME").
   *
   * @return The name of the shape as a string.
   */
  String getName();

  /**
   * Allows the user to retrieve the color of the shape. This is a universal parameter so this is
   * available as its own function. Can do the same with getParameter("COLOR).
   *
   * @return The color of the shape as a {@link ColorClass} object.
   */
  ColorClass getColor();

  /**
   * Allows the user to retrieve the position of the shape. This is a universal parameter so this is
   * available as its own function. Can do the same with getParameter("POSITION").
   *
   * @return The position of the shape as a {@link Position2D} object.
   */
  Position2D getPosition();

  /**
   * Allows the user to retrieve the position of a shape. This is a universal parameter so this is
   * available as its own function. Can do the same with getParameter("ORIENTATION").
   *
   * @return The orientation of the shape as a float.
   */
  float getOrientation();

  /**
   * Allows the user to retrieve the visibility of the shape. This is a universal parameter so this
   * is available as its own function. Can do the same with getParameter("VISIBILITY").
   *
   * @return The visibility of the shape as a boolean object.
   */
  boolean getVisibility();

  /**
   * Allows the user to reset the shape to its initial parameters (i.e the ones it was constructed
   * with).
   */
  void resetShape();

  /**
   * Allows the user to produce a shallow copy of the Shape Interface.
   *
   * @return A shallow copy of the shape as a Shape Interface.
   */
  ShapeInterface copy();


  @Override
  String toString();


  @Override
  boolean equals(Object obj);

  @Override
  int hashCode();

  /**
   * Returns all of shapes' dimensions.
   *
   * @return the dimensions for shapes
   */
  double[] allDimensions();

  /**
   * Returns a svg shape format shape name.
   *
   * @return the svg shape format shape name.
   */
  String getSvgShape() throws IllegalArgumentException;

  /**
   * Represent the length of shape.
   *
   * @return the String represrnt the length of shape.
   */
  String getSvgShapeLenX();

  /**
   * Represent the length of shape.
   *
   * @return the String represrnt the length of shape.
   */
  String getSvgShapeLenY();

  /**
   * represent the x-coordinate of a shape.
   *
   * @return the x-coordinate of a shape.
   */
  String getSvgShapeCodX();

  /**
   * represent the x-coordinate of a shape.
   *
   * @return the x-coordinate of a shape.
   */
  String getSvgShapeCodY();

  /**
   * A list contains all lenNames of a shape.
   * Such as withd, height.
   *
   * @return a List contains all Len names of a shape.
   */
  List<String> getattributeLennames();

  /**
   * Allows the shape to be drawn as a graphics object.
   * @param g The graphics object the shape will be drawn too. I think. Not too sure on this one.
   */
  void draw(Graphics g);

  /**
   * Covert Shape to Svg String.
   *
   * @return A formatted String for Svg text
   */
  String toSvgString();
}
