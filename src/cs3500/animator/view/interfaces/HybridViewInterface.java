package cs3500.animator.view.interfaces;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

import javax.swing.event.ChangeListener;

/**
 * Extension of the ViewInterface that allows for the control of an animation's playback. Only
 * to be used for Interactive views.
 */
public interface HybridViewInterface extends ViewInterface {

  /**
   * Allows for the setting listeners for use by controller.
   *
   * @param buttons action listener for buttons.
   * @param changes key listener for the speed spinner.
   * @param items   item listener for any checkboxes and such.
   */
  void setListeners(ActionListener buttons, ChangeListener changes, ItemListener items);

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Updates speed spinner.
   *
   * @param newSpeed the new speed.
   */
  void setSpeed(int newSpeed);

  /**
   * Allows the user to determine the looping behavior (animation restarts upon completion).
   *
   * @param loops determines whether the animation loops after it ends.
   */
  void setLooping(boolean loops);

  /**
   * Allows the user to begin playing the animation.
   */
  void resume();

  /**
   * Allows the user to pause the animation.
   */
  void pause();

  /**
   * Allows the user to reset the animation to the beginning.
   */
  void restart();

  /**
   * Allows the user to export the animation to an SVG file.
   */
  void exportSVG();

  /**
   * Allows the user to select and deselect shapes.
   */
  void selectOrUnseletShapes(String shapeName);

  /**
   * Allows the user set current tick.
   */
  void setToTick(int prevTick, int currTick);
}
