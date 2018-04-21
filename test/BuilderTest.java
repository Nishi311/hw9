import org.junit.Test;

import java.util.List;

import cs3500.animator.controller.interfaces.TweenModelBuilder;
import cs3500.animator.model.AnimationModelText;

import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.factories.AnimationComponentFactoryBasic;
import cs3500.animator.model.factories.ShapeFactoryBasic;
import cs3500.animator.model.interfaces.AnimationComponentFactoryInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeFactoryInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

import static junit.framework.TestCase.assertEquals;

public class BuilderTest {
  private TweenModelBuilder<AnimationModelText> builder = new AnimationModelText.Builder();
  private ColorClass expectedColor = new ColorClass(2f, 3f, 4f);
  private Duration expectedDur = new Duration(10, 30);
  private Position2D expectedPos = new Position2D(0f, 0f);

  private ShapeFactoryInterface sFac = new ShapeFactoryBasic();
  private AnimationComponentFactoryInterface aFac = new AnimationComponentFactoryBasic();

  @Test
  public void addOvalValid() {

    builder.addOval("Oval1", 0f, 0f, 1f, 1f, 2f, 3f, 4f,
            10, 30, 1);

    ShapeInterface expectedOval = sFac.create("Oval1", "Oval",
            expectedColor, expectedPos, 0f, 1f, 1f);

    AnimationComponentInterface expectedVisible = aFac.create(expectedOval,
            "Visibility Change", new Duration(10), true);

    AnimationComponentInterface expectedInvisible = aFac.create(expectedOval,
            "Visibility Change", new Duration(30), false);

    List<ShapeInterface> shapeList = builder.build().getShapeList();
    List<AnimationComponentInterface> animList = builder.build().getAnimationList();

    assertEquals(expectedOval.toString(), shapeList.get(0).toString());

    assertEquals(expectedVisible.getTarget(), animList.get(0).getTarget());
    assertEquals(expectedVisible.getStartTime(), animList.get(0).getStartTime());

    assertEquals(expectedInvisible.getTarget(), animList.get(1).getTarget());
    assertEquals(expectedInvisible.getStartTime(), animList.get(1).getStartTime());

  }

  @Test
  public void addRectangleValid() {
    builder.addRectangle("Rectangle1", 0f, 0f, 1f, 1f, 2f, 3f, 4f,
            10, 30, 1);

    ShapeInterface expectedRec = sFac.create("Rectangle1", "Rectangle",
            expectedColor, expectedPos, 0f, 1f, 1f);

    AnimationComponentInterface expectedVisible = aFac.create(expectedRec,
            "Visibility Change", new Duration(10), true);

    AnimationComponentInterface expectedInvisible = aFac.create(expectedRec,
            "Visibility Change", new Duration(30), false);

    List<ShapeInterface> shapeList = builder.build().getShapeList();
    List<AnimationComponentInterface> animList = builder.build().getAnimationList();

    assertEquals(expectedRec.toString(), shapeList.get(0).toString());

    assertEquals(expectedVisible.getTarget(), animList.get(0).getTarget());
    assertEquals(expectedVisible.getStartTime(), animList.get(0).getStartTime());

    assertEquals(expectedInvisible.getTarget(), animList.get(1).getTarget());
    assertEquals(expectedInvisible.getStartTime(), animList.get(1).getStartTime());
  }

  @Test
  public void addMoveValid() {
    builder.addOval("Oval1", 0f, 0f, 1f, 1f, 2f, 3f, 4f,
            10, 30, 1);

    ShapeInterface expectedOval = sFac.create("Oval1", "Oval",
            expectedColor, expectedPos, 0f, 1f, 1f);

    Position2D expectedEndPos = new Position2D(1f, 1f);

    AnimationComponentInterface expectedAnimation = aFac.create(expectedOval,
            "Position Change", expectedDur, expectedPos, expectedEndPos);

    builder.addMove("Oval1", 0f, 0f, 1f, 1f,
            10, 30);
    List<AnimationComponentInterface> animList = builder.build().getAnimationList();

    assertEquals(expectedAnimation.toString(), animList.get(1).toString());
  }

  @Test
  public void addScaleValid() {

    builder.addOval("Oval1", 0f, 0f, 1f, 1f, 2f, 3f, 4f,
            10, 30, 1);

    builder.addRectangle("Rectangle1", 0f, 0f, 1f, 1f, 2f, 3f, 4f,
            10, 30, 1);

    ShapeInterface expectedOval = sFac.create("Oval1", "Oval",
            expectedColor, expectedPos, 0f, 1f, 1f);

    ShapeInterface expectedRec = sFac.create("Rectangle1", "Rectangle",
            expectedColor, expectedPos, 0f, 1f, 1f);

    Position2D expectedEndPos = new Position2D(1f, 1f);

    AnimationComponentInterface expectedAnimationOval = aFac.create(expectedOval,
            "Scale Change RR", expectedDur, 0f, 0f, 1f, 1f);

    AnimationComponentInterface expectedAnimationRec = aFac.create(expectedRec,
            "Scale Change WH", expectedDur, 0f, 0f, 1f, 1f);

    builder.addScaleToChange("Oval1", 0f, 0f, 1f, 1f,
            10, 30);

    builder.addScaleToChange("Rectangle1", 0f, 0f, 1f, 1f,
            10, 30);
    List<AnimationComponentInterface> animList = builder.build().getAnimationList();

    assertEquals(expectedAnimationOval.toString(), animList.get(2).toString());
    assertEquals(expectedAnimationRec.toString(), animList.get(3).toString());

  }

  @Test
  public void addColor() {
    builder.addOval("Oval1", 0f, 0f, 1f, 1f, 2f, 3f, 4f,
            10, 30, 1);

    ShapeInterface expectedOval = sFac.create("Oval1", "Oval",
            expectedColor, expectedPos, 0f, 1f, 1f);

    builder.addColorChange("Oval1", 2f, 3f, 4f, 5f,
            6f, 7f, 10, 30);

    AnimationComponentInterface expectedAnimationOval = aFac.create(expectedOval,
            "Color Change", expectedDur, expectedColor, new ColorClass(
                    5f, 6f, 7f));

    List<AnimationComponentInterface> animList = builder.build().getAnimationList();

    assertEquals(expectedAnimationOval.toString(), animList.get(1).toString());

  }
}
