package cs3500.animator.controller.controllerimplementations;

import cs3500.animator.controller.interfaces.ControllerInterface;
import cs3500.animator.view.interfaces.ViewFactoryInterface;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.view.interfaces.ViewInterface;
import cs3500.animator.view.ViewTypes;

/**
 * Abstract class the defines basic methods and parameters for all controllerInterface
 * implementations to use.
 */
public abstract class ControllerAbstract implements ControllerInterface {
  protected AnimationModelInterface model;
  protected ViewInterface view = null;
  protected ViewFactoryInterface vFac;

  /**
   * The basic constructor.
   *
   * @param model The model that will be filled out with the parseInput() command native to
   *              each implementation of ControllerInterface.
   * @param vFac  The viewFactory that each parseInput() method will use to build the internal view.
   */
  public ControllerAbstract(AnimationModelInterface model, ViewFactoryInterface vFac) {
    this.model = model;
    this.vFac = vFac;
  }


  @Override
  public ViewInterface getView() throws IllegalStateException {
    if (view == null) {
      throw new IllegalStateException("View has not been created. MUST run parseInput() method "
              + "first.");
    }
    return view;
  }

  //helper function that retrieves the proper view type from the given string.
  protected abstract ViewTypes viewTypeCheck(String temp);

}
