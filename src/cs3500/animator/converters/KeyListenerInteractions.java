package cs3500.animator.converters;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cs3500.animator.provider.IController;

/**
 * Empty version of the KeyListenerInteractions class that is required for the compilation of the
 * provider's Interactive view. Empty because key bindings aren't strictly required by the
 * original HW 7 prompt so we spared no effort to get them working.
 */
public class KeyListenerInteractions implements KeyListener {
  /**
   * Empty constructor used only as a placeholder.
   *
   * @param controller The controller theoretically used to listen for stuff.
   */
  public KeyListenerInteractions(IController controller) {
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

}
