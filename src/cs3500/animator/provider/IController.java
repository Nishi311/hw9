package cs3500.animator.provider;
import java.util.List;

import cs3500.animator.animshape.IAnimShape;
import cs3500.animator.transformations.ITransformation;

/**
 * This is the interface for controller objects, which control animations. Controllers act as an
 * intermediary between the view and the model so they can be as isolated from each other as
 * possible, and it listens for user input.
 */
public interface IController {

  /**
   * Build the model that will keep track of what the animation should display at any given tick.
   */
  void buildModel();

  /**
   * Build the view that will display the shapes and transformations in the format that is chosen:
   * Text, SVG, Visual, or Interactive.
   */
  void buildView();

  String getStatus();

  /**
   * Pause the animation. Continue displaying the current frame, do not move to the next one.
   */
  void pauseAnimation();

  /**
   * Change the current speed to the given speed.
   *
   * @param speed Int to change the number of frames per second to
   */
  void changeSpeed(int speed);

  /**
   * Get the current speed of the animation.
   *
   * @return int representing the speed of the animation
   */
  int getSpeed();

  /**
   * Get all of the shapes in the animation.
   *
   * @return A list of all the shapes in the animation
   */
  List<IAnimShape> getAllShapes();

  /**
   * Get all of the transformations in the animation.
   *
   * @return A list of all the transformations in the animation
   */
  List<ITransformation> getAllTransformations();

  /**
   * Get all of the currently active shapes in the animation.
   *
   * @return A list of all active shapes in the animation
   */
  List<IAnimShape> getCurrentShapes();

  /**
   * Checks whether the animation is going to loop at the end.
   *
   * @return the boolean flag that checks if the animation is looping
   */
  boolean isLooping();

  /**
   * Gets the end tick of the animation.
   * @return the final tick of the animation after every shape disappears
   */
  int getEndTick();

//  /**
//   * Gets the boolean that tells whether that shape is visible or not
//   *
//   * @return an array of booleans that tells whether the shape is visible
//   */
//  boolean[] visibleShape();


  /**
   * Let the animation loop back to the beginning when it's over, or turn this feature off.
   */
  void toggleLoopBack();

  /**
   * Double the speed of the animation.
   */
  public void doubleSpeed();

  /**
   * Halve the speed of the animation.
   */
  public void halveSpeed();

  /**
   * Reset the speed of the animation to the default speed.
   */
  public void resetSpeed();
  //TODO

  void restart();
  //TODO

  void toggleVisible(String shapeName);
  //TODO

  boolean getPaused();
  //TODO

  void changeStatus(String s);
}

