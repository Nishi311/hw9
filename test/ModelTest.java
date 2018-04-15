import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.Map;

import cs3500.animator.model.AnimationModelText;
import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.factories.AnimationComponentFactoryBasic;
import cs3500.animator.model.factories.ShapeFactoryBasic;
import cs3500.animator.model.interfaces.AnimationComponentFactoryInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.model.interfaces.ShapeFactoryInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class ModelTest {
  AnimationModelInterface model;
  AnimationComponentFactoryInterface animationFactory;
  ShapeFactoryInterface shapeFactoryInterface;

  ColorClass testColor = new ColorClass(0f, 0f, 0f);
  Position2D testPosition = new Position2D(0f, 0f);

  /**
   * Basic setup for testing. creates model with basic factories and creates two test rectangles
   * and two test ovals.
   */
  @Before
  public void setUp() {
    animationFactory = new AnimationComponentFactoryBasic();
    shapeFactoryInterface = new ShapeFactoryBasic();

    model = new AnimationModelText(shapeFactoryInterface, animationFactory);

    model.addShape("TestRectangle1", "Rectangle", testColor, testPosition,
            0.0f, 0.0f, 0.0f);

    model.addShape("TestRectangle2", "Rectangle", testColor, testPosition,
            0.0f, 0.0f, 0.0f);

    model.addShape("TestOval1", "Oval", testColor, testPosition,
            0.0f, 0.0f, 0.0f);

    model.addShape("TestOval2", "Oval", testColor, testPosition,
            0.0f, 0.0f, 0.0f);


  }

  @Test
  public void addAnimationValid() {
    Duration testDuration = new Duration(0, 0);
    Duration testDuration2 = new Duration(3, 3);

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);

    List<AnimationComponentInterface> animationList = model.getAnimationList();

    assertTrue(animationList.get(0).getTargetName().equals("TestRectangle1")
            && animationList.get(0).getAnimationType().equals("Visibility Change"));
  }

  @Test
  public void addAnimationInvalidOverlapping() {
    Duration testDuration = new Duration(0, 0);
    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);
    String expectedOutput = "At least two animation components attempt to "
            + "perform the same act on the same object during the same period";
    try {
      model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
              false);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }


  @Test
  public void addAnimationInvalidUnknownType() {
    Duration testDuration = new Duration(0, 0);

    String expectedOutput = "Not a recognized animation type";
    try {
      model.addAnimation("TestRectangle1", "Hello World", testDuration,
              true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidNoShape() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "There is no shape with name: \"Hello World\"";
    try {
      model.addAnimation("Hello World", "Visibility Change", testDuration,
              true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationWrongScaleRect() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "This scale change is for shapes with two radii";
    try {
      model.addAnimation("TestRectangle1", "Scale Change RR", testDuration,
              0.0f, 0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationWrongScaleOval() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "This scale change is for width/height based shapes";
    try {
      model.addAnimation("TestOval1", "Scale Change WH", testDuration,
              0.0f, 0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidColor() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Must pass two ColorClass parameters";
    try {
      model.addAnimation("TestRectangle1", "Color Change", testDuration,
              new ColorClass(), 0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidPos() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Must pass two Position2D parameters";
    try {
      model.addAnimation("TestRectangle1", "Position Change", testDuration,
              0.0f, 0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidVisibility() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Must pass a Boolean parameter";
    try {
      model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
              0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidTooFewScaleParamRect() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Wrong amount of parameters for a Scale Change";
    try {
      model.addAnimation("TestRectangle1", "Scale Change WH", testDuration,
              0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidBadScaleParamsRect() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Must pass two float parameters";
    try {
      model.addAnimation("TestRectangle1", "Scale Change WH", testDuration,
              0.0f, 0.0f, 0.0f, true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidTooFewScaleParamOval() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Wrong amount of parameters for a Scale Change";
    try {
      model.addAnimation("TestOval1", "Scale Change RR", testDuration,
              0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addAnimationInvalidBadScaleParamsOval() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Must pass four float parameters";
    try {
      model.addAnimation("TestOval1", "Scale Change RR", testDuration,
              0.0f, 0.0f, 0.0f, true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }


  @Test
  public void editAnimationValid() {
    Duration testDuration = new Duration(0, 0);

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);


    model.editAnimation(1, "TestRectangle1", "Color Change",
            testDuration, testColor, new ColorClass());

    List<AnimationComponentInterface> animationList = model.getAnimationList();

    assertTrue(animationList.get(0).getTargetName().equals("TestRectangle1")
            && animationList.get(0).getAnimationType().equals("Color Change"));
  }

  @Test
  public void editAnimationInvalidIndexNeg() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Cannot have a negative index";

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);

    try {
      model.editAnimation(-1, "TestRectangle1", "Color Change",
              testDuration, new ColorClass());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void editAnimationInvalidIndexTooLarge() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Cannot have an index that exceeds number of animations";

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);

    try {
      model.editAnimation(2, "TestRectangle1", "Color Change",
              testDuration, new ColorClass(), new ColorClass());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void editAnimationInvalidNoShape() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "There is no shape with name: \"Hello World\"";

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);

    try {
      model.editAnimation(1, "Hello World", "Color Change",
              testDuration, new ColorClass());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void editAnimationInvalidOverlap() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "This edit would cause conflict with other animations";

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);
    model.addAnimation("TestRectangle1", "Visibility Change",
            new Duration(1, 1), true);
    try {
      model.editAnimation(2, "TestRectangle1", "Visibility Change",
              testDuration, false);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void addShapeValidTest() {
    String shapeName = "Test";
    String shapeType = "Rectangle";
    ColorClass color = new ColorClass();
    Position2D pos = new Position2D();

    ShapeInterface newShape = new Rectangle(shapeName, color, pos, 0.0f, false,
            0.0f, 0.0f);
    model.addShape(shapeName, shapeType, color, pos, 0.0f, 0.0f, 0.0f);

    List<ShapeInterface> shapeList = model.getShapeList();
    assertTrue(shapeList.contains(newShape));
  }


  @Test
  public void addShapeAlreadyExistingName() {
    String shapeName = "TestRectangle1";
    String shapeType = "Rectangle";
    ColorClass color = new ColorClass();
    Position2D pos = new Position2D();

    try {
      model.addShape(shapeName, shapeType, color, pos, 0.0f, 0.0f, 0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There is already a shape with this name", e.getMessage());
    }
  }

  @Test
  public void addShapeInValidShapeType() {
    String shapeName = "Test";
    String shapeType = "Hello World";
    ColorClass color = new ColorClass();
    Position2D pos = new Position2D();

    try {
      model.addShape(shapeName, shapeType, color, pos, 0.0f, 0.0f, 0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not a recognized shape type", e.getMessage());
    }
  }


  @Test
  public void addShapeInvalidNumParamsRectangle() {
    String shapeName = "test";
    String shapeType = "Rectangle";
    ColorClass color = new ColorClass();
    Position2D pos = new Position2D();

    try {
      model.addShape(shapeName, shapeType, color, pos, 0.0f, 0.0f, 0.0f, 0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Wrong amount of parameters for a rectangle", e.getMessage());
    }
  }

  @Test
  public void addShapeInvalidNumParamsOval() {
    String shapeName = "test";
    String shapeType = "Oval";
    ColorClass color = new ColorClass();
    Position2D pos = new Position2D();

    try {
      model.addShape(shapeName, shapeType, color, pos, 0.0f, 0.0f, 0.0f, 0.0f);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Wrong amount of parameters for an Oval", e.getMessage());
    }
  }


  @Test
  public void addShapeValidNumParamsInvalidParamTypesRectangle() {
    String shapeName = "test";
    String shapeType = "Rectangle";
    ColorClass color = new ColorClass();
    Position2D pos = new Position2D();

    try {
      model.addShape(shapeName, shapeType, color, pos, 0.0f, color, color);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Wrong parameters for a rectangle", e.getMessage());
    }
  }

  @Test
  public void addShapeValidNumParamsInvalidParamTypesOval() {
    String shapeName = "test";
    String shapeType = "Oval";
    ColorClass color = new ColorClass();
    Position2D pos = new Position2D();

    try {
      model.addShape(shapeName, shapeType, color, pos, 0.0f, color, color);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Wrong parameters for an Oval", e.getMessage());
    }
  }

  @Test
  public void removeValidShapeTest() {
    Duration testDuration = new Duration(0, 0);

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);

    model.removeShape("TestRectangle1");

    assertTrue(model.getAnimationList().isEmpty() && model.getShapeList().size() == 3);

  }

  @Test
  public void removeInvalidShapeTest() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "There is no shape with name: \"Hello World\"";

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);

    try {
      model.removeShape("Hello World");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }


  @Test
  public void removeAnimationValidTest() {
    Duration testDuration = new Duration(0, 0);


    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);
    model.removeAnimation(1);
    assertTrue(model.getAnimationList().isEmpty());
  }

  @Test
  public void removeAnimationInvalidTooLargeIndexTest() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Cannot have an index that exceeds number of animations";

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);
    try {
      model.removeAnimation(2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void removeAnimationInvalidNegIndexTest() {
    Duration testDuration = new Duration(0, 0);
    String expectedOutput = "Cannot have a negative index";

    model.addAnimation("TestRectangle1", "Visibility Change", testDuration,
            true);
    try {
      model.removeAnimation(-1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedOutput, e.getMessage());
    }
  }

  @Test
  public void getOverViewTest() {
    model.clearShapeList();

    String expectedOutput = "shapes:\n"
            + "Name: R\n"
            + "Type: rectangle\n"
            + "Lower-left corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
            + "Appears at t=1\n"
            + "Disappears at t=100\n\n"

            + "Name: C\n"
            + "Type: oval\n"
            + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=6\n"
            + "Disappears at t=100\n\n"

            + "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50\n"
            + "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=20 to t=70\n"
            + "Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) from t=50 to t=80\n"
            + "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from "
            + "t=51 to t=70\n"
            + "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70 to t=100\n";

    Position2D rectPos = new Position2D(200.0f, 200.0f);
    Position2D ovalPos = new Position2D(500.0f, 100.0f);

    ColorClass rectColor = new ColorClass(1f, 0f, 0f);
    ColorClass ovalColor = new ColorClass(0f, 0f, 1f);

    model.addShape("R", "Rectangle", rectColor, rectPos, 0.0f,
            50.0f, 100.0f);
    model.addShape("C", "Oval", ovalColor, ovalPos, 0.0f,
            60.0f, 30.0f);

    Duration rectVis1 = new Duration(1);
    Duration rectVis2 = new Duration(100);
    Duration rectMove1Dur = new Duration(10, 50);
    Duration rectMove2Dur = new Duration(70, 100);
    Duration rectScaleDur = new Duration(51, 70);

    Duration ovalVis1 = new Duration(6);
    Duration ovalVis2 = new Duration(100);
    Duration ovalMoveDur = new Duration(20, 70);
    Duration ovalColorDur = new Duration(50, 80);

    Position2D rectMove1Pos = new Position2D(300f, 300f);
    Position2D rectMove2Pos = new Position2D(200f, 200f);
    Position2D ovalMovePos = new Position2D(500f, 400f);

    ColorClass ovalColorValue = new ColorClass(0f, 1f, 0f);


    model.addAnimation("R", "Visibility Change", rectVis1, true);
    model.addAnimation("R", "Visibility Change", rectVis2, false);

    model.addAnimation("R", "Scale Change WH", rectScaleDur,
            50f, 100f, 25.0f, 100.0f);

    model.addAnimation("C", "Visibility Change", ovalVis1, true);
    model.addAnimation("C", "Visibility Change", ovalVis2, false);

    model.addAnimation("R", "Position Change", rectMove1Dur, rectPos,
            rectMove1Pos);
    model.addAnimation("R", "Position Change", rectMove2Dur, rectMove1Pos,
            rectMove2Pos);

    model.addAnimation("C", "Color Change", ovalColorDur, ovalColor,
            ovalColorValue);
    model.addAnimation("C", "Position Change", ovalMoveDur, ovalPos,
            ovalMovePos);

    assertEquals(expectedOutput, model.getOverview());
  }

  @Test
  public void sortCheck() {
    Duration dur1 = new Duration(1, 1);
    Duration dur2 = new Duration(1, 2);
    Duration dur3 = new Duration(1, 3);
    Duration dur4 = new Duration(1, 4);
    Duration dur5 = new Duration(1, 5);

    Position2D rectPos = new Position2D(200.0f, 200.0f);
    ColorClass rectColor = new ColorClass(1f, 0f, 0f);

    String expectedOutput = "TestRectangle1\n"
            + "R\n"
            + "TestOval1\n"
            + "TestRectangle2\n"
            + "TestOval2\n";
    StringBuilder actualOutput = new StringBuilder();

    model.addShape("R", "Rectangle", rectColor, rectPos, 0.0f,
            50.0f, 100.0f);

    model.addAnimation("TestRectangle1", "Scale Change WH", dur1,
            0f, 0f, 25.0f, 100.0f);

    model.addAnimation("TestRectangle2", "Scale Change WH", dur4,
            0f, 0f, 25.0f, 100.0f);

    model.addAnimation("TestOval1", "Scale Change RR", dur3,
            0.f, 0f, 25.0f, 100.0f);

    model.addAnimation("TestOval2", "Scale Change RR", dur5,
            0.f, 0f, 25.0f, 100.0f);

    model.addAnimation("R", "Scale Change WH", dur2,
            0.f, 0f, 25.0f, 100.0f);
    List<AnimationComponentInterface> anList = model.getAnimationList();

    for (AnimationComponentInterface a : anList) {
      actualOutput.append(a.getTargetName() + "\n");
    }

    assertEquals(expectedOutput, actualOutput.toString());
  }

  @Test
  public void mapCopyCheck() {
    Duration dur1 = new Duration(1, 1);
    model.addAnimation("TestRectangle1", "Scale Change WH", dur1,
            0f, 0f, 25.0f, 100.0f);
    Map<Integer, List<AnimationComponentInterface>> oldMap = model.getStartToAnimationMap();
    Map<Integer, List<AnimationComponentInterface>> newMap = model.getStartToAnimationMap();

    ShapeInterface test = new Rectangle("Hello World", new ColorClass(), new Position2D(),
            0f, false, 0f, 0f);

    newMap.get(1).get(0).setShape(test);

    assertTrue(!oldMap.equals(newMap));
  }

}
