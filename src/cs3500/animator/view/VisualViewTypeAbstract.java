package cs3500.animator.view;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.view.interfaces.ViewInterface;

/**
 * Abstract class that contains joint methods used for both VisualView and HybridView including
 * frame set up, reorganization of maps retrieved from read-only model (deep copies given sort
 * of mess with the animation logic) and actual tick-by-tick animation.
 */
public abstract class VisualViewTypeAbstract extends JFrame implements ViewInterface {
  //general parameters
  protected ModelInsulatorInterface model;


  //visual maps and lists
  protected List<ShapeInterface> shapeList;
  protected List<AnimationComponentInterface> animationList;

  protected Map<Integer, List<ShapeInterface>> layerMap;
  protected Map<Integer, List<AnimationComponentInterface>> startTimeMap;
  protected Map<Integer, List<AnimationComponentInterface>> endTimeMap;

  protected List<AnimationComponentInterface> runningAnimationList = new ArrayList<>();
  protected List<AnimationComponentInterface> beginningAnimationList = new ArrayList<>();
  protected List<AnimationComponentInterface> endingAnimationList = new ArrayList<>();

  protected ArrayList<Integer> startTicks;
  protected ArrayList<Integer> endTicks;

  //controlling parameters
  protected int ticksPerSecond;
  protected long milliPerTick;

  /**
   * Basic constructor that retrieves all necessary information from the read-only model and re-
   * organizes it so that all the references in the deep-copies match up again. Sets up frame.
   *
   * @param model          The read-only model used by the view.
   * @param ticksPerSecond The number of ticks per second the animation will be played at.
   */
  protected VisualViewTypeAbstract(ModelInsulatorInterface model, int ticksPerSecond) {
    this.model = model;

    this.ticksPerSecond = ticksPerSecond;
    this.milliPerTick = 1000 / ticksPerSecond;

    this.shapeList = model.getShapeList();
    this.animationList = model.getAnimationList();
    this.startTimeMap = model.getStartToAnimationMap();
    this.endTimeMap = model.getEndToAnimationMap();
    this.layerMap = model.getLayerMap();
    reorganizeMapsAndLists();

    //retrieves ticks where animation components begin
    startTicks = new ArrayList<>(startTimeMap.keySet());
    //retrieves ticks where animation components end
    endTicks = new ArrayList<>(endTimeMap.keySet());

    Collections.sort(startTicks);
    Collections.sort(endTicks);

    this.setTitle("Animation!");
    this.setSize(500, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //use a border layout with drawing panel in center and status output in south
    this.setLayout(new BorderLayout());
    //make window visible

  }

  /**
   * Checks all animations and based on the tick either ends them, starts them, or continues them
   * and then redraws the panel.
   *
   * @param currentTick The tick that will be executed by the function.
   * @param dPan        The panel that will be drawn to upon updating of shape information
   *                    by animations.
   */
  protected void runForOneTick(int currentTick, JPanel dPan) {
    //represents how long all the animations components  required to be done during the tick
    //actually took to compute and render.
    //execute a tick on any animations currently running.
    if (!runningAnimationList.isEmpty()) {
      for (AnimationComponentInterface runningAnimation : runningAnimationList) {
        runningAnimation.executeIncrement();
      }
    }

    //if starting tick is the current tick, add these animations into the list of currently
    //running animations and use the initial method to ensure proper starting values.
    if (startTimeMap.containsKey(currentTick)) {
      beginningAnimationList = new ArrayList<>(startTimeMap.get(currentTick));
      for (AnimationComponentInterface beginningAnimation : beginningAnimationList) {
        beginningAnimation.executeIncrementInitial();
      }
      runningAnimationList.addAll(beginningAnimationList);
    }

    //remove any animations ending during this tick from the list of currently running animations.
    if (endTimeMap.containsKey(currentTick)) {
      endingAnimationList = new ArrayList<>(endTimeMap.get(currentTick));
      for (AnimationComponentInterface endingAnimation : endingAnimationList) {
        if (runningAnimationList.contains(endingAnimation)) {
          runningAnimationList.remove(endingAnimation);
        }
      }
    }

    //update the panel with new iterations of shapes using each shapes internal draw method.
    dPan.repaint();
  }

  /**
   * Reorganizes maps and lists so that the animationList AnimationComponents
   * point to the new shape references in the shapeList (will have been modified
   * by the deep copy in the model get() methods). Then remaps the startTimeMap
   * and endTimeMap to do reference the new AnimationComponent references.
   */
  private void reorganizeMapsAndLists() {
    //sets all animationLists to reference proper shape copies.
    for (AnimationComponentInterface a : animationList) {
      int index = shapeList.indexOf(a.getTarget());
      a.setShape(shapeList.get(index));
    }

    reorganizeHelper(startTimeMap);
    reorganizeHelper(endTimeMap);

    //Same code as reorganizeHelper but for re-organizing with ShapeInterfaces
    for (Map.Entry<Integer, List<ShapeInterface>> entry : layerMap.entrySet()) {
      //make new list of AmComs with updated references
      List<ShapeInterface> newList = new ArrayList<>();
      for (ShapeInterface a : entry.getValue()) {
        int index = shapeList.indexOf(a);
        a = shapeList.get(index);
        newList.add(a);
      }
      //replace old list with new list of updated references.
      layerMap.put(entry.getKey(), newList);
    }
  }

  /**
   * Reorganizes maps and lists so that the animationList AnimationComponents
   * point to the new shape references in the shapeList (will have been
   * modified by the deep copy in the model get() methods).
   *
   * @param map map of integer and animation components to iterate through
   */
  private void reorganizeHelper(Map<Integer, List<AnimationComponentInterface>> map) {
    for (Map.Entry<Integer, List<AnimationComponentInterface>> entry : map.entrySet()) {
      //make new list of AmComs with updated references
      List<AnimationComponentInterface> newList = new ArrayList<>();
      for (AnimationComponentInterface a : entry.getValue()) {
        int index = animationList.indexOf(a);
        a = animationList.get(index);
        newList.add(a);
      }
      //replace old list with new list of updated references.
      map.put(entry.getKey(), newList);
    }
  }
}
