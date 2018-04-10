package cs3500.animator.view.interfaces;


/**
 * Interface for the view, provides functionality necessary to create and run any model given
 * to the view.
 */
public interface ViewInterface {
  /**
   * The meat of the view, allows for the execution of a model's list of animations. What this
   * execution entails depends on the view type.
   */
  void run();

  /**
   * Allows the text of view to be returned if necessary. Used for text-based views only.
   *
   * @return A string representing the execution of an animation
   */
  String viewText();

  /**
   * Allows the user to determine what kind of view this is.
   *
   * @return The type of view as a string.
   */
  String getViewType();

  /**
   * Allows the user to retrieve the number of ticks per second the view is set to.
   *
   * @return The number of ticks per second in the view.
   */
  int getSpeed();

  /**
   * Allows the user to retrieve the output destination of a view.
   *
   * @return The destination of a view as a string.
   */
  String getDestination();
}
