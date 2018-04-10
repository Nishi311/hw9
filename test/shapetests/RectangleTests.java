package shapetests;

import org.junit.Test;

import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class RectangleTests {
  @Test
  public void copyRectangleTest() {
    String name = "test";
    ColorClass color = new ColorClass(1f, 0f, 0f);
    Position2D pos = new Position2D(10f, 30f);
    boolean visible = true;

    ShapeInterface test = new Rectangle(name, color, pos, 0.0f, visible, 12.3f, 43.4f);
    ShapeInterface compare = test.copy();

    assertTrue(test.equals(compare));

  }

  @Test
  public void toStringTest() {
    String name = "test";
    ColorClass color = new ColorClass(1f, 0f, 0f);
    Position2D pos = new Position2D(10f, 30f);
    boolean visible = true;

    ShapeInterface test = new Rectangle(name, color, pos, 0.0f, visible, 12.3f, 43.4f);

    String expectedResult = "Name: test\n"
            + "Type: rectangle\n"
            + "Lower-left corner: (10.0,30.0), Width: 12.3, Height: 43.4, Color: (1.0,0.0,0.0)\n";

    assertEquals(expectedResult, test.toString());

  }
}
