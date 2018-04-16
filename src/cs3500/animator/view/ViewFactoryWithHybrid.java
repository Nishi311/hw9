package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.view.interfaces.ViewFactoryInterface;
import cs3500.animator.view.interfaces.ViewInterface;
import cs3500.animator.view.viewimplementations.HybridView;
import cs3500.animator.view.viewimplementations.SVGView;
import cs3500.animator.view.viewimplementations.TextView;
import cs3500.animator.view.viewimplementations.VisualView;

/**
 * View Factory capable of producing text, svg, visual as well as interactive (hybrid) views.
 */
public class ViewFactoryWithHybrid implements ViewFactoryInterface {
  @Override
  public ViewInterface create(ViewTypes type, ModelInsulatorInterface model, String output,
                              int ticksPerSecond)
          throws IllegalArgumentException, IOException {

    if (model == null) {
      throw new IllegalArgumentException("Cannot accept null model");
    }
    if (type == ViewTypes.TEXT) {
      return new TextView(model, output, ticksPerSecond);
    } else if (type == ViewTypes.SVG) {
      return new SVGView(model, output, ticksPerSecond);
    } else if (type == ViewTypes.VISUAL) {
      return new VisualView(model, ticksPerSecond);
    } else if (type == ViewTypes.HYBRID) {
      return new HybridView(model, output, ticksPerSecond);
    } else {
      throw new IllegalArgumentException("Unknown view type");
    }
  }
}
