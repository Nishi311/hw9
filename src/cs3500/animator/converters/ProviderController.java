package cs3500.animator.converters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.controller.controllerimplementations.ControllerWithHybrid;

import cs3500.animator.model.interfaces.AnimationModelInterface;

import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.IAnimationModel;
import cs3500.animator.provider.IController;
import cs3500.animator.provider.ITransformation;
import cs3500.animator.provider.view.APrintableView;
import cs3500.animator.provider.view.IRunnableView;
import cs3500.animator.provider.view.IViewable;

import cs3500.animator.view.ViewTypes;
import cs3500.animator.view.interfaces.HybridViewInterface;

/**
 * Controller that combines functions and information necessary to work both our views as well as
 * the provider's views, via extension.
 */
public class ProviderController extends ControllerWithHybrid implements KeyListener, ActionListener,
        ItemListener, IController {

  private IViewable providerView;
  private IAnimationModel providerModel;

  private String status;

  private ViewFactoryProviderInterface vFacProvider;

  private Timer timer;

  private boolean doesLoop = true;
  private boolean isPaused = false;

  /**
   * Basic constructor. Requires a ViewFactory capable of producing both our and provider views.
   *
   * @param model The model which will be built up with file information.
   * @param vFac  The viewFactory that will be used to create the views.
   */
  public ProviderController(AnimationModelInterface model, ViewFactoryProviderInterface vFac) {
    super(model, vFac);
    this.vFacProvider = vFac;
  }

  @Override
  public void run() {
    if (view == null && hybridView == null && providerView == null) {
      throw new IllegalStateException("View has not been created. MUST run parseInput() method "
              + "first.");
    }
    //if the view is a hybrid, run the hybrid version of run, otherwise run the standard version.
    if (view instanceof HybridViewInterface) {
      hybridView.run();
    } else if (providerView != null) {
      if (providerView instanceof APrintableView) {
        try {
          ((APrintableView) providerView).printView();
        } catch (Exception e) {
          System.out.println("Printable view failure");
        }
      } else {
        timer.start();
        changeStatus("Playing");
      }
    } else {
      view.run();
    }

  }

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
    return this.status;
  }

  @Override
  public void changeStatus(String status) {
    this.status = status;
  }

  @Override
  public void pauseAnimation() {
    isPaused = !isPaused;
    if (isPaused) {
      changeStatus("Paused");
      timer.stop();
    } else {
      changeStatus("Playing");
      timer.start();
    }
  }

  @Override
  public int getSpeed() {
    return this.ticksPerSecond;
  }

  @Override
  public void changeSpeed(int speed) {
    this.ticksPerSecond = speed;
    timer.setDelay(1000 / speed);
  }

  @Override
  public List<IAnimShape> getAllShapes() {
    return this.providerModel.getShapes();
  }

  @Override
  public List<ITransformation> getAllTransformations() {
    return this.providerModel.getTransformations();
  }

  @Override
  public List<IAnimShape> getCurrentShapes() {
    return this.providerModel.currentShapes();
  }

  @Override
  public List<IAnimShape> getStartShapes() {
    return this.providerModel.getStartShapes();
  }

  @Override
  public boolean isLooping() {
    return this.doesLoop;
  }

  @Override
  public int getEndTick() {
    return this.providerModel.getEndTick();
  }

  @Override
  public void toggleLoopBack() {
    this.doesLoop = !doesLoop;
  }

  @Override
  public void doubleSpeed() {
    throw new UnsupportedOperationException("Not needed for HW 8");

  }

  @Override
  public void halveSpeed() {
    throw new UnsupportedOperationException("Not needed for HW 8");

  }

  @Override
  public void resetSpeed() {
    throw new UnsupportedOperationException("Not needed for HW 8");

  }

  @Override
  public void restart() {
    providerModel.restart();
    timer.start();
    changeStatus("Playing");
  }

  @Override
  public void toggleVisible(String shapeName) {
    providerModel.toggleVisible(shapeName);
  }

  @Override
  public boolean getPaused() {
    return isPaused;
  }


  @Override
  protected ViewTypes viewTypeCheck(String temp) {
    ViewTypes type = ViewTypes.SVG;
    switch (temp) {
      case "text":
        type = ViewTypes.TEXT;
        break;
      case "visual":
        type = ViewTypes.VISUAL;
        break;
      case "svg":
        type = ViewTypes.SVG;
        break;
      case "interactive":
        type = ViewTypes.HYBRID;
        break;
      case "text2":
        type = ViewTypes.TEXT2;
        break;
      case "visual2":
        type = ViewTypes.VISUAL2;
        break;
      case "svg2":
        type = ViewTypes.SVG2;
        break;
      case "provider":
        type = ViewTypes.PROVIDER;
        break;

      default:
        throw new IllegalArgumentException("The view type MUST be either \"text\", \"visual\", "
                + "\"svg\", \"interactive\", \"text2\", \"visual2\"," +
                "\"svg2\", or \"provider\". These are case sensitive.");
    }
    return type;
  }


  @Override
  protected void createView() throws IOException {
    ArrayList<String> providerViewNames = new ArrayList<>();
    providerViewNames.add("PROVIDER");
    providerViewNames.add("SVG2");
    providerViewNames.add("TEXT2");
    providerViewNames.add("VISUAL2");

    if (providerViewNames.contains(type.name())) {
      providerModel = new ProviderModel(this.model);

      try {
        providerView = vFacProvider.createProviderView(type,
                getAllShapes(),
                getCurrentShapes(),
                getAllTransformations(),
                outFile,
                ticksPerSecond,
                this);
      } catch (IOException e) {
        throw new IOException(e.getMessage());
      }

      timer = new Timer(1000 / this.ticksPerSecond, new RunAction());
      timer.stop();
    } else {
      try {
        view = vFac.create(type, new ModelInsulator(model), outFile, ticksPerSecond);
      } catch (Exception e) {
        throw new IllegalArgumentException(e.getMessage());
      }

      //if the view is a hybrid, create the hybridView and also assign proper listeners.
      if (view.getViewType().equals("Hybrid View")) {
        hybridView = (HybridViewInterface) view;
        hybridView.setListeners(this, this, this);
      }

    }
  }

  /**
   * The action that will occur on each cycle of the timer. Calls model methods to update the
   * local list of shapes and draw to views.
   */
  private class RunAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

      if (providerModel.getTick() <= providerModel.getEndTick()) {
        try {
          providerModel.tick();
          ((IRunnableView) providerView).updateView(getCurrentShapes(), getStatus());
        } catch (IOException exception) {
          throw new IllegalStateException("Could not render this frame, at tick "
                  + providerModel.getTick() + ".");
        }
      } else {
        if (isLooping()) {
          restart();
        } else {
          pauseAnimation();
        }
      }
    }

  }


}
