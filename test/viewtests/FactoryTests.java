package viewtests;

import org.junit.Before;
import org.junit.Test;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.view.ViewFactory;
import cs3500.animator.view.ViewTypes;
import cs3500.animator.model.AnimationModelText;
import cs3500.animator.model.factories.AnimationComponentFactoryBasic;
import cs3500.animator.model.factories.ShapeFactoryBasic;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.view.interfaces.ViewInterface;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class FactoryTests {
  AnimationModelInterface model;
  ModelInsulator modelInsulator;
  ViewFactory vFac;
  ViewInterface testView;

  /**
   * Basic set up. Creates new model Insulator and view factory for use in tests.
   */
  @Before
  public void setUp() {
    model = new AnimationModelText(new ShapeFactoryBasic(), new AnimationComponentFactoryBasic());
    modelInsulator = new ModelInsulator(model);
    vFac = new ViewFactory();
  }

  @Test
  public void textReturnValid() {
    try {
      testView = vFac.create(ViewTypes.TEXT, modelInsulator, "out", 1);
    } catch (Exception e) {
      fail();
    }

    assertTrue(testView.getViewType().equals("Text View"));
  }

  @Test
  public void svgReturnValid() {
    try {
      testView = vFac.create(ViewTypes.SVG, modelInsulator, "out", 1);
    } catch (Exception e) {
      fail();
    }

    assertTrue(testView.getViewType().equals("SVG View"));
  }

  @Test
  public void visualReturnValid() {
    try {
      testView = vFac.create(ViewTypes.VISUAL, modelInsulator, "out", 1);
    } catch (Exception e) {
      fail();
    }

    assertTrue(testView.getViewType().equals("Visual View"));
  }

  @Test
  public void nullModelTest() {
    modelInsulator = null;
    try {
      testView = vFac.create(ViewTypes.TEXT, modelInsulator, "out", 1);
      fail();
    } catch (Exception e) {
      assertTrue(e.getMessage().equals("Cannot accept null model"));
    }
  }

  @Test
  public void nullType() {

    try {
      testView = vFac.create(null, modelInsulator, "out", 1);
      fail();
    } catch (Exception e) {
      assertTrue(e.getMessage().equals("Unknown view type"));
    }
  }

}
