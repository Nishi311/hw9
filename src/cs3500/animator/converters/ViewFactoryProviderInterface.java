package cs3500.animator.converters;

import java.io.IOException;
import java.util.List;


import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.IController;
import cs3500.animator.provider.ITransformation;
import cs3500.animator.provider.view.IViewable;
import cs3500.animator.view.ViewTypes;
import cs3500.animator.view.interfaces.ViewFactoryInterface;

/**
 * New View Factory interface that allows for the creation of both our own proprietary view types
 * as well as provider view types.
 */
public interface ViewFactoryProviderInterface extends ViewFactoryInterface {
  /**
   * Allows for the creation of provider view types.
   *
   * @param viewType                The type of view to be created.
   * @param providerShapeListAll    The list of all shapes in the view.
   * @param providerShapeListActive The list of shapes that are active on the fist tick of the
   *                                animation.
   * @param providerTransformList   The list of all Transforms executed in the view.
   * @param output                  The desired output location.
   * @param ticksPerSecond          The initial speed of the view.
   * @param controller              The controller necessary to run the view (really only
   *                                used with the provider interactive view)
   * @return The completed view.
   * @throws IOException If anything goes wrong with output destination.
   */
  IViewable createProviderView(ViewTypes viewType, List<IAnimShape> providerShapeListAll,
                               List<IAnimShape> providerShapeListActive,
                               List<ITransformation> providerTransformList,
                               String output, int ticksPerSecond, IController controller)
          throws IOException;
}
