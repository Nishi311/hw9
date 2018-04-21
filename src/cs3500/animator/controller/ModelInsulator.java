package cs3500.animator.controller;

import java.util.List;
import java.util.Map;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * Implementation of the ModelInsulatorInterface. Contains a full AnimationModel type object but
 * grants access to the READ-ONLY methods.
 */
public class ModelInsulator implements ModelInsulatorInterface {
  private AnimationModelInterface model;

  /**
   * Basic constructor for the ModelInsulator, needs only a model to hold onto.
   *
   * @param origModel The model that will be insulated.
   * @throws IllegalArgumentException if origModel is null.
   */
  public ModelInsulator(AnimationModelInterface origModel) throws IllegalArgumentException {
    if (origModel == null) {
      throw new IllegalArgumentException("Cannot accept null model");
    }
    this.model = origModel;
  }

  @Override
  public List<AnimationComponentInterface> getAnimationList() {
    return this.model.getAnimationList();
  }

  @Override
  public List<ShapeInterface> getShapeList() {
    return this.model.getShapeList();
  }

  @Override
  public Map<Integer, List<AnimationComponentInterface>> getStartToAnimationMap() {
    return this.model.getStartToAnimationMap();
  }

  @Override
  public Map<Integer, List<AnimationComponentInterface>> getEndToAnimationMap() {
    return this.model.getEndToAnimationMap();
  }

  @Override
  public Map<String, List<AnimationComponentInterface>> getShapeNameToAnimationMap() {
    return this.model.getShapeNameToAnimationMap();
  }

  @Override
  public Map<Integer, List<ShapeInterface>> getLayerMap() {
    return this.model.getLayerMap();
  }

  public String getOverview() {
    return this.model.getOverview();
  }

  public String getOverviewTick(int ticksPerSecond) {
    return this.model.getOverviewTick(ticksPerSecond);
  }

}
