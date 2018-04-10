package viewtests;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.view.ViewFactory;
import cs3500.animator.view.ViewTypes;
import cs3500.animator.model.AnimationModelText;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.factories.AnimationComponentFactoryBasic;
import cs3500.animator.model.factories.ShapeFactoryBasic;
import cs3500.animator.model.interfaces.AnimationComponentFactoryInterface;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.model.interfaces.ShapeFactoryInterface;
import cs3500.animator.view.interfaces.ViewInterface;

import static junit.framework.TestCase.assertEquals;

/**
 * Basic tests for SVG view.
 */
public class SVGTest {
  AnimationModelInterface model;
  AnimationComponentFactoryInterface animationFactory;
  ShapeFactoryInterface shapeFactoryInterface;


  String inFile = null;
  ViewTypes type = ViewTypes.SVG;
  String outFile = "out";
  int ticksPerSecond = 1;

  ViewFactory vFac = new ViewFactory();
  ViewInterface view;

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
  }

  @Test
  public void testSvgViewShapes() throws IOException {
    model.clearShapeList();

    String expectedOutput = "<svg width=\"1000\" height=\"1000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\"" +
            " fill=\"rgb(255,0,0)\" visibility=\"visible\">\n" +
            "</rect>\n" +
            "<ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\"" +
            " fill=\"rgb(0,0,255)\" visibility=\"visible\">\n" +
            "</ellipse>\n" +
            "</svg>";

    Position2D rectPos = new Position2D(200.0f, 200.0f);
    Position2D ovalPos = new Position2D(500.0f, 100.0f);

    ColorClass rectColor = new ColorClass(1f, 0f, 0f);
    ColorClass ovalColor = new ColorClass(0f, 0f, 1f);

    model.addShape("R", "Rectangle", rectColor, rectPos, 0.0f,
            50.0f, 100.0f);
    model.addShape("C", "Oval", ovalColor, ovalPos, 0.0f,
            60.0f, 30.0f);

    //Create the view based on  the given parameters.
    view = vFac.create(type, new ModelInsulator(model), outFile, ticksPerSecond);

    assertEquals(expectedOutput, view.viewText());
  }

  @Test
  public void testSvgViewAnimation() throws IOException {
    model.clearShapeList();

    String expectedOutput = "<svg width=\"1000\" height=\"1000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"90.0\" " +
            "fill=\"rgb(255,0,0)\" visibility=\"visible\">\n" +
            "<animate attributeType= \"xml\" begin=\"10000.0ms\" dur=\"40000.0ms\" " +
            "attributeName=\"x\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "<animate attributeType= \"xml\" begin=\"10000.0ms\" dur=\"40000.0ms\" " +
            "attributeName=\"y\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "\n" +
            "<animate attributeType= \"xml\" begin=\"51000.0ms\" dur=\"19000.0ms\" " +
            "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "<animate attributeType= \"xml\" begin=\"51000.0ms\" dur=\"19000.0ms\" " +
            "attributeName=\"height\" from=\"90.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "\n" +
            "<animate attributeType= \"xml\" begin=\"70000.0ms\" dur=\"30000.0ms\" " +
            "attributeName=\"x\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n" +
            "<animate attributeType= \"xml\" begin=\"70000.0ms\" dur=\"30000.0ms\" " +
            "attributeName=\"y\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n" +
            "\n" +
            "</rect>\n" +
            "<ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" " +
            "fill=\"rgb(0,0,255)\" visibility=\"visible\">\n" +
            "<animate attributeType= \"xml\" begin=\"20000.0ms\" dur=\"50000.0ms\" " +
            "attributeName=\"cx\" from=\"500.0\" to=\"500.0\" fill=\"freeze\" />\n" +
            "<animate attributeType= \"xml\" begin=\"20000.0ms\" dur=\"50000.0ms\" " +
            "attributeName=\"cy\" from=\"100.0\" to=\"400.0\" fill=\"freeze\" />\n" +
            "\n" +
            "<animate attributeType= \"xml\" begin=\"50000.0ms\" dur=\"30000.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n"
            + "\n" +
            "</ellipse>\n" +
            "</svg>";

    Position2D rectPos = new Position2D(200.0f, 200.0f);
    Position2D ovalPos = new Position2D(500.0f, 100.0f);

    ColorClass rectColor = new ColorClass(1f, 0f, 0f);
    ColorClass ovalColor = new ColorClass(0f, 0f, 1f);

    model.addShape("R", "Rectangle", rectColor, rectPos, 0.0f,
            50.0f, 90.0f);
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
            50f, 90f, 25.0f, 100.0f);

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

    //Create the view based on  the given parameters.
    view = vFac.create(type, new ModelInsulator(model), outFile, ticksPerSecond);

    assertEquals(expectedOutput, view.viewText());
  }
}
