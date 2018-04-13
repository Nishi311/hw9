package cs3500.animator.converters;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.controller.controllerimplementations.ControllerWithHybrid;
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
import cs3500.animator.provider.IController;
import cs3500.animator.provider.ITransformation;
import cs3500.animator.provider.view.IViewable;
import cs3500.animator.view.ViewTypes;
import cs3500.animator.view.interfaces.HybridViewInterface;

public class ProviderController extends ControllerWithHybrid implements KeyListener, ActionListener,
        ItemListener, IController {

  private IViewable providerView;


  private List<IAnimShape> providerShapeListAll;
  private List<IAnimShape> providerShapeListActive;
  private List<ITransformation> providerTransformList;

  private List<ShapeInterface> shapeList;
  private List<AnimationComponentInterface> animationList;

  private Map<String, List<AnimationComponentInterface>> shapeToAnimation = new HashMap<>();
  private Map<String, IAnimShape> shapeToShapeObject = new HashMap<>();

  private boolean isPaused = false;
  private boolean doesLoop = false;

  private String status;

  private ViewFactoryProviderInterface vFacProvider;

  public ProviderController(AnimationModelInterface model, ViewFactoryProviderInterface vFac){
    super (model, vFac);
    this.vFacProvider = vFac;
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

  }

  @Override
  public void toggleVisible(String shapeName) {

  }

  @Override
  public boolean getPaused(){
    return this.isPaused;
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

      if (animation instanceof ColorChange){
        providerTransformList.add(new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName())));
      }
      else if (animation instanceof PositionChange){
        providerTransformList.add(new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName())));
      }
      else if (animation instanceof ScaleChangeRR || animation instanceof ScaleChangeWH){
        providerTransformList.add(new ProviderColorChange(animation,
                shapeToShapeObject.get(animation.getTargetName())));
      }
    }
  }
}
