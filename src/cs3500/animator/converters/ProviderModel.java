package cs3500.animator.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.converters.transforms.ProviderColorChange;
import cs3500.animator.converters.transforms.ProviderPositionChange;
import cs3500.animator.converters.transforms.ProviderScaleChange;
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


  private List<IAnimShape> providerShapeBackup = new ArrayList<>();

  private List<IAnimShape> providerShapeListAll = new ArrayList<>();
  private List<IAnimShape> providerShapeListActive = new ArrayList<>();
  private List<ITransformation> providerTransformListAll = new ArrayList<>();

  private Map<String, List<AnimationComponentInterface>> shapeToAnimation = new HashMap<>();
  private Map<String, IAnimShape> shapeToShapeObject = new HashMap<>();
  private Map<String, IAnimShape> shapeToShapeObjectBackup = new HashMap<>();

  private int tick = 0;
  private int endTick = 0;

  private boolean isPaused = false;
  private boolean doesLoop = false;

  public ProviderModel(AnimationModelInterface origModel){
    shapeToAnimation = origModel.getShapeNameToAnimationMap();
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
    List<ITransformation> output = new ArrayList<>();
    for (ITransformation t: providerTransformListAll){
      if (t.isActive(tick)){
        output.add(t);
      }
    }
    return output;
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
    tick++;
    for (ITransformation t: currentTransformations()){
      t.transform(tick);
    }

  }

  @Override
  public void endAnim() {
  }

  @Override
  public boolean running() {
    return !isPaused;
  }

  @Override
  public void restart() {
    tick = 0;
    providerShapeListAll.clear();
    for (IAnimShape s: providerShapeBackup){
      ProviderAnimShape temp = new ProviderAnimShape(s);
      providerShapeListAll.add(temp);
      shapeToShapeObject.put(s.getName(), temp);
    }
    for (ITransformation t: providerTransformListAll){
      t.setShape(shapeToShapeObject.get(t.getShapeName()));
    }
  }

  @Override
  public String getAnimationString() {
    return null;
  }

  @Override
  public void toggleVisible(String shapeName) {
    if (shapeToShapeObject.containsKey(shapeName)){
      shapeToShapeObject.get(shapeName).toggleVisible();
      shapeToShapeObjectBackup.get(shapeName).toggleVisible();
    }
  }

  private void shapesToIShapes(List<ShapeInterface> conversion) {
    for (ShapeInterface shape : conversion) {

      List<AnimationComponentInterface> animations = shapeToAnimation.get(shape.getName());
      IAnimShape temp = new ProviderAnimShape(shape, animations.get(0),
              animations.get(animations.size() - 1));

      shapeToShapeObject.put(temp.getName(), temp);
      providerShapeListAll.add(temp);

      IAnimShape temp2 = new ProviderAnimShape(shape, animations.get(0),
              animations.get(animations.size() - 1));

      providerShapeBackup.add(temp2);
      shapeToShapeObjectBackup.put(temp2.getName(), temp2);
    }
  }

  private void amComsToTransformations(List<AnimationComponentInterface> conversion) {
    for (AnimationComponentInterface animation : conversion){
      ITransformation current;
      if (animation instanceof ColorChange){
        current = new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName()));
        providerTransformListAll.add(current);

      }
      else if (animation instanceof PositionChange){
        current = new ProviderPositionChange(animation,
                shapeToShapeObject.get(animation.getTargetName()));
        providerTransformListAll.add(current);

      }
      else if (animation instanceof ScaleChangeRR || animation instanceof ScaleChangeWH){
        current = new ProviderScaleChange(animation,
                shapeToShapeObject.get(animation.getTargetName()));
        providerTransformListAll.add(current);

      }
    }
  }

}

