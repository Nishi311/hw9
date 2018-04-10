package utilityclasstests;

import org.junit.Test;

import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ColorClassTests {

  @Test
  public void invalidCreationTest() {
    try {
      ColorClass test = new ColorClass(-1, 0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot have negative color values", e.getMessage());
    }
  }

  @Test
  public void toStringTest() {
    ColorClass test = new ColorClass(0, 0, 0);
    assertEquals("(0.0,0.0,0.0)", test.toString());
  }

  @Test
  public void equalityTrueTest() {
    ColorClass test = new ColorClass(0, 0, 0);
    ColorClass test2 = new ColorClass(0, 0, 0);
    assertTrue(test.equals(test2));
  }

  @Test
  public void equalityFalseTest() {
    ColorClass test = new ColorClass(0, 0, 0);
    ColorClass test2 = new ColorClass(1, 0, 0);
    assertFalse(test.equals(test2));
  }
}
