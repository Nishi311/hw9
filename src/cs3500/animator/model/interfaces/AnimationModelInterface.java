package cs3500.animator.model.interfaces;

import java.util.List;
import java.util.Map;

/**
 * This interface governs the implementation of the Animation model. Provides functions related to
 * manipulating internal animation components and shapes and executing animation components.
 */
public interface AnimationModelInterface {

  /**
   * Adds an animation to an existing shape in the model.
   *
   * @param shapeName         The name of the shape to be used in the animation.
   * @param animationType     The type of animation to be performed on the shape.
   * @param animationDuration How long the animation will take to be completed on the shape.
   * @param parameters        Any parameters necessary to describe the animation.
   * @throws IllegalArgumentException If the shape has not been created, tries to perform an unknown
   *                                  animation type, if any of the parameters are invalid or if
   *                                  the resulting animation will result in a conflict.
   */
  void addAnimation(String shapeName, String animationType,
                    DurationInterface animationDuration, Object... parameters)
          throws IllegalArgumentException;

  /**
   * Much like addAnimation but instead allows the user to re-write an existing animationComponent.
   * Essentially just creates a new animationComponent and replaces the old one.
   *
   * @param listIndex         The number of the animation in to be edited.
   *                          {@link #getAnimationList()}
   * @param shapeName         The name of the shape to be used in the animation.
   * @param animationType     The type of animation to be performed on the shape.
   * @param animationDuration How long the animation will take to be completed on the shape.
   * @param parameters        Any parameters necessary to describe the animation.
   * @throws IllegalArgumentException If the shape has not been created, tries to perform an unknown
   *                                  animation type, if any of the parameters are invalid or if
   *                                  the resulting animation will result in a conflict.
   */
  void editAnimation(int listIndex, String shapeName,
                     String animationType, DurationInterface animationDuration,
                     Object... parameters) throws IllegalArgumentException;

  /**
   * Allows the user to add a shape to the model. Note, by default shapes will NOT be visible
   * and must be made visible via an animationComponent.
   *
   * @param shapeName  The name of the shape to be created.
   * @param shapeType  the type of shape to be created.
   * @param color      The color of the shape to be created.
   * @param pos        The initial position of the shape. (Reference point depends on shapeType)
   * @param orient     The initial orientation of the shape. (0 is up, 180 is down).
   * @param layer      The layer in which the shape will be represented
   * @param parameters Any parameters necessary to describe the animation.
   * @throws IllegalArgumentException If the shapeName conflicts with an existing shape, tries to
   *                                  create an unknown shape type, if any of the parameters
   *                                  (including color and pos) are invalid.
   */
  void addShape(String shapeName, String shapeType, ColorClassInterface color,
                Position2DInterface pos, float orient, int layer, Object... parameters)
          throws IllegalArgumentException;


  /**
   * Allows the user to modify the initial parameters of a shape. Note: It is NOT possible to
   * modify a shape's name, type, or visibility but Color, Pos, and other parameters are fair game.
   *
   * @param shapeName      The name of the shape to be modified.
   * @param parameterName  The name of the parameter to be modified.
   * @param parameterValue The value of the parameter to be set.
   * @throws IllegalArgumentException If the shape does not exist, if the shape does not have that
   *                                  parameter (or is an illegal parameter) or if the parameter
   *                                  value does not match to the type specified by the
   *                                  parameterName
   */
  void editShape(String shapeName, String parameterName, Object parameterValue)
          throws IllegalArgumentException;

  /**
   * Allows for the removal of a shape from the model. Note: Removing a shape will also remove any
   * animationComponents associated with it.
   *
   * @param shapeName The name of the shape to be removed
   * @throws IllegalArgumentException If no shape with that name is present in the model.
   */
  void removeShape(String shapeName) throws IllegalArgumentException;

  /**
   * Allows for the removal of an animationComponent from the model. Indexes start fom one.
   *
   * @param listIndex the index of the animationComponent to be removed. {@link #getAnimationList()}
   * @throws IllegalArgumentException if the listIndex is negative or exceeds the number of
   *                                  animation components in the model.
   */
  void removeAnimation(int listIndex) throws IllegalArgumentException;

  /**
   * Allows the user to retrieve the current list of animations in the model. Animations Components
   * will be in chronological order.
   *
   * @return A list of AnimationComponentInterface objects that represent all animation components
   *         in the model.
   */
  List<AnimationComponentInterface> getAnimationList();

  /**
   * Allows the user to retrieve the current list of shapes in the model.
   *
   * @return A list of ShapeInterface objects that represents all shapes in the model.
   */
  List<ShapeInterface> getShapeList();

  /**
   * Allows the user to retrieve the current map of animations as grouped by their starting times.
   *
   * @return A map that contains lists of AnimationComponentInterfaces grouped by their starting
   *         times.
   */
  Map<Integer, List<AnimationComponentInterface>> getStartToAnimationMap();

  /**
   * Allows the user to retrieve the current map of animations as grouped by their ending times.
   *
   * @return A map that contains lists of AnimationComponentInterfaces grouped by their ending
   *         times.
   */
  Map<Integer, List<AnimationComponentInterface>> getEndToAnimationMap();

  /**
   * Allows the user to retrieve the map of shapes and the animations they are attached to.
   *
   * @return A map that contains a list of AnimationComponentInterfaces grouped by their
   *         shape.
   */
  Map<String, List<AnimationComponentInterface>> getShapeNameToAnimationMap();

  /**
   * Allows the user to retrieve the map of layers and their corresponding shapes.
   *
   * @return A map that contains a key list of layer numbers and entries corresponding to the
   *         shapes in each layer.
   */
  Map<Integer, List<ShapeInterface>> getLayerMap();

  /**
   * Allows the user to completely wipe the model of all animationComponents. Will keep any shapes.
   */
  void clearAnimationList();

  /**
   * Allows the user to completely wipe the model of all shapes. Will eliminate any
   * animationComponents as well.
   */
  void clearShapeList();

  /**
   * Provides a detailed overview of what the model will output if run from start to finish using
   * the {@link #runFull()} method.
   *
   * @return A formatted string with the resulting animation sequence in text.
   */
  String getOverview();

  /**
   * Provides a detailed overview of what the model will output if run from start to finish using
   * the {@link #runFull()} method.
   *
   * @param ticksPerSecond ticks the animation is running per second
   * @return A string representation for the animation with tick value for time.
   */
  String getOverviewTick(int ticksPerSecond);

  /**
   * Provides a detailed overview of the status of all shapes in the model. Requires
   * {@link #runFrom(int, int)} or {@link #runFull()} to be run first.
   *
   * @return A formatted string with the current status of all shapes.
   */
  String getCurrentState();

  /**
   * Runs the entire list of animationComponents stored in the model from time = 0 to the
   * latest end time in the list.
   */
  void runFull();

  /**
   * Will carry out all animationComponent orders from the specified startTime to the specified
   * End Time. All animation components that start before the startTime will be either completed (
   * if their end time is before startTime) or in progress (if their end time is after startTime).
   *
   * @param startTime The time from which animationComponent actions will be carried out.
   * @param endTime   The time at which animationComponent actions will cease.
   */
  void runFrom(int startTime, int endTime);


  /**
   * Will reset the status of all shapes to their original state. That is, when they were first
   * created in the model.
   */
  void restartAnimation();

}
