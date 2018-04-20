package cs3500.animator.model.concreteclasses.shapes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import cs3500.animator.model.abstracts.ShapeAbstract;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.interfaces.ColorClassInterface;
import cs3500.animator.model.interfaces.Position2DInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * This class contains all information necessary to designate a Rectangle type shape.
 * INVARIANT: Cannot have negative width or height.
 */
public class Rectangle extends ShapeAbstract {
  /**
   * Basic Rectangle constructor.
   *
   * @param name       The name of the rectangle.
   * @param color      The color of the rectangle.
   * @param pos        The position of the rectangle.
   * @param visibility The visibility of the rectangle.
   * @param width      The width of the rectangle.
   * @param height     The height of the rectangle.
   * @throws IllegalArgumentException If either the width or height is negative.
   */
  public Rectangle(String name,
                   ColorClassInterface color,
                   Position2DInterface pos,
                   float orient,
                   boolean visibility,
                   float width,
                   float height)
          throws IllegalArgumentException {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Cannot have negative width or height for rectangle");
    }

    if (orient < 0.0 || orient >= 360) {
      throw new IllegalArgumentException("Cannot have an orientation that is negative or greater"
              + "than 360 degrees");
    }

    workingParameterMap.put("width", width);
    workingParameterMap.put("height", height);
    workingParameterMap.put(UniversalShapeParameterTypes.NAME.name(), name);
    workingParameterMap.put(UniversalShapeParameterTypes.COLOR.name(), color);
    workingParameterMap.put(UniversalShapeParameterTypes.POSITION.name(), pos);
    workingParameterMap.put(UniversalShapeParameterTypes.ORIENTATION.name(), orient);
    workingParameterMap.put(UniversalShapeParameterTypes.VISIBILITY.name(), visibility);

    initialParameterMap = new HashMap<String, Object>(workingParameterMap);
  }

  /**
   * default Rectangle constructor.
   */
  public Rectangle() {
    workingParameterMap.put("width", 0);
    workingParameterMap.put("height", 0);
    workingParameterMap.put(UniversalShapeParameterTypes.NAME.name(), "Hello World");
    workingParameterMap.put(UniversalShapeParameterTypes.COLOR.name(), new ColorClass());
    workingParameterMap.put(UniversalShapeParameterTypes.ORIENTATION.name(), 0.0);
    workingParameterMap.put(UniversalShapeParameterTypes.POSITION.name(), new Position2D());
    workingParameterMap.put(UniversalShapeParameterTypes.VISIBILITY.name(), false);

    initialParameterMap = new HashMap<String, Object>(workingParameterMap);
  }

  @Override
  public void editParameter(String key, Object value) throws IllegalArgumentException {
    if (!workingParameterMap.containsKey(key)) {
      throw new IllegalArgumentException("A(n) " + getShapeType() + " does not have the parameter "
              + "\"" + key + "\"");
    }

    universalKeyObjectValidation(key, value);

    if (key.equals("width") && !(value instanceof Float)) {
      throw new IllegalArgumentException("width must be a float");
    } else if (key.equals("height") && !(value instanceof Float)) {
      throw new IllegalArgumentException("height must be a float");
    }

    workingParameterMap.replace(key, value);

  }

  @Override
  public String getShapeType() {
    return "Rectangle";
  }

  @Override
  public ShapeInterface copy() {
    ShapeInterface copyRectangle = new Rectangle(
            (String) workingParameterMap.get(UniversalShapeParameterTypes.NAME.name()),
            (ColorClassInterface)
                    workingParameterMap.get(UniversalShapeParameterTypes.COLOR.name()),
            (Position2DInterface)
                    workingParameterMap.get(UniversalShapeParameterTypes.POSITION.name()),
            (float) workingParameterMap.get(UniversalShapeParameterTypes.ORIENTATION.name()),
            (boolean) workingParameterMap.get(UniversalShapeParameterTypes.VISIBILITY.name()),
            (float) workingParameterMap.get("width"),
            (float) workingParameterMap.get("height")
    );
    return copyRectangle;
  }

  @Override
  public void resetShape() {
    workingParameterMap.put(UniversalShapeParameterTypes.NAME.name(),
            initialParameterMap.get(UniversalShapeParameterTypes.NAME.name()));

    workingParameterMap.put(UniversalShapeParameterTypes.COLOR.name(),
            new ColorClass((ColorClass) initialParameterMap.get(
                    UniversalShapeParameterTypes.COLOR.name())));

    workingParameterMap.put(UniversalShapeParameterTypes.POSITION.name(),
            new Position2D((Position2D) initialParameterMap.get(
                    UniversalShapeParameterTypes.POSITION.name())));

    workingParameterMap.put(UniversalShapeParameterTypes.ORIENTATION.name(),
            initialParameterMap.get(UniversalShapeParameterTypes.ORIENTATION.name()));

    workingParameterMap.put(UniversalShapeParameterTypes.VISIBILITY.name(),
            initialParameterMap.get(UniversalShapeParameterTypes.VISIBILITY.name()));

    workingParameterMap.put("width", initialParameterMap.get("width"));
    workingParameterMap.put("height", initialParameterMap.get("height"));

  }


  @Override
  public String toString() {
    ColorClassInterface currentColor = (ColorClass) workingParameterMap.get(
            UniversalShapeParameterTypes.COLOR.name());
    Position2DInterface currentPosition = (Position2D) workingParameterMap.get(
            UniversalShapeParameterTypes.POSITION.name());
    float currentWidth = (float) workingParameterMap.get("width");
    float currentHeight = (float) workingParameterMap.get("height");

    return "Name: " + getName() + "\n"
            + "Type: rectangle\n"
            + "Lower-left corner: " + currentPosition.toString() + ", Width: " + currentWidth
            + ", Height: " + currentHeight + ", Color: " + currentColor.toString() + "\n";
  }

  @Override
  public double[] allDimensions() {
    float currentWidth = (float) workingParameterMap.get("width");
    float currentHeight = (float) workingParameterMap.get("height");
    double[] dims = {currentWidth, currentHeight};
    return dims;
  }

  @Override
  public String getSvgShape() {
    return "rect";
  }

  @Override
  public String getSvgShapeLenX() {
    return "width";
  }

  @Override
  public String getSvgShapeLenY() {
    return "height";
  }

  @Override
  public String getSvgShapeCodX() {
    return "x";
  }

  @Override
  public String getSvgShapeCodY() {
    return "y";
  }

  @Override
  public List<String> getattributeLennames() {
    List<String> names = new ArrayList<String>();
    names.add(getSvgShapeLenX());
    names.add(getSvgShapeLenY());
    return names;
  }

  @Override
  public String toSvgString() {
    ColorClassInterface currentColor = (ColorClass) workingParameterMap.get(
            UniversalShapeParameterTypes.COLOR.name());
    Position2DInterface currentPosition = (Position2D) workingParameterMap.get(
            UniversalShapeParameterTypes.POSITION.name());
    float currentWidth = (float) workingParameterMap.get("width");
    float currentHeight = (float) workingParameterMap.get("height");

    return "<rect id=" +
            "\"" + getName() + "\"" + " " + getSvgShapeCodX()
            + "=" + "\""
            + currentPosition.getX() + "\"" + " "
            + getSvgShapeCodY() + "=" + "\"" + currentPosition.getY()
            + "\"" + " "

            + getSvgShapeLenX() + "=" + "\""
            + allDimensions()[0] + "\"" +
            " " + getSvgShapeLenY()
            + "=" + "\"" +
            allDimensions()[1] + "\"" + " fill=" + "\"" + "rgb" +
            currentColor.tranferrbg() + "\"" + " visibility=\"visible\"" + ">";
  }


  @Override
  public void draw(Graphics2D g) {
    if ((Boolean) workingParameterMap.get(UniversalShapeParameterTypes.VISIBILITY.name())) {

      Position2DInterface currentPosition = (Position2D) workingParameterMap.get(
              UniversalShapeParameterTypes.POSITION.name());
      ColorClassInterface currentColor = (ColorClass) workingParameterMap.get(
              UniversalShapeParameterTypes.COLOR.name());
      float currentRadiusX = (float) workingParameterMap.get("width");
      float currentRadiusY = (float) workingParameterMap.get("height");


      Shape rectangle = new Rectangle2D.Double((double) currentPosition.getX(),
              (double) currentPosition.getY(),
              (double) currentRadiusX,
              (double) currentRadiusY) {
      };
      AffineTransform afx = new AffineTransform();
      afx.rotate(Math.toRadians(getOrientation()), currentPosition.getX(), currentPosition.getY());

      Shape rotatedRectangle = afx.createTransformedShape(rectangle);
      g.setPaint(new Color(currentColor.getRed(),currentColor.getGreen(),currentColor.getBlue()));
      g.fill(rotatedRectangle);
    } else {
      g.dispose();
    }
  }
}
