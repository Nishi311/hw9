package cs3500.animator.converters;

import java.io.IOException;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.provider.IController;
import cs3500.animator.provider.view.IViewable;
import cs3500.animator.view.interfaces.ViewFactoryInterface;

/**
 * New View Factory interface that allows for the creation of both our own proprietary view types
 * as well as provider view types.
 */
public interface ViewFactoryProviderInterface extends ViewFactoryInterface {
  /**
   * Creates a new provider view with the given parameters.
   * @param viewType The view type to be created.
   * @param model The model from which AmComsInterfaces and ShapeInterfaces will be converted.
   * @param output The desired output location.
   * @param ticksPerSecond The number of ticks per second at which the animation should be played.
   * @param controller The controller containing relevant information to the provider views.
   * @return An IViewable configured appropriately
   * @throws IOException
   */
  IViewable createProviderView(String viewType, ModelInsulatorInterface model, String output, int
                               ticksPerSecond, IController controller) throws IOException;
}
