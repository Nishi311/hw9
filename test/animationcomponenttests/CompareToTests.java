package animationcomponenttests;

import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.concreteclasses.animationcomponenttypes.ColorChange;
import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

import static org.junit.Assert.assertTrue;

public class CompareToTests {
  AnimationComponentInterface testAnimation;
  ShapeInterface shape;
  ColorClass testColor;
  Position2D testPos;

  /**
   * Basic set up for testing. Creates dummy values and a basic AnimationComponent to test against.
   */
  @Before
  public void setUp() {
    testColor = new ColorClass();
    testPos = new Position2D();

    shape = new Rectangle("TestRect", testColor, testPos, 0,
            false, 0f, 0f);

    Duration testDur = new Duration(5, 10);
    testAnimation = new ColorChange(shape, testDur, testColor, testColor);
  }

  @Test
  public void earlyDiffStart() {
    Duration newDur = new Duration(3, 4);
    AnimationComponentInterface earlyAn = new ColorChange(shape, newDur, testColor, testColor);
    int outcome = testAnimation.compareTo(earlyAn);
    assertTrue(outcome == 1);
  }


  @Test
  public void lateDiffStart() {
    Duration newDur = new Duration(6, 10);
    AnimationComponentInterface earlyAn = new ColorChange(shape, newDur, testColor, testColor);
    int outcome = testAnimation.compareTo(earlyAn);
    assertTrue(outcome == -1);
  }

  @Test
  public void earlySameStart() {
    Duration newDur = new Duration(5, 9);
    AnimationComponentInterface earlyAn = new ColorChange(shape, newDur, testColor, testColor);
    int outcome = testAnimation.compareTo(earlyAn);
    assertTrue(outcome == 1);
  }

  @Test
  public void earlyLateStart() {
    Duration newDur = new Duration(5, 15);
    AnimationComponentInterface earlyAn = new ColorChange(shape, newDur, testColor, testColor);
    int outcome = testAnimation.compareTo(earlyAn);
    assertTrue(outcome == -1);
  }

  @Test
  public void sameTime() {
    Duration newDur = new Duration(5, 10);
    AnimationComponentInterface earlyAn = new ColorChange(shape, newDur, testColor, testColor);
    int outcome = testAnimation.compareTo(earlyAn);
    assertTrue(outcome == 0);
  }

}
