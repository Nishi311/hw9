package utilityclasstests;

import org.junit.Test;

import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Position2DTests {


  @Test
  public void equalityTrueTest() {
    Position2D test = new Position2D(0, 2);
    Position2D test2 = new Position2D(0, 2);
    assertTrue(test.equals(test2));
  }

  @Test
  public void equalityFalseTest() {
    Position2D test = new Position2D(0, 2);
    Position2D test2 = new Position2D(0, 3);
    assertFalse(test.equals(test2));
  }

  @Test
  public void toStringTest() {
    Position2D test = new Position2D(0, 2);
    assertEquals("(0.0,2.0)", test.toString());
  }
}
