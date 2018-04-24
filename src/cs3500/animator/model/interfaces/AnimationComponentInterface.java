package cs3500.animator.model.interfaces;

import java.util.List;

/**
 * This interface governs the implementation of any Animation Component. These objects
 * are the building blocks of AnimationModels.
 */
public interface AnimationComponentInterface extends Comparable<AnimationComponentInterface> {
  /**
   * Performs the entire animation component from start to finish.
   */
  void executeFull();

  /**
   * Performs the initial tick of the animation component. Will ensure that the shape starts from
   * the proper starting values.
   */
  void executeIncrementInitial();

  /**
   * Performs one tick of the animation component. Works with the current status of the shape (i.e,
   * may not be in the initial state).
   */
  void executeIncrement();

  /**
   * Performs one tick of the animation component. Works with the current status of the shape (i.e,
   * may not be in the initial state).
   */
  void executeDecrement();

  /**
   * Allows user to set animation by tick.
   */
  void setToTick(int startTick, int endTick);

  /**
   * Allows the animationComponent to be copied.
   *
   * @return The copy animationComponent
   */
  AnimationComponentInterface copy();

  /**
   * Allows the shape to be overwritten. Must be the same type of shape as the previous contained
   * shape or will throw exception.
   *
   * @param shape the new shape to be modified by the animationComponent.
   * @throws IllegalArgumentException If the new shape is not the same type as the old shape.
   */
  void setShape(ShapeInterface shape) throws IllegalArgumentException;

  /**
   * Allows the user to retrieve the type of animation component.
   *
   * @return the type of animation component as a string.
   */
  String getAnimationType();

  /**
   * Allows the user to retrieve the name of the target shape of the animation component.
   *
   * @return the name of the target shape as a string.
   */
  String getTargetName();

  /**
   * Allows the user to retrieve the target shape itself.
   *
   * @return the target shape itself.
   */
  ShapeInterface getTarget();

  /**
   * Allows the user to retrieve the designated start time of the animation component.
   *
   * @return the start time of the animation component as an int.
   */
  int getStartTime();

  /**
   * Allows the user to retrieve the designated end time of the animation component.
   *
   * @return the end time of the animation component as an int
   */
  int getEndTime();

  /**
   * Allows the user to retrieve the initial parameters of the component.
   *
   * @return A list of the initial parameters.
   */
  List<Object> getInitialParameters();

  /**
   * Allows the user to retrieve the final parameters of the component.
   *
   * @return A list of the final parameters.
   */
  List<Object> getFinalParameters();

  /**
   * Allows the user to compare two animation components.
   *
   * @param test the animation component to be compared to
   * @return 1 if parameter component has earlier start time, 0 if parameter component has
   * the same start time, -1 if parameter component has later start time.
   */
  int compareTo(AnimationComponentInterface test);

  /**
   * Allows the user to check equality against another animationComponentInterface type.
   *
   * @param test The object to be compared to
   * @return true if the values of the two are the same, false if not.
   */
  boolean equals(Object test);

  /**
   * Allows the user to retrieve the hashcode of the animationComponent.
   *
   * @returns the integer hashcode.
   */
  int hashCode();

  /**
   * Standard method that allows the animation to be given as text.
   *
   * @return String description of the animation.
   */
  String toString();

  /**
   * Allows the user to compare two animation components with tickPerSecond.
   *
   * @param ticksPerSecond ticks represented in one second.
   * @return String representation for the animation overview with ticksPerSecond.
   */
  String toStringTick(int ticksPerSecond);

  /**
   * Count how many attributes for svg.
   *
   * @return the number of attributes.
   */
  int countattributions();

  /**
   * Get the name of attributes.
   *
   * @return the String represent attributes.
   */
  String getattributename(int index);

  /**
   * Get the attribute value of "from".
   *
   * @return the String represent the beginning value of an attribute.
   */
  String attributeValueFrom(int index);

  /**
   * Get the attribute value of "to".
   *
   * @return the String represent the dest value of an attribute.
   */
  String attributeValueTo(int index);


  /**
   * Return the svg text form for the animation.
   *
   * @return the svg text form for the animation.
   */
  String getSvg(boolean isLoopback, int ticksPerSecond);

}
