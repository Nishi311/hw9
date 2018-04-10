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
import cs3500.animator.model.concreteclasses.animationcomponenttypes.PositionChange;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public abstract class PositionChangeTests {
  Position2D testPosInitial;
  Position2D testPos;
  ShapeInterface testShape;
  Duration testDur;

  /**
   * Basic setup for testing. Makes new test pos, shape and duration.
   */
  @Before
  public void setup() {
    testPosInitial = new Position2D(1f, 1f);
    testPos = new Position2D(10.5f, 20f);
    testShape = make("test", new ColorClass(), testPosInitial, 0.0f,
            false, 1.0f, 1.0f);
    testDur = new Duration(1, 4);
  }

  @Test
  public void executeIncrementTest() {

    Position2D incrementPos = new Position2D(1.0f + ((10.5f - 1.0f) / 4f), 1f +
            ((20.0f - 1.0f) / 4f));
    AnimationComponentAbstract action = new PositionChange(testShape, testDur, testPosInitial,
            testPos);

    action.executeIncrement();

    assertTrue(testShape.getParameter(UniversalShapeParameterTypes.POSITION.name()).equals(
            incrementPos));
  }

  @Test
  public void executeFullTest() {
    AnimationComponentAbstract action = new PositionChange(testShape, testDur, testPosInitial,
            testPos);

    action.executeFull();

    assertTrue(testShape.getParameter(UniversalShapeParameterTypes.POSITION.name()).equals(
            testPos));
  }

  @Test
  public void getAnimationTypeTest() {

    AnimationComponentAbstract action = new PositionChange(testShape, testDur, testPosInitial,
            testPos);

    assertEquals("Position Change", action.getAnimationType());
  }

  @Test
  public void toStringTest() {
    AnimationComponentAbstract action = new PositionChange(testShape, testDur, testPosInitial,
            testPos);

    String expectedOutput = "Shape test moves from (1.0,1.0) to (10.5,20.0) "
            + "from t=1 to t=4\n";

    assertEquals(expectedOutput, action.toString());
  }

  protected abstract ShapeInterface make(String name, ColorClass color, Position2D pos,
                                         float orient, boolean visibility, float param1,
                                         float param2);

  public static final class RectangleTest extends PositionChangeTests {
    @Override
    protected ShapeInterface make(String name, ColorClass color, Position2D pos, float orient,
                                  boolean visibility, float param1, float param2) {
      return new Rectangle(name, color, pos, orient, visibility, param1, param2);
    }
  }

  public static final class OvalTest extends PositionChangeTests {
    @Override
    protected ShapeInterface make(String name, ColorClass color, Position2D pos, float orient,
                                  boolean visibility, float param1, float param2) {
      return new Oval(name, color, pos, orient, visibility, param1, param2);
    }
  }
}


