package cs3500.animator.controller.controllerimplementations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;

import cs3500.animator.view.interfaces.ViewFactoryInterface;

import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.view.ViewTypes;
import cs3500.animator.view.viewimplementations.TestHybridView;

/**
 * Different Implementation of controllerAbstract that has the same functionality as the standard
 * ControllerWithHybrid but exposes things for testing.
 */
public class TestControllerHybrid extends ControllerAbstract implements ActionListener,
        ItemListener, KeyListener {
  //exposed test version of hybridView allows for the checking of testing booleans
  public TestHybridView hybridView;

  /**
   * Basic controller.
   *
   * @param model Doesn't actually do anything in this class.
   * @param vFac  Doesn't actually do anything in this class.
   */
  public TestControllerHybrid(AnimationModelInterface model, ViewFactoryInterface vFac) {
    super(model, vFac);
  }

  @Override
  public void run() throws IllegalStateException {
    //Not implemented because it is not needed for testing
  }

  @Override
  public String parseInput(String[] args) {
    return "Not implemented because it is not needed for testing";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    checkIfHybridExists();
    switch (e.getActionCommand()) {
      case "Play":
        hybridView.resume();
        hybridView.resetFocus();
        break;
      case "Pause":
        hybridView.pause();
        hybridView.resetFocus();
        break;
      case "Restart":
        hybridView.restart();
        hybridView.resetFocus();
        break;
      default:
        throw new IllegalArgumentException("Button option not valid");
    }
  }

  @Override
  public void itemStateChanged(ItemEvent i) {
    checkIfHybridExists();
    String which = ((JCheckBox) i.getItemSelectable()).getActionCommand();

    if (which.equals("Loop Box")) {
      if (i.getStateChange() == ItemEvent.SELECTED) {
        hybridView.setLooping(true);
      } else {
        hybridView.setLooping(false);
      }
    } else {
      hybridView.selectOrUnseletShapes(which);
    }

  }

  @Override
  public void keyTyped(KeyEvent k) {
    checkIfHybridExists();

  }

  @Override
  public void keyPressed(KeyEvent k) {
    checkIfHybridExists();

  }

  @Override
  public void keyReleased(KeyEvent k) {
    checkIfHybridExists();

  }

  @Override
  protected ViewTypes viewTypeCheck(String temp) {
    ViewTypes type = ViewTypes.SVG;
    return type;
  }

  //simple check to see if the hybrid view has been created.
  private void checkIfHybridExists() {
    if (hybridView == null) {
      throw new IllegalStateException("Hybrid view not present");
    }
  }
}

