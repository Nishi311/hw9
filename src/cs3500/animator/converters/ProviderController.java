package cs3500.animator.converters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;


import javax.swing.*;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.controller.controllerimplementations.ControllerWithHybrid;

import cs3500.animator.model.interfaces.AnimationModelInterface;

import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.IAnimationModel;
import cs3500.animator.provider.IController;
import cs3500.animator.provider.ITransformation;
import cs3500.animator.provider.view.IPrintableView;
import cs3500.animator.provider.view.IRunnableView;
import cs3500.animator.provider.view.IViewable;
import cs3500.animator.view.ViewTypes;
import cs3500.animator.view.interfaces.HybridViewInterface;

public class ProviderController extends ControllerWithHybrid implements KeyListener, ActionListener,
        ItemListener, IController {

  private IViewable providerView;
  private IPrintableView printView;
  private IRunnableView runView;
  private IAnimationModel providerModel;

  private String status;

  private ViewFactoryProviderInterface vFacProvider;

  private Timer timer;


  public ProviderController(AnimationModelInterface model, ViewFactoryProviderInterface vFac){
    super (model, vFac);
    this.vFacProvider = vFac;
  }

  @Override
  public void run() {


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
    } else {
      changeStatus("Playing");
    }
  }

  @Override
  public int getSpeed() {
    return this.ticksPerSecond;
  }

  @Override
  public void changeSpeed(int speed) {
    this.ticksPerSecond = speed;
  }

  @Override
  public List<IAnimShape> getAllShapes() {
    return this.providerShapeListAll;
  }

  @Override
  public List<ITransformation> getAllTransformations() {
    return this.providerTransformList;
  }

  @Override
  public List<IAnimShape> getCurrentShapes() {
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public boolean isLooping() {
    return this.doesLoop;
  }

  @Override
  public int getEndTick() {
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public void toggleLoopBack() {
    this.doesLoop = !doesLoop;
  }

  @Override
  public void doubleSpeed(){
    throw new UnsupportedOperationException("Not needed for HW 8");

  }

  @Override
  public void halveSpeed(){
    throw new UnsupportedOperationException("Not needed for HW 8");

  }

  @Override
  public void resetSpeed(){
    throw new UnsupportedOperationException("Not needed for HW 8");

  }

  @Override
  public void restart(){
    providerModel.restart();
  }

  @Override
  public void toggleVisible(String shapeName) {
    providerModel.toggleVisible(shapeName);
  }

  @Override
  public boolean getPaused(){
    return !providerModel.running();
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
        shapesToIShapes(model.getShapeList());
        amComsToTransformations(model.getAnimationList());
        try {
          providerView = vFacProvider.createProviderView(type,
                  providerShapeListAll,
                  providerShapeListActive,
                  providerTransformList,
                  outFile,
                  ticksPerSecond,
                  this);
        } catch (IOException e){
          throw new IOException(e.getMessage());
        }
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


  private class RunAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      //if the animation is over, either reset and and start again or terminate draws
      //depending on looping behavior

        if (model.is)
        //int frames = 0;
        while (model.running()) {
          if (System.nanoTime() > nanosBetweenFrames + lastTime) {
            //frames++;
            //double timeDifference = (System.nanoTime() - lastTime) / 1000000000d;
            lastTime = System.nanoTime();
            //System.out.println("Frame: " + frames + " Tick: " + model.getTick()
            //        + " Time difference: " + timeDifference
            //        + " Actual FPS: " + 1d / timeDifference);
            try {
              runnableView.updateView(getCurrentShapes(), getStatus());
            } catch (IOException e) {
              throw new IllegalStateException("Could not render this frame, at tick "
                      + model.getTick() + ".");
            }
            if (!paused) {
              model.tick();
            }
          }
          if (System.nanoTime() > statusTime + 3000000000L) {
            status = "...";
          }
        }
        //System.out.println("looping + " + lastTime);

        while (!isLooping()) {
          paused = true;
          changeStatus("End of the animation");
          try {
            runnableView.updateView(getCurrentShapes(), getStatus());
          } catch (IOException e) {
            throwPopUpError("Could not render this frame.");
          }
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
            throwPopUpError("Sleep interrupted.");
          }
        }
        model.restart();
        changeStatus("Restarting");
      } while (true);







      }
    }


}
