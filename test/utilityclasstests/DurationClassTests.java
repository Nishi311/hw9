package utilityclasstests;

import org.junit.Test;

import cs3500.animator.model.concreteclasses.utilityclasses.Duration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class DurationClassTests {

  @Test
  public void invalidCreationTwoParamsTest() {
    try {
      Duration test = new Duration(-1, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot have negative times", e.getMessage());
    }
  }

  @Test
  public void invalidCreationOneParamsTest() {
    try {
      Duration test = new Duration(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot have negative times", e.getMessage());
    }
  }


  @Test
  public void equalityTrueTest() {
    Duration test = new Duration(0, 2);
    Duration test2 = new Duration(0, 2);
    assertTrue(test.equals(test2));
  }

  @Test
  public void equalityFalseTest() {
    Duration test = new Duration(0, 2);
    Duration test2 = new Duration(0, 1);
    assertFalse(test.equals(test2));
  }
}
