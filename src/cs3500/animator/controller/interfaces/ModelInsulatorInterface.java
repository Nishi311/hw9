package cs3500.animator.controller.interfaces;

import java.util.List;
import java.util.Map;

import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * Insulating interface that will allow an AnimationModelInterface object to be passed to the view
 * without the view having any access to important modification functions. The implementation
 * of these classes should simply be the calling of the model methods with the same name.
 */
public interface ModelInsulatorInterface {
  /**
   * See {@link AnimationModelInterface#getAnimationList()}.
   *
   * @return {@link AnimationModelInterface#getAnimationList()}
   */
  List<AnimationComponentInterface> getAnimationList();

  /**
   * See {@link AnimationModelInterface#getShapeList()}.
   *
   * @return {@link AnimationModelInterface#getShapeList()}
   */
  List<ShapeInterface> getShapeList();

  /**
   * See {@link AnimationModelInterface#getStartToAnimationMap()}.
   *
   * @return {@link AnimationModelInterface#getStartToAnimationMap()}
   */
  Map<Integer, List<AnimationComponentInterface>> getStartToAnimationMap();

  /**
   * See {@link AnimationModelInterface#getEndToAnimationMap()}.
   *
   * @return {@link AnimationModelInterface#getEndToAnimationMap()}
   */
  Map<Integer, List<AnimationComponentInterface>> getEndToAnimationMap();

  /**
   * See {@link AnimationModelInterface#getShapeNameToAnimationMap()}.
   *
   * @return see {@link AnimationModelInterface#getShapeNameToAnimationMap()}.
   */
  Map<String, List<AnimationComponentInterface>> getShapeNameToAnimationMap();

  /**
   * see {@link AnimationModelInterface#getOverview()}.
   *
   * @return {@link AnimationModelInterface#getOverview()}.
   */
  String getOverview();

  /**
   * {@link AnimationModelInterface#getOverviewTick(int ticksPerSecond)} .
   *
   * @param ticksPerSecond The number of ticks represented by 1 second.
   * @return See {@link AnimationModelInterface#getOverviewTick(int ticksPerSecond)}.
   */
  String getOverviewTick(int ticksPerSecond);
}