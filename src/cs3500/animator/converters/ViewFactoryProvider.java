package cs3500.animator.converters;

import java.io.IOException;
import java.util.List;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.IController;
import cs3500.animator.provider.ITransformation;
import cs3500.animator.provider.view.IViewable;
import cs3500.animator.view.ViewFactoryWithHybrid;
import cs3500.animator.view.interfaces.ViewFactoryProviderInterface;

public class ViewFactoryProvider extends ViewFactoryWithHybrid
        implements ViewFactoryProviderInterface {
    List<IAnimShape> providerShapeList;
    List<ITransformation> providerTransformList;

    List<ShapeInterface> shapeList;
    List<AnimationComponentInterface>

  @Override
  public IViewable createProviderView(String viewType, ModelInsulatorInterface model, String output,
                                      int ticksPerSecond, IController) throws IOException{
    IViewable toReturn;

    shapesToIshapes(model.getShapeList());
    amComsToTransformations(model.getAnimationList());

    switch(viewType){
      case "text2":
        break;
      case "svg2":
        break;
      case "visual2":
        break;
      case "provider":
        break;
      default:
        break;
    }
    return toReturn;
  }

  private void shapesToIshapes(List<ShapeInterface> conversion){
    for(ShapeInterface shape: conversion){
      if (ShapeInterface instanceof Rectangle){

      }
    }
  }

  private void amComsToTransformations(List<AnimationComponentInterface> conversion){

  }


}
