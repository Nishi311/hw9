package cs3500.animator.view.interfaces;

import java.io.IOException;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.provider.view.IViewable;

public interface ViewFactoryProviderInterface extends ViewFactoryInterface {

  IViewable createProviderView(String viewType, ModelInsulatorInterface model, String output, int
                               ticksPerSecond) throws IOException;
}
