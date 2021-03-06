package animationcomponenttests;

import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.abstracts.AnimationComponentAbstract;
import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeWH;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ScaleChangeWHTests {
  float param1Initial;
  float param2Initial;
  float param1;
  float param2;
  ShapeInterface testShape;
  Duration testDur;

  /**
   * Basic setup for testing. creates test parameters, shape and duration.
   */
  @Before
  public void setup() {
    param1Initial = 1f;
    param2Initial = 1f;
    param1 = 5.0f;
    param2 = 10.5f;
    testShape = new Rectangle("test", new ColorClass(), new Position2D(), 0.0f,
            false, param1Initial, param2Initial);
    testDur = new Duration(1, 4);
  }

  @Test
  public void executeIncrementTest() {

    AnimationComponentAbstract action = new ScaleChangeWH(testShape, testDur, param1Initial,
            param2Initial, param1, param2);

    float incrementX = (5.0f - 1.0f) / 4.0f + 1f;
    float incrementY = (10.5f - 1.0f) / 4.0f + 1f;

    action.executeIncrement();

    assertTrue(((float) testShape.getParameter("width") == incrementX) &&
            ((float) testShape.getParameter("height") == incrementY));
  }

  @Test
  public void executeFullTest() {
    AnimationComponentAbstract action = new ScaleChangeWH(testShape, testDur, param1Initial,
            param2Initial, param1, param2);

    action.executeFull();

    assertTrue(((float) testShape.getParameter("width") == 5f) &&
            ((float) testShape.getParameter("height") == 10.5f));
  }

  @Test
  public void getAnimationTypeTest() {

    AnimationComponentAbstract action = new ScaleChangeWH(testShape, testDur, param1Initial,
            param2Initial, param1, param2);

    assertEquals("Scale Change WH", action.getAnimationType());
  }

  @Test
  public void toStringTest() {
    AnimationComponentAbstract action = new ScaleChangeWH(testShape, testDur, param1Initial,
            param2Initial, param1, param2);

    String expectedOutput = "Shape test scales from Width: 1.0, Height: 1.0 to Width: 5.0, "
            + "Height: 10.5 from t=1 to t=4\n";

    assertEquals(expectedOutput, action.toString());
  }

}


