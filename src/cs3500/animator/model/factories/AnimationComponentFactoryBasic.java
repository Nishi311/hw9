package cs3500.animator.model.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.animator.model.concreteclasses.animationcomponenttypes.ColorChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.PositionChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeRR;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeWH;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.VisibilityChange;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.interfaces.AnimationComponentFactoryInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * Implementation of AnimationComponentFactoryInterface that provides for color changing,
 * position changing, scale changing and visibility changing.
 */
public class AnimationComponentFactoryBasic implements AnimationComponentFactoryInterface {
  @Override
  public AnimationComponentInterface create(ShapeInterface targetShape, String animationType,
                                            Duration animationDuration, Object... parameters)
          throws IllegalArgumentException {
    AnimationComponentInterface newAnimation;
    List<Object> parameterList = new ArrayList<>();

    Collections.addAll(parameterList, parameters);

    switch (animationType) {
      case "Color Change":
        if (parameterList.size() != 2) {
          throw new IllegalArgumentException("Wrong amount of parameters for a Color Change");
        }
        if (!(parameterList.get(0) instanceof ColorClass)
                || !(parameterList.get(1) instanceof ColorClass)) {
          throw new IllegalArgumentException("Must pass two ColorClass parameters");
        }
        newAnimation = new ColorChange(targetShape, animationDuration,
                (ColorClass) parameterList.get(0), (ColorClass) parameterList.get(1));
        break;

      case "Position Change":
        if (parameterList.size() != 2) {
          throw new IllegalArgumentException("Wrong amount of parameters for a Position Change");
        }
        if (!(parameterList.get(0) instanceof Position2D)
                || !(parameterList.get(1) instanceof Position2D)) {
          throw new IllegalArgumentException("Must pass two Position2D parameters");
        }
        newAnimation = new PositionChange(targetShape, animationDuration,
                (Position2D) parameterList.get(0), (Position2D) parameterList.get(1));
        break;

      case "Scale Change RR":
        if (!targetShape.getShapeType().equals("Oval")) {
          throw new IllegalArgumentException("This scale change is for shapes with two radii");
        }
        if (parameterList.size() != 4) {
          throw new IllegalArgumentException("Wrong amount of parameters for a Scale Change");
        }
        if (!(parameterList.get(0) instanceof Float) || !(parameterList.get(1)
                instanceof Float) || !(parameterList.get(2) instanceof Float)
                || !(parameterList.get(3) instanceof Float)) {
          throw new IllegalArgumentException("Must pass four float parameters");
        }

        newAnimation = new ScaleChangeRR(targetShape, animationDuration,
                (float) parameterList.get(0), (float) parameterList.get(1),
                (float) parameterList.get(2), (float) parameterList.get(3));
        break;

      case "Scale Change WH":
        if (!targetShape.getShapeType().equals("Rectangle")) {
          throw new IllegalArgumentException("This scale change is for width/height based shapes");
        }
        if (parameterList.size() != 4) {
          throw new IllegalArgumentException("Wrong amount of parameters for a Scale Change");
        }
        if (!(parameterList.get(0) instanceof Float)
                || !(parameterList.get(1) instanceof Float) || !(parameterList.get(2)
                instanceof Float)
                || !(parameterList.get(3) instanceof Float)) {
          throw new IllegalArgumentException("Must pass two float parameters");
        }
        newAnimation = new ScaleChangeWH(targetShape, animationDuration,
                (float) parameterList.get(0), (float) parameterList.get(1),
                (float) parameterList.get(2), (float) parameterList.get(3));
        break;

      case "Visibility Change":
        if (parameterList.size() != 1) {
          throw new IllegalArgumentException("Wrong amount of parameters for a Visiblity Change");
        }
        if (!(parameterList.get(0) instanceof Boolean)) {
          throw new IllegalArgumentException("Must pass a Boolean parameter");
        }
        newAnimation = new VisibilityChange(targetShape, animationDuration,
                (Boolean) parameterList.get(0));
        break;
      default:
        throw new IllegalArgumentException("Not a recognized animation type");
    }
    return newAnimation;
  }

}

