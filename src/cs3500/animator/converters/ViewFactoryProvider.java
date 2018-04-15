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
import cs3500.animator.view.ViewTypes;

/**
 * An implementation of ViewFactoryProviderInterface that extends the original viewFactory. Allows
 * for the create of both provider and our own view types.
 */
public class ViewFactoryProvider extends ViewFactoryWithHybrid
        implements ViewFactoryProviderInterface {

  @Override
  public IViewable createProviderView(ViewTypes viewType, List<IAnimShape> providerShapeListAll,
                                      List<IAnimShape> providerShapeListActive,
                                      List<ITransformation> providerTransformList,
                                      String output, int ticksPerSecond, IController controller)
          throws IOException {

    IViewable toReturn;

    PrintStream out = System.out;
    if (!output.equals("out")) {
      out = new PrintStream(new FileOutputStream(output));
    }

    boolean isLooping = controller.isLooping();
    int endTick = controller.getEndTick();

    switch (viewType) {
      case TEXT2:
        toReturn = new TextView(ticksPerSecond, out);
        toReturn.setShapes(providerShapeListAll);
        ((IPrintableView) toReturn).setTransformations(providerTransformList);
        return toReturn;
      case SVG2:
        toReturn = new SVGView(ticksPerSecond, out, isLooping, endTick);
        toReturn.setShapes(providerShapeListAll);
        ((IPrintableView) toReturn).setTransformations(providerTransformList);
        return toReturn;
      case VISUAL2:
        toReturn = new VisualView(ticksPerSecond);
        toReturn.setShapes(providerShapeListActive);
        return toReturn;
      case PROVIDER:
        try {
          toReturn = new InteractiveView(ticksPerSecond, out, endTick, controller);
          toReturn.setShapes(providerShapeListActive);
          ((IPrintableView) toReturn).setTransformations(providerTransformList);
          return toReturn;
        } catch (Exception e) {
          throw new IllegalStateException(e.getMessage());
        }
      default:
        break;
    }
    return null;
  }
}
