package cs3500.animator.provider;

import java.util.List;

/**
 * This is the interface for an animation model. Classes implementing this
 * interface must start the animation, store animshape, keep track of TIME,
 * transform animshape, and end the animation.
 */
public interface IAnimationModel {

  /**
   * Add a shape to the model at any point before or during the animation.
   *
   * @param shape the shape to be added
   */
  void addShape(IAnimShape shape);

  /**
   * Add a transformation to the model at any point before or during the animation.
   *
   * @param trans the transformation to be performed
   */
  void addTransformation(ITransformation trans);

  /**
   * Begin the animation.
   */
  void startAnim();

  /**
   * Returns the list of animshape the model is currently displaying.
   *
   * @return list of all active AnimShapes
   */
  List<IAnimShape> currentShapes();

  /**
   * Returns the list of transformations the model is currently putting in effect.
   *
   * @return list of all active Transformations
   */
  List<ITransformation> currentTransformations();

  /**
   * Returns the list of animshapes in the model.
   *
   * @return list of all AnimShapes
   */
  List<IAnimShape> getShapes();

  /**
   * Returns the list of all transformations in the model.
   *
   * @return list of all Transformations
   */
  List<ITransformation> getTransformations();

  /**
   * Returns an int representing the current tick of the animation. Starts at 1.
   *
   * @return int representing the current TICK
   */
  int getTick();

  /**
   * Returns an int representing the last tick of the animation.
   *
   * @return int representing the last TICK that will be played
   */
  int getEndTick();

  /**
   * Run one tick of the animation.
   * Bring new animshape in, throw dead animshape out, move transforming animshape. Advance
   * TICK by one
   */
  void tick();

  /**
   * End the animation.
   */
  void endAnim();

  /**
   * Is the animation running.
   *
   * @return True if started, false if not started
   */
  boolean running();

  /**
   * Restart the animation from TICK 1.
   */
  void restart();

  /**
   * Give a verbose description of the animation as a whole. Every shape and every transformation.
   *
   * @return The animation's life story
   */
  String getAnimationString();

  /**
   * Describe the entire animation.
   * (This note is for documentation only, it will not enforce classes implementing this
   * interface to override its inherent toString() method, but all such classes should.)
   *
   * @return A verbose description for every shape, and action on those animshape,
   * shown and performed.
   */
  String toString();

  //TODO
  void toggleVisible(String shapeName);
}
