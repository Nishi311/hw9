package cs3500.animator.provider;

import java.util.List;


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
   * Get the starting values for all of the shapes in the animation, maintaining the current
   * visibility.
   *
   * @return A list of all the shapes in the animation as they first appeared
   */
  List<IAnimShape> getStartShapes();

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

  /**
   * Restart the animation from the beginning.
   */
  void restart();

  /**
   * Change the selected shape's visibility, from visible to invisible or vice versa.
   * @param shapeName   The shape to be turned visible or invisible
   */
  void toggleVisible(String shapeName);

  /**
   * See if the animation is currently paused.
   * @return  True if the animation is paused, false if it is playing
   */
  boolean getPaused();

  /**
   * Change the status String to the given string.
   * @param s  The string to make the status
   */
  void changeStatus(String s);

  /**
   * Return the status String, describing the state of the animation.
   * @return The string representing the current status
   */
  String getStatus();
}

