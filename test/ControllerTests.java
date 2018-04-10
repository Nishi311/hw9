import org.junit.Before;
import org.junit.Test;

import cs3500.animator.controller.controllerimplementations.ControllerBasic;
import cs3500.animator.view.ViewFactoryWithHybrid;
import cs3500.animator.controller.interfaces.ControllerInterface;
import cs3500.animator.view.interfaces.ViewFactoryInterface;
import cs3500.animator.model.AnimationModelText;
import cs3500.animator.model.factories.AnimationComponentFactoryBasic;
import cs3500.animator.model.factories.ShapeFactoryBasic;
import cs3500.animator.model.interfaces.AnimationModelInterface;

import static junit.framework.TestCase.assertEquals;

public class ControllerTests {

  private ControllerInterface controller;

  /**
   * Basic set up. creates new controller for the tests to use.
   */
  @Before
  public void setUp() {
    AnimationModelInterface model = new AnimationModelText(new ShapeFactoryBasic(),
            new AnimationComponentFactoryBasic());

    ViewFactoryInterface vFac = new ViewFactoryWithHybrid();
    controller = new ControllerBasic(model, vFac);
  }


  @Test
  public void goBeforeParse() {

    String expectedOutput = "View has not been created. MUST run parseInput() method first.";
    try {
      controller.run();
    } catch (Exception e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }


  @Test
  public void missingInFile() {
    String[] input = {"-iv", "visual"};

    String expectedOutput = "Must have an in-file";

    assertEquals(expectedOutput, controller.parseInput(input));
  }

  @Test
  public void missingViewType() {
    String[] input = {"-if", "helloWorld.txt"};

    String expectedOutput = "Must have a view type";

    assertEquals(expectedOutput, controller.parseInput(input));
  }


  @Test
  public void badArgument() {
    String[] input = {"-if", "helloWorld.txt", "-iv", "visual", "-bad", "50"};

    String expectedOutput = "Unexpected input, please ensure that all arguments are preceded with "
            + "\"-if\", \"-iv\", \"-o\", or \"-speed\"";

    assertEquals(expectedOutput, controller.parseInput(input));
  }


  @Test
  public void badViewType() {
    String[] input = {"-if", "helloWorld.txt", "-iv", "blarg", "-bad", "50"};

    String expectedOutput = "The view type MUST be either \"text\", \"visual\", or "
            + "\"svg\". These are case sensitive.";

    assertEquals(expectedOutput, controller.parseInput(input));
  }

  @Test
  public void badSpeedNonInt() {
    String[] input = {"-if", "helloWorld.txt", "-iv", "visual", "-speed", "honk"};

    String expectedOutput = "Speed MUST be an integer";

    assertEquals(expectedOutput, controller.parseInput(input));
  }

  @Test
  public void badSpeedLessThanZero() {
    String[] input = {"-if", "helloWorld.txt", "-iv", "visual", "-speed", "-10"};

    String expectedOutput = "Speed MUST be GREATER than 0";

    assertEquals(expectedOutput, controller.parseInput(input));
  }

  @Test
  public void badFileName() {
    String[] input = {"-if", "helloWorld.txt", "-iv", "visual", "-speed", "10"};

    String expectedOutput = "helloWorld.txt (The system cannot find the file specified)";

    assertEquals(expectedOutput, controller.parseInput(input));
  }

  @Test
  public void goodCommandVisual() {
    String[] input = {"-if", "smalldemo.txt", "-o", "blargHonk.txt", "-iv", "visual",
                      "-speed", "10"};

    String expectedOutput = "No Error";

    assertEquals(expectedOutput, controller.parseInput(input));
  }


  @Test
  public void goodCommandText() {
    String[] input = {"-if", "smalldemo.txt", "-o", "blargHonk.txt", "-iv", "text",
                      "-speed", "10"};

    String expectedOutput = "No Error";

    assertEquals(expectedOutput, controller.parseInput(input));
  }


  @Test
  public void goodCommandSVG() {
    String[] input = {"-if", "smalldemo.txt", "-o", "blargHonk.txt", "-iv", "svg",
                      "-speed", "10"};

    String expectedOutput = "No Error";

    assertEquals(expectedOutput, controller.parseInput(input));
  }


  @Test
  public void defaultOutput() {
    String[] input = {"-if", "smalldemo.txt", "-iv", "svg"};

    String expectedOutput = "out";

    controller.parseInput(input);
    assertEquals(expectedOutput, controller.getView().getDestination());
  }

  @Test
  public void defaultSpeed() {
    String[] input = {"-if", "smalldemo.txt", "-iv", "svg"};

    int expectedOutput = 1;

    controller.parseInput(input);
    assertEquals(expectedOutput, controller.getView().getSpeed());
  }

}
