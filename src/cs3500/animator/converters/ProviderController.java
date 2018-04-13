package cs3500.animator.converters;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.controller.controllerimplementations.ControllerWithHybrid;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.IController;
import cs3500.animator.provider.ITransformation;
import cs3500.animator.view.interfaces.HybridViewInterface;

public class ProviderController extends ControllerWithHybrid implements KeyListener, ActionListener,
        ItemListener, IController {


  @Override
  public void buildModel() {
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public void buildView() {
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public String getStatus() {

  }

  @Override
  public void setStatus(String status) {

  }

  @Override
  public void pauseAnimation() {

  }

  @Override
  public int getSpeed() {

  }

  @Override
  public void changeSpeed(int speed) {

  }

  @Override
  public List<IAnimShape> getAllShapes() {

  }

  @Override
  public List<ITransformation> getAllTransformations() {

  }

  @Override
  public List<IAnimShape> getCurrentShapes() {
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public boolean isLooping() {

  }

  @Override
  public int getTick() {
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public void toggleLoopBack() {

  }


  private String createView() throws IllegalArgumentException {
    ArrayList<String> providerViewNames = new ArrayList<>();
    providerViewNames.add("provider");
    providerViewNames.add("svg2");
    providerViewNames.add("text2");
    providerViewNames.add("visual2");

    if (providerViewNames.contains(type)) {

    } else {
      try {
        view = vFac.create(type, new ModelInsulator(model), outFile, ticksPerSecond);
      } catch (Exception e) {
        return e.getMessage();
      }

      //if the view is a hybrid, create the hybridView and also assign proper listeners.
      if (view.getViewType().equals("Hybrid View")) {
        hybridView = (HybridViewInterface) view;
        hybridView.setListeners(this, this, this);
      }

    }
  }

}
