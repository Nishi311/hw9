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

  IViewable createProviderView(ViewTypes viewType, List<IAnimShape> providerShapeListAll,
                               List<IAnimShape> providerShapeListActive,
                               List<ITransformation> providerTransformList,
                               String output, int ticksPerSecond, IController controller)
          throws IOException;
}
