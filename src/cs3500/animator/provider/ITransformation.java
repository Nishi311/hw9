package cs3500.animator.provider;

/**
 * This is the interface for a transformation object, which holds information relating to how
 * and when a certain shape should change.
 */
public interface ITransformation {

  /**
   * Virtual copy constructor, implementing the prototype pattern.
   *
   * @return A deep copy of this transformation
   */
  ITransformation makeCopy();

  /**
   * Check if the shape should be displayed in the animation, given what tick it is.
   *
   * @param tick current tick in the animation
   * @return True if the move is active, false if it is not
   */
  boolean isActive(int tick);

  /**
   * Activate the transformation if it to be performed on this tick.
   *
   * @param tick current tick in the animation
   */
  void transform(int tick);

  /**
   * Getter for name, the name of the shape as the animation refers to it.
   *
   * @return String representing the shape's name
   */
  String getShapeName();

  /**
   * Setter for the shape, which the transformation will now refer to.
   *
   * @param shape IAnimShape that the transformation will now refer to
   */
  void setShape(IAnimShape shape);

  /**
   * Getter for startTick, the tick of the animation that the transformation starts on.
   *
   * @return int representing the first tick this transformation takes place
   */
  int getStartTick();

  /**
   * Getter for endTick, the tick of the animation that the transformation ends on.
   *
   * @return int representing the last tick this transformation takes place
   */
  int getEndTick();

  /**
   * Check if this transformation conflicts with an already existing transformation,
   * i.e. trying to move the same shape in different directions at the same time.
   *
   * @param other Another transformation
   * @return True if there is no conflict, false if there is
   */
  boolean noConflict(ITransformation other);

  /**
   * Build the strings necessary for the human readable version of the move.
   */
  String[] buildStrings();

  /**
   * Gets the information of the transformation in a array in the order [shapeName, tVerb,
   * startString, endString, startTick, endTick] to be used when comparing transformation in the
   * view.
   *
   * @return An array of transformation attributes
   */
  String[] getInfo();

  /**
   * Gets the starting state of the transformation. ie. Move: starting position,
   * ColorChange: starting color, Scale: starting size(width and height, xradius, yradius).
   *
   * @return The starting state of the transformation
   */
  Double[] getStart();

  /**
   * Gets the end state of the transformation. ie. Move: end position, ColorChange: end color,
   * Scale: end size(width and height, xradius, yradius).
   *
   * @return The end state of the transformation
   */
  Double[] getEnd();

}