package cs3500.animator.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.converters.transforms.ProviderColorChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ColorChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.PositionChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeRR;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeWH;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.IAnimationModel;
import cs3500.animator.provider.ITransformation;

public class ProviderModel implements IAnimationModel {

  private List<IAnimShape> providerShapeListAll;
  private List<IAnimShape> providerShapeListActive;
  private List<ITransformation> providerTransformListAll;
  private List<ITransformation> providerTransformListActive;


  private List<ShapeInterface> shapeList;
  private List<AnimationComponentInterface> animationList;

  private Map<String, List<AnimationComponentInterface>> shapeToAnimation = new HashMap<>();
  private Map<String, IAnimShape> shapeToShapeObject = new HashMap<>();

  private int tick = 1;
  private int endTick = 0;

  private boolean isPaused = false;
  private boolean doesLoop = false;

  public ProviderModel(AnimationModelInterface origModel){
    shapesToIShapes(origModel.getShapeList());
    List<AnimationComponentInterface> temp = origModel.getAnimationList();
    amComsToTransformations(temp);
    endTick = temp.get(temp.size()-1).getEndTime();
  }


  @Override
  public void addShape(IAnimShape shape) {
    throw new UnsupportedOperationException("Not required for HW 8");
  }

  @Override
  public void addTransformation(ITransformation trans) {
    throw new UnsupportedOperationException("Not required for HW 8");
  }

  @Override
  public void startAnim() {
    this.isPaused = true;
  }

  @Override
  public List<IAnimShape> currentShapes() {
    providerShapeListActive.clear();
    for (IAnimShape s: providerShapeListAll){
      if (s.isActive(tick)){
        providerShapeListActive.add(new ProviderAnimShape(s));
      }
    }
    return providerShapeListActive;
  }

  @Override
  public List<ITransformation> currentTransformations() {
    providerTransformListActive.clear();
    for (ITransformation t: providerTransformListAll){
      if (t.isActive(tick)){
        providerTransformListActive.add(t.makeCopy());
      }
    }
    return providerTransformListActive;
  }

  @Override
  public List<IAnimShape> getShapes() {
    List<IAnimShape> output = new ArrayList<>();
    for (IAnimShape s: providerShapeListAll){
      output.add(new ProviderAnimShape(s));
    }

    return output;
  }

  @Override
  public List<ITransformation> getTransformations() {
    List<ITransformation> output = new ArrayList<>();
    for (ITransformation t: providerTransformListAll){
      output.add(t.makeCopy());
    }
    return output;
  }

  @Override
  public int getTick() {
    return tick;
  }

  @Override
  public int getEndTick() {
    return endTick;
  }

  @Override
  public void tick() {

  }

  @Override
  public void endAnim() {

  }

  @Override
  public boolean running() {
    return false;
  }

  @Override
  public void restart() {

  }

  @Override
  public String getAnimationString() {
    return null;
  }

  @Override
  public void toggleVisible(String shapeName) {

  }

  private void shapesToIShapes(List<ShapeInterface> conversion) {
    for (ShapeInterface shape : conversion) {
      List<AnimationComponentInterface> animations = shapeToAnimation.get(shape.getName());

      IAnimShape temp = new ProviderAnimShape(shape, animations.get(0),
              animations.get(animations.size()-1));
      shapeToShapeObject.put(temp.getName(), temp);
      providerShapeListAll.add(temp);
    }

    for (IAnimShape shape: providerShapeListAll){
      if (shape.isActive(1)){
        providerShapeListActive.add(shape);
      }
    }

  }

  private void amComsToTransformations(List<AnimationComponentInterface> conversion) {
    for (AnimationComponentInterface animation : conversion){
      ITransformation current;
      if (animation instanceof ColorChange){
        current = new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName()));
        providerTransformListAll.add(current);

        if (current.isActive(1)){
          providerTransformListActive.add(current.makeCopy());
        }

      }
      else if (animation instanceof PositionChange){
        current = new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName()));
        providerTransformListAll.add(current);
        if (current.isActive(1)){
          providerTransformListActive.add(current.makeCopy());
        }

      }
      else if (animation instanceof ScaleChangeRR || animation instanceof ScaleChangeWH){
        current = new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName()))
        providerTransformListAll.add(current);
        if (current.isActive(1)){
          providerTransformListActive.add(current.makeCopy());
        }

      }
    }
  }


}
