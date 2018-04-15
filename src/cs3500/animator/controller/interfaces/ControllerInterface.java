package cs3500.animator.controller.interfaces;

import cs3500.animator.view.interfaces.ViewInterface;

/**
 * Contains the basic methods necessary for a controller to function.
 */
public interface ControllerInterface {

  /**
   * Takes the list of arguments and builds up the model and creates an interval view for the
   * controller to use in the run() function. Will output any error it comes across as a string.
   *
   * @param args String of arguments for running the program. REQUIRES: "-if" and a filename
   *             for an input file; "-iv" and a view type. OPTIONAL: "-o" and a filename for an
   *             output file (defaults to system.out). "-speed" and an int to designate ticks per
   *             second.
   * @return String version of any error messages produced during the parsing. This can be used for
   * both testing and pop-up creation.
   */
  String parseInput(String[] args);

  /**
   * Runs the view created during the parseInput method. REQUIRES THAT THE PARSEINPUT() METHOD
   * HAS BEEN RUN.
   *
   * @throws IllegalStateException if parseInput() has not been run.
   */
  void run() throws IllegalStateException;

  /**
   * Allows the user to retrieve the view once it has been created by running the parseInput method.
   * Note that this is a DIRECT REFERENCE and not a copy.  REQUIRES THAT THE PARSEINPUT() METHOD
   * HAS BEEN RUN.
   *
   * @return A reference to the controller's contained view.
   * @throws IllegalStateException if parseInput() has not been run.
   */
  ViewInterface getView() throws IllegalStateException;
}
