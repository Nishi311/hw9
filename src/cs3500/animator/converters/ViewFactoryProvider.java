package cs3500.animator.converters;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.converters.transforms.ProviderColorChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ColorChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.PositionChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeRR;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeWH;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.IController;
import cs3500.animator.provider.ITransformation;
import cs3500.animator.provider.view.IPrintableView;
import cs3500.animator.provider.view.IViewable;
import cs3500.animator.provider.view.InteractiveView;
import cs3500.animator.provider.view.VisualView;
import cs3500.animator.provider.view.SVGView;
import cs3500.animator.provider.view.TextView;
import cs3500.animator.view.ViewFactoryWithHybrid;



public class ViewFactoryProvider extends ViewFactoryWithHybrid
        implements ViewFactoryProviderInterface {
  private List<IAnimShape> providerShapeListAll;
  private List<IAnimShape> providerShapeListActive;
  private List<ITransformation> providerTransformList;

  private List<ShapeInterface> shapeList;
  private List<AnimationComponentInterface> animationList;

  private Map<String, List<AnimationComponentInterface>> shapeToAnimation = new HashMap<>();
  private Map<String, IAnimShape> shapeToShapeObject = new HashMap<>();

  @Override
  public IViewable createProviderView(String viewType, ModelInsulatorInterface model, String output,
                                      int ticksPerSecond, IController controller)
          throws IOException {

    IViewable toReturn;

    shapeToAnimation = model.getShapeNameToAnimationMap();
    shapesToIShapes(model.getShapeList());
    amComsToTransformations(model.getAnimationList());

    PrintStream out = System.out;
    if (!output.equals("out")){
      out = new PrintStream(new FileOutputStream(output));
    }

    boolean isLooping = controller.isLooping();
    int endTick = controller.getEndTick();

    switch (viewType) {
      case "text2":
        toReturn = new TextView(ticksPerSecond, out);
        toReturn.setShapes(providerShapeListAll);
        ((IPrintableView) toReturn).setTransformations(providerTransformList);
        return toReturn;
        break;
      case "svg2":
        toReturn = new SVGView(ticksPerSecond, out, isLooping, endTick);
        toReturn.setShapes(providerShapeListAll);
        ((IPrintableView) toReturn).setTransformations(providerTransformList);
        return toReturn;
        break;
      case "visual2":
        toReturn = new VisualView(ticksPerSecond);
        toReturn.setShapes(providerShapeListActive);
        return toReturn;
        break;
      case "provider":
        try {
          toReturn = new InteractiveView(ticksPerSecond, out, endTick, controller);
          toReturn.setShapes(providerShapeListActive);
          ((IPrintableView) toReturn).setTransformations(providerTransformList);
          return toReturn;
        } catch (Exception e){
          throw new IllegalStateException(e.getMessage());
        }
        break;
      default:
        break;
    }
    return null;
  }

  private void shapesToIShapes(List<ShapeInterface> conversion) {
    for (ShapeInterface shape : conversion) {
      List<AnimationComponentInterface> animations = shapeToAnimation.get(shape.getName());

      IAnimShape temp = new ProviderAnimShape(shape, animations.get(0),
              animations.get(animations.size()-1));
      shapeToShapeObject.put(temp.getName(), temp);
      providerShapeListAll.add(temp);
    }

    for (IAnimShape shape: providerShapeListAll){
      if (shape.isActive(1)){
        providerShapeListActive.add(shape);
      }
    }

  }

  private void amComsToTransformations(List<AnimationComponentInterface> conversion) {
    for (AnimationComponentInterface animation : conversion){

      if (animation instanceof ColorChange){
       providerTransformList.add(new ProviderColorChange(animation,
               shapeToShapeObject.get(animation.getTargetName())));
      }
      else if (animation instanceof PositionChange){
        providerTransformList.add(new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName())));
      }
      else if (animation instanceof ScaleChangeRR || animation instanceof ScaleChangeWH){
        providerTransformList.add(new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName())));
      }
    }
  }
}
