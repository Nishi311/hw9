package cs3500.animator.model.factories;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.concreteclasses.shapes.Oval;
import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.interfaces.ShapeFactoryInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * A Factory class with the sole purpose of spitting out ShapeInterfaces through the create()
 * function. Can create only Oval and Rectangle shape types.
 */
public class ShapeFactoryBasic implements ShapeFactoryInterface {
  @Override
  public ShapeInterface create(String shapeName, String shapeType, ColorClass color, Position2D pos,
                               float orient, Object... parameters)
          throws IllegalArgumentException {
    ShapeInterface newShape;
    List<Object> parameterList = new ArrayList<>();
    for (Object params : parameters) {
      parameterList.add(params);
    }

    switch (shapeType) {

      case "Rectangle":
        if (parameterList.size() != 2) {
          throw new IllegalArgumentException("Wrong amount of parameters for a rectangle");
        }
        try {
          newShape = new Rectangle(shapeName, color, pos, orient, false,
                  (float) parameterList.get(0), (float) parameterList.get(1));
        } catch (ClassCastException e) {
          throw new IllegalArgumentException("Wrong parameters for a rectangle");
        }

        break;

      case "Oval":
        if (parameterList.size() != 2) {
          throw new IllegalArgumentException("Wrong amount of parameters for an Oval");
        }
        try {
          newShape = new Oval(shapeName, color, pos, orient, false,
                  (float) parameterList.get(0), (float) parameterList.get(1));
        } catch (ClassCastException e) {
          throw new IllegalArgumentException("Wrong parameters for an Oval");
        }

        break;

      default:
        throw new IllegalArgumentException("Not a recognized shape type");
    }

    return newShape;
  }
}
