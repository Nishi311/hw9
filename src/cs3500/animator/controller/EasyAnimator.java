package cs3500.animator.controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.animator.controller.interfaces.ControllerInterface;

import cs3500.animator.converters.ProviderController;
import cs3500.animator.converters.ViewFactoryProvider;
import cs3500.animator.converters.ViewFactoryProviderInterface;
import cs3500.animator.model.AnimationModelText;

import cs3500.animator.model.factories.AnimationComponentFactoryBasic;
import cs3500.animator.model.factories.ShapeFactoryBasic;
import cs3500.animator.model.interfaces.AnimationModelInterface;

/**
 * Contains the main method and helper functions that allow the user to input files to the
 * controller and throw error messages as needed.
 */
public final class EasyAnimator {
  /**
   * The main method. Receives input from the args, parses it, creates the model, creates the view
   * and then enacts the view. REQUIRES: Input file and view type. OPTIONAL: Output file designation
   * (defaults to System.out) and speed (defaults to 1 tick / second).
   *
   * @param args The console given arguments for the program. MUST contain an input file and a view
   *             type. May also contain an output file designation and speed.
   */
  public static void main(String[] args) {

    AnimationModelInterface model = new AnimationModelText(new ShapeFactoryBasic(),
            new AnimationComponentFactoryBasic());

    ViewFactoryProviderInterface vFac = new ViewFactoryProvider() {
    };
    ControllerInterface controller = new ProviderController(model, vFac);

    String errorMessage = controller.parseInput(args);

    if (!errorMessage.equals("No Error")) {
      warningMessageAndQuit(errorMessage);
    } else {
      try {
        controller.run();
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }

  }

  //helper function that simplifies creating the JOptionPanes and then exits the program.
  private static void warningMessageAndQuit(String message) {
    JFrame frame = new JFrame();
    JOptionPane.showMessageDialog(frame,
            message,
            "Command-line Argument Warning",
            JOptionPane.WARNING_MESSAGE);
    System.exit(0);
  }

}
