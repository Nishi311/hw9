package animationcomponenttests;

import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.abstracts.AnimationComponentAbstract;
import cs3500.animator.model.concreteclasses.shapes.Oval;
import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.VisibilityChange;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public abstract class VisibilityChangeTests {
  Position2D testPos;
  ShapeInterface testShape;
  Duration testDur;

  /**
   * Basic setup for testing. creates test position, shape and duration.
   */
  @Before
  public void setup() {
    testPos = new Position2D(10.5f, 20f);
    testShape = make("test", new ColorClass(), new Position2D(1f, 1f), 0.0f,
            false, 1.0f, 1.0f);
    testDur = new Duration(1);
  }

  @Test
  public void executeIncrementTest() {

    AnimationComponentAbstract action = new VisibilityChange(testShape, testDur, true);

    action.executeIncrement();

    assertTrue((boolean) testShape.getParameter(UniversalShapeParameterTypes.VISIBILITY.name()));
  }

  @Test
  public void executeFullTest() {
    AnimationComponentAbstract action = new VisibilityChange(testShape, testDur, true);

    action.executeFull();

    assertTrue((boolean) testShape.getParameter(UniversalShapeParameterTypes.VISIBILITY.name()));
  }

  @Test
  public void getAnimationTypeTest() {

    AnimationComponentAbstract action = new VisibilityChange(testShape, testDur, true);

    assertEquals("Visibility Change", action.getAnimationType());
  }

  @Test
  public void toStringTestTrue() {
    AnimationComponentAbstract action = new VisibilityChange(testShape, testDur, true);

    String expectedOutput = "Appears at t=1\n";

    assertEquals(expectedOutput, action.toString());

  }

  @Test
  public void toStringTestFalse() {
    AnimationComponentAbstract action = new VisibilityChange(testShape, testDur, false);

    String expectedOutput = "Disappears at t=1\n";

    assertEquals(expectedOutput, action.toString());
  }

  @Test
  public void longDurationTest() {
    try {
      AnimationComponentAbstract action = new VisibilityChange(testShape, new Duration(1, 2),
              false);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("A change in visibility must be done at one time unit", e.getMessage());
    }

  }

  protected abstract ShapeInterface make(String name, ColorClass color, Position2D pos,
                                         float orient, boolean visibility, float param1,
                                         float param2);

  public static final class RectangleTest extends VisibilityChangeTests {
    @Override
    protected ShapeInterface make(String name, ColorClass color, Position2D pos, float orient,
                                  boolean visibility, float param1, float param2) {
      return new Rectangle(name, color, pos, 0.0f, visibility, param1, param2);
    }
  }

  public static final class OvalTest extends VisibilityChangeTests {
    @Override
    protected ShapeInterface make(String name, ColorClass color, Position2D pos, float orient,
                                  boolean visibility, float param1, float param2) {
      return new Oval(name, color, pos, 0.0f, visibility, param1, param2);
    }
  }
}


