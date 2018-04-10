package cs3500.animator.view.viewimplementations;


import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.view.interfaces.HybridViewInterface;
import cs3500.animator.view.VisualViewTypeAbstract;

public class TestHybridView extends VisualViewTypeAbstract implements HybridViewInterface {

  public boolean isRunning = false;
  public boolean isPaused = false;
  public boolean isRestarting = false;
  public boolean isLooping = false;
  public boolean isExporting = false;
  public boolean speedUp = false;
  public boolean speedDown = false;

  public List<ShapeInterface> blackListed = new ArrayList<>();

  public TestHybridView(ModelInsulatorInterface model, String outFile, int ticksPerSecond) {
    super(model, ticksPerSecond);
  }

  @Override
  public void setListeners(ActionListener buttons, KeyListener keys, ItemListener item) {
    //empty because not needed for testing
  }

  @Override
  public void resetFocus() {
    //empty because not needed for testing
  }

  @Override
  public void speedUp() {
    speedUp = true;
  }

  @Override
  public void speedDown() {
    speedDown = true;
  }

  @Override
  public void setLooping(boolean loops) {
    isLooping = loops;
  }

  @Override
  public void resume() {
    isRunning = true;
  }

  @Override
  public void pause() {
    isPaused = true;

  }

  @Override
  public void restart() {
    isRestarting = true;
  }

  @Override
  public void exportSVG() {
    isExporting = true;
  }

  @Override
  public void run() {
    //empty because not needed for testing
  }

  @Override
  public String viewText() {
    return "REPLACE ME";
  }

  @Override
  public String getViewType() {
    return "Hybrid View";
  }

  @Override
  public int getSpeed() {
    return 1000000000;
  }

  @Override
  public String getDestination() {
    return "REPLACE ME";
  }

  @Override
  public void selectOrUnseletShapes(String shapeName) {
    //empty because not needed for testing
  }
}




