package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.view.interfaces.ViewFactoryInterface;
import cs3500.animator.view.interfaces.ViewInterface;
import cs3500.animator.view.viewimplementations.SVGView;
import cs3500.animator.view.viewimplementations.TextView;
import cs3500.animator.view.viewimplementations.VisualView;

/**
 * Factory class that enables the production of different view types based on the given inputs.
 */
public class ViewFactory implements ViewFactoryInterface {
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
    } else {
      throw new IllegalArgumentException("Unknown view type");
    }
  }
}
