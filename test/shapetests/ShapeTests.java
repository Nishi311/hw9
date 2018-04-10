package shapetests;

import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.concreteclasses.shapes.Oval;
import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.concreteclasses.utilityclasses.UniversalShapeParameterTypes;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;

public abstract class ShapeTests {
  String name;
  ColorClass color;
  Position2D pos;
  boolean visible;

  /**
   * Basic set up. Creates new parameters to use.
   */
  @Before
  public void setUp() {
    name = "test";
    color = new ColorClass();
    pos = new Position2D();
    visible = false;

  }

  @Test
  public void invalidCreationTest() {
    try {
      ShapeInterface test = make(name, color, pos, 0.0f, false, 0.0f, -1f);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Cannot have negative"));
    }
  }

  @Test
  public void editParamNameTest() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    test.editParameter(UniversalShapeParameterTypes.NAME.name(), "Hello World");

    assertTrue("Hello World".equals(test.getName()));
  }

  @Test
  public void editParamColorTest() {
    ColorClass newColor = new ColorClass(10f, 40f, 30.5f);

    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    test.editParameter(UniversalShapeParameterTypes.COLOR.name(), newColor);

    assertTrue(newColor.equals(test.getColor()));
  }

  @Test
  public void editParamPositionTest() {
    Position2D newPos = new Position2D(13.54f, 30.1f);

    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    test.editParameter(UniversalShapeParameterTypes.POSITION.name(), newPos);

    assertTrue(newPos.equals(test.getPosition()));
  }

  @Test
  public void editParamVisibleTest() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    test.editParameter(UniversalShapeParameterTypes.VISIBILITY.name(), false);

    assertFalse(test.getVisibility());
  }

  @Test
  public void editParamFailureTest() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);

    String expectedOutput = ("A(n) " + test.getShapeType() + " does not have the parameter \"Hello"
            + " World\"");
    try {
      test.editParameter("Hello World", false);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }


  }

  @Test
  public void resetShapeTest() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    ShapeInterface testCopy = test.copy();

    ColorClass newColor = new ColorClass(10f, 40f, 30.5f);
    Position2D newPos = new Position2D(13.54f, 30.1f);

    test.editParameter(UniversalShapeParameterTypes.NAME.name(), "Hello World");
    test.editParameter(UniversalShapeParameterTypes.COLOR.name(), newColor);
    test.editParameter(UniversalShapeParameterTypes.POSITION.name(), newPos);
    test.editParameter(UniversalShapeParameterTypes.VISIBILITY.name(), false);

    test.resetShape();

    assertTrue(testCopy.equals(test));
  }

  @Test
  public void paramCheckName() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    try {
      test.editParameter(UniversalShapeParameterTypes.NAME.name(), 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().equals("Name must be a String"));
    }
  }

  @Test
  public void paramCheckColor() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    try {
      test.editParameter(UniversalShapeParameterTypes.COLOR.name(), 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().equals("Color must be a ColorClass"));
    }
  }


  @Test
  public void paramCheckPos() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    try {
      test.editParameter(UniversalShapeParameterTypes.POSITION.name(), 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().equals("Position must be a Position2D"));
    }
  }

  @Test
  public void paramCheckOrient() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    try {
      test.editParameter(UniversalShapeParameterTypes.ORIENTATION.name(), "Hello");
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().equals("Orientation must be a float"));
    }
  }

  @Test
  public void paramCheckVisible() {
    ShapeInterface test = make(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    try {
      test.editParameter(UniversalShapeParameterTypes.VISIBILITY.name(), 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().equals("Visibility must be a Boolean"));
    }
  }


  protected abstract ShapeInterface make(String name, ColorClass color, Position2D pos,
                                         float orient, boolean visibility, float param1,
                                         float param2);

  public static final class RectangleTest extends ShapeTests {
    @Override
    protected ShapeInterface make(String name, ColorClass color, Position2D pos, float orient,
                                  boolean visibility, float param1, float param2) {
      return new Rectangle(name, color, pos, orient, visibility, param1, param2);
    }
  }

  public static final class OvalTest extends ShapeTests {
    @Override
    protected ShapeInterface make(String name, ColorClass color, Position2D pos, float orient,
                                  boolean visibility, float param1, float param2) {
      return new Oval(name, color, pos, orient, visibility, param1, param2);
    }
  }


}


