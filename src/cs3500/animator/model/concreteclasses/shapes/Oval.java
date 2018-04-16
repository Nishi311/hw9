package cs3500.animator.model.concreteclasses.shapes;

import java.awt.Graphics;
import java.awt.Color;
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
 * This class contains all information necessary to designate an Oval type shape.
 * INVARIANT: Cannot have negative x or y radius.
 */
public class Oval extends ShapeAbstract {
  /**
   * Basic Oval constructor.
   *
   * @param name       The name of the oval.
   * @param color      The color of the oval.
   * @param pos        The position of the oval.
   * @param visibility The visibility of the oval.
   * @param radiusX    The x radius of the oval.
   * @param radiusY    The y radius of the oval.
   * @throws IllegalArgumentException If either the X or Y radii are negative.
   */
  public Oval(String name,
              ColorClassInterface color,
              Position2DInterface pos,
              float orient,
              boolean visibility,
              float radiusX,
              float radiusY) throws IllegalArgumentException {
    if (radiusX < 0.0 || radiusY < 0) {
      throw new IllegalArgumentException("Cannot have negative radius for Oval");
    }
    if (orient < 0.0 || orient >= 360) {
      throw new IllegalArgumentException("Cannot have an orientation that is negative or greater"
              + "than 360 degrees");
    }

    workingParameterMap.put("xRadius", radiusX);
    workingParameterMap.put("yRadius", radiusY);
    workingParameterMap.put(UniversalShapeParameterTypes.NAME.name(), name);
    workingParameterMap.put(UniversalShapeParameterTypes.COLOR.name(), color);
    workingParameterMap.put(UniversalShapeParameterTypes.POSITION.name(), pos);
    workingParameterMap.put(UniversalShapeParameterTypes.ORIENTATION.name(), orient);
    workingParameterMap.put(UniversalShapeParameterTypes.VISIBILITY.name(), visibility);

    initialParameterMap = new HashMap<>(workingParameterMap);
  }

  /**
   * Default Oval constructor.
   */
  public Oval() {
    workingParameterMap.put("xRadius", 0);
    workingParameterMap.put("yRadius", 0);
    workingParameterMap.put(UniversalShapeParameterTypes.NAME.name(), "Hello World");
    workingParameterMap.put(UniversalShapeParameterTypes.COLOR.name(), new ColorClass());
    workingParameterMap.put(UniversalShapeParameterTypes.POSITION.name(), new Position2D());
    workingParameterMap.put(UniversalShapeParameterTypes.ORIENTATION.name(), 0.0);
    workingParameterMap.put(UniversalShapeParameterTypes.VISIBILITY.name(), false);

    initialParameterMap = new HashMap<>(workingParameterMap);
  }

  @Override
  public void editParameter(String key, Object value) throws IllegalArgumentException {
    if (!workingParameterMap.containsKey(key)) {
      throw new IllegalArgumentException("A(n) " + getShapeType() + " does not have the parameter "
              + "\"" + key + "\"");
    }

    universalKeyObjectValidation(key, value);

    if (key.equals("xRadius") && !(value instanceof Float)) {
      throw new IllegalArgumentException("X radius must be a float");
    } else if (key.equals("yRadius") && !(value instanceof Float)) {
      throw new IllegalArgumentException("Y radius must be a float");
    }

    workingParameterMap.replace(key, value);

  }

  @Override
  public String getShapeType() {
    return "Oval";
  }

  @Override
  public ShapeInterface copy() {

    ShapeInterface copyOval = new Oval(
            (String) workingParameterMap.get(UniversalShapeParameterTypes.NAME.name()),
            (ColorClassInterface)
                    workingParameterMap.get(UniversalShapeParameterTypes.COLOR.name()),
            (Position2DInterface)
                    workingParameterMap.get(UniversalShapeParameterTypes.POSITION.name()),
            (float) workingParameterMap.get(UniversalShapeParameterTypes.ORIENTATION.name()),
            (boolean) workingParameterMap.get(UniversalShapeParameterTypes.VISIBILITY.name()),
            (float) workingParameterMap.get("xRadius"),
            (float) workingParameterMap.get("yRadius")
    );
    return copyOval;
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

    workingParameterMap.put("xRadius", initialParameterMap.get("xRadius"));
    workingParameterMap.put("yRadius", initialParameterMap.get("yRadius"));

  }

  @Override
  public String toString() {
    ColorClassInterface currentColor = (ColorClass) workingParameterMap.get(
            UniversalShapeParameterTypes.COLOR.name());
    Position2DInterface currentPosition = (Position2D) workingParameterMap.get(
            UniversalShapeParameterTypes.POSITION.name());
    float currentRadiusX = (float) workingParameterMap.get("xRadius");
    float currentRadiusY = (float) workingParameterMap.get("yRadius");

    return "Name: " + getName() + "\n"
            + "Type: oval\n"
            + "Center: " + currentPosition.toString() + ", X radius: " + currentRadiusX
            + ", Y radius: " + currentRadiusY + ", Color: " + currentColor.toString() + "\n";
  }

  @Override
  public double[] allDimensions() {
    float currentRadiusX = (float) workingParameterMap.get("xRadius");
    float currentRadiusY = (float) workingParameterMap.get("yRadius");
    return new double[]{currentRadiusX, currentRadiusY};
  }

  @Override
  public String getSvgShape() {
    return "ellipse";
  }

  @Override
  public String getSvgShapeLenX() {
    return "rx";
  }

  @Override
  public String getSvgShapeLenY() {
    return "ry";
  }

  @Override
  public String getSvgShapeCodX() {
    return "cx";
  }

  @Override
  public String getSvgShapeCodY() {
    return "cy";
  }

  @Override
  public List<String> getattributeLennames() {
    List<String> names = new ArrayList<String>();
    names.add(getSvgShapeCodX());
    names.add(getSvgShapeCodY());
    return names;
  }

  @Override
  public String toSvgString() {
    ColorClassInterface currentColor = (ColorClass) workingParameterMap.get(
            UniversalShapeParameterTypes.COLOR.name());
    Position2DInterface currentPosition = (Position2D) workingParameterMap.get(
            UniversalShapeParameterTypes.POSITION.name());
    float currentRadiusX = (float) workingParameterMap.get("xRadius");
    float currentRadiusY = (float) workingParameterMap.get("yRadius");

    return "<ellipse id=" +
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
            getColor().tranferrbg() + "\"" + " visibility=\"visible\"" + ">";
  }

  @Override
  public void draw(Graphics g) {

    if ((Boolean) workingParameterMap.get(UniversalShapeParameterTypes.VISIBILITY.name())) {
      Position2DInterface currentPosition = (Position2D) workingParameterMap.get(
              UniversalShapeParameterTypes.POSITION.name());
      ColorClassInterface currentColor = (ColorClass) workingParameterMap.get(
              UniversalShapeParameterTypes.COLOR.name());
      float currentRadiusX = (float) workingParameterMap.get("xRadius");
      float currentRadiusY = (float) workingParameterMap.get("yRadius");

      g.drawOval((int) currentPosition.getX(), (int) currentPosition.getY(), (int) currentRadiusX,
              (int) currentRadiusY);
      g.setColor(new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()));
      g.fillOval((int) currentPosition.getX(), (int) currentPosition.getY(), (int) currentRadiusX,
              (int) currentRadiusY);
    } else {
      g.dispose();
    }
  }
}
