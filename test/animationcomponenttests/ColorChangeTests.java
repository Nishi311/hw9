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
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ColorChange;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public abstract class ColorChangeTests {
  ColorClass testColorInitial;
  ColorClass testColor;
  ShapeInterface testShape;
  Duration testDur;

  /**
   * Just set up for color change tests. making a new shape and a test color and duration
   */
  @Before
  public void setup() {
    testColorInitial = new ColorClass(1.0f, 1.0f, 1.0f);
    testColor = new ColorClass(0.4f, 0.2f, 0.0f);
    testShape = make("test", testColorInitial, new Position2D(),
            0.0f, false, 1.0f, 1.0f);
    testDur = new Duration(1, 3);
  }

  @Test
  public void executeIncrementTest() {

    ColorClass incrementColor = new ColorClass(.8f, 1f - ((1.0f - 0.2f) / 3f),
            0.6666666f);
    AnimationComponentAbstract action = new ColorChange(testShape, testDur, testColorInitial,
            testColor);

    action.executeIncrement();

    assertTrue(testShape.getParameter(UniversalShapeParameterTypes.COLOR.name()).equals(
            incrementColor));
  }

  @Test
  public void executeFullTest() {
    AnimationComponentAbstract action = new ColorChange(testShape, testDur, testColorInitial,
            testColor);

    action.executeFull();

    assertTrue(testShape.getParameter(UniversalShapeParameterTypes.COLOR.name()).equals(testColor));
  }

  @Test
  public void getAnimationTypeTest() {

    AnimationComponentAbstract action = new ColorChange(testShape, testDur, testColorInitial,
            testColor);

    assertEquals("Color Change", action.getAnimationType());
  }

  @Test
  public void toStringTest() {
    AnimationComponentAbstract action = new ColorChange(testShape, testDur, testColorInitial,
            testColor);

    String expectedOutput = "Shape test changes color from (1.0,1.0,1.0) to (0.4,0.2,0.0) "
            + "from t=1 to t=3\n";

    assertEquals(expectedOutput, action.toString());
  }

  @Test
  public void equalityTests() {
    AnimationComponentAbstract action = new ColorChange(testShape, testDur, testColorInitial,
            testColor);

    AnimationComponentAbstract action2 = new ColorChange(testShape, testDur, testColorInitial,
            testColor);


    ShapeInterface newShape = make("test2", testColorInitial, new Position2D(),
            0.0f, false, 1.0f, 1.0f);


    AnimationComponentInterface action3 = new ColorChange(newShape, new Duration(),
            new ColorClass(), new ColorClass());

    assertTrue(action.equals(action2));
    assertTrue(!action.equals(action3));
  }

  protected abstract ShapeInterface make(String name, ColorClass color, Position2D pos,
                                         float orient, boolean visibility, float param1,
                                         float param2);

  public static final class RectangleTest extends ColorChangeTests {
    @Override
    protected ShapeInterface make(String name, ColorClass color, Position2D pos, float orient,
                                  boolean visibility, float param1, float param2) {
      return new Rectangle(name, color, pos, orient, visibility, param1, param2);
    }
  }

  public static final class OvalTest extends ColorChangeTests {
    @Override
    protected ShapeInterface make(String name, ColorClass color, Position2D pos, float orient,
                                  boolean visibility, float param1, float param2) {
      return new Oval(name, color, pos, orient, visibility, param1, param2);
    }
  }
}


