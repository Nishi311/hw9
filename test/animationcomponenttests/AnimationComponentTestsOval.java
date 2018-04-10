package animationcomponenttests;

import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.concreteclasses.animationcomponenttypes.ColorChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.PositionChange;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.ScaleChangeRR;
import cs3500.animator.model.concreteclasses.animationcomponenttypes.VisibilityChange;
import cs3500.animator.model.concreteclasses.shapes.Oval;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;

public abstract class AnimationComponentTestsOval {
  ShapeInterface testShape;
  Duration testDur;
  AnimationComponentInterface testAmCom;

  @Before
  public void setup() {
    testShape = new Oval("test", new ColorClass(), new Position2D(), 0.0f, false, 0.0f, 0.0f);
    testDur = new Duration(10, 10);
  }

  @Test
  public void getTargetName() {
    testAmCom = make(testShape, testDur);
    assertEquals("test", testAmCom.getTargetName());
  }

  @Test
  public void getStartTime() {
    testAmCom = make(testShape, testDur);
    assertEquals(10, testAmCom.getStartTime());
  }

  @Test
  public void getEndTime() {
    testAmCom = make(testShape, testDur);
    assertEquals(10, testAmCom.getEndTime());
  }

  @Test
  public void compareToTestLow() {
    testAmCom = make(testShape, testDur);
    AnimationComponentInterface compareAmCom = make(testShape, new Duration(15, 15));

    assertEquals(-1, testAmCom.compareTo(compareAmCom));

  }

  @Test
  public void compareToTestHigh() {
    testAmCom = make(testShape, testDur);
    AnimationComponentInterface compareAmCom = make(testShape, new Duration(9, 9));

    assertEquals(1, testAmCom.compareTo(compareAmCom));

  }

  @Test
  public void compareToTestEqual() {
    testAmCom = make(testShape, testDur);
    AnimationComponentInterface compareAmCom = make(testShape, new Duration(10, 10));

    assertEquals(0, testAmCom.compareTo(compareAmCom));

  }


  protected abstract AnimationComponentInterface make(ShapeInterface shape, Duration dur);

  public static final class ColorTest extends AnimationComponentTestsOval {
    @Override
    protected AnimationComponentInterface make(ShapeInterface shape, Duration dur) {
      return new ColorChange(shape, dur, new ColorClass(), new ColorClass());
    }
  }

  public static final class PositionTest extends AnimationComponentTestsOval {
    @Override
    protected AnimationComponentInterface make(ShapeInterface shape, Duration dur) {
      return new PositionChange(shape, dur, new Position2D(), new Position2D());
    }

  }

  public static final class ScaleRRTest extends AnimationComponentTestsOval {
    @Override
    protected AnimationComponentInterface make(ShapeInterface shape, Duration dur) {
      return new ScaleChangeRR(shape, dur, 0.0f, 0.0f, 0.0f, 0.0f);
    }
  }

  public static final class VisibilityTest extends AnimationComponentTestsOval {
    @Override
    protected AnimationComponentInterface make(ShapeInterface shape, Duration dur) {
      return new VisibilityChange(shape, dur, true);
    }
  }
}
