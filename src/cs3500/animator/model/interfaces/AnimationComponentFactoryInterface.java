package cs3500.animator.model.interfaces;

/**
 * This interface is used to guide any animation component factories built. The sole method,
 * create(), is all that's needed for the factory.
 */
public interface AnimationComponentFactoryInterface {
  /**
   * The create method is responsible for creating an AnimationComponentInterface object from
   * the given parameters. The implementation of this method will determine what kinds of
   * animation components the user can make.
   *
   * @param targetShape       The shape interface which this animationComponent will affect.
   * @param animationType     The type of animation that will be performed on the shape.
   * @param animationDuration The duration of the animation to be performed.
   * @param parameters        Any additional parameters needed to execute the animation. Dictated by
   *                          animation type.
   * @return An AnimationComponentInterface of the type designated by the parameters.
   * @throws IllegalArgumentException if the animationType is not known, or if any of the parameters
   *                                  are invalid for that animationType
   */
  AnimationComponentInterface create(ShapeInterface targetShape, String animationType,
                                     DurationInterface animationDuration, Object... parameters)
          throws IllegalArgumentException;
}
