package cs3500.animator.view.interfaces;

import java.io.IOException;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.view.ViewTypes;

/**
 * Basic outline for any viewFactory type.
 */
public interface ViewFactoryInterface {
  /**
   * This function produces different implementations of the view interface depending on the
   * viewType and provides these implementations with necessary values.
   *
   * @param type           The type of view to be produced.
   * @param model          An insulated version of the model that will be used to create
   *                       the animation.
   * @param output         The designated output destination of the animation.
   * @param ticksPerSecond The number of ticks represented by 1 second.
   * @return A fully formed view of the given type.
   * @throws IllegalArgumentException If the view type is unknown. If model is null.
   * @throws IOException              If the view encounters a problem with the FileOutputStream.
   */
  ViewInterface create(ViewTypes type, ModelInsulatorInterface model, String output,
                       int ticksPerSecond) throws IOException;
}
