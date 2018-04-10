import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JCheckBox;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.controller.controllerimplementations.TestControllerHybrid;
import cs3500.animator.view.interfaces.ViewFactoryInterface;
import cs3500.animator.model.AnimationModelText;
import cs3500.animator.model.factories.AnimationComponentFactoryBasic;
import cs3500.animator.model.factories.ShapeFactoryBasic;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.view.ViewFactoryWithHybrid;
import cs3500.animator.view.viewimplementations.TestHybridView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ControllerHybridTests {
  ViewFactoryInterface vFac = new ViewFactoryWithHybrid();

  AnimationModelInterface model = new AnimationModelText(new ShapeFactoryBasic(),
          new AnimationComponentFactoryBasic());

  TestControllerHybrid controller = new TestControllerHybrid(model, vFac);
  TestHybridView view = new TestHybridView(new ModelInsulator(model), "out", 1);

  @Before
  public void setUp() {
    controller.hybridView = view;
  }

  @Test
  public void buttonTests() {

    ActionEvent pause = new ActionEvent("test", 1, "Pause");
    ActionEvent run = new ActionEvent("test", 1, "Play");
    ActionEvent restart = new ActionEvent("test", 1, "Restart");
    ActionEvent speedUp = new ActionEvent("test", 1, "Speed Up");
    ActionEvent speedDown = new ActionEvent("test", 1, "Speed Down");
    ActionEvent exportSVG = new ActionEvent("test", 1, "Export");


    controller.actionPerformed(pause);
    controller.actionPerformed(run);
    controller.actionPerformed(restart);
    controller.actionPerformed(speedUp);
    controller.actionPerformed(speedDown);
    controller.actionPerformed(exportSVG);


    assertTrue(controller.hybridView.isPaused);
    assertTrue(controller.hybridView.isRunning);
    assertTrue(controller.hybridView.isRestarting);
    assertTrue(controller.hybridView.speedUp);
    assertTrue(controller.hybridView.speedDown);
    assertTrue(controller.hybridView.isExporting);
  }

  @Test
  public void checkBoxTests() {
    JCheckBox loopBox = new JCheckBox("Loop on Complete");
    loopBox.setSelected(true);
    loopBox.setActionCommand("Loop Box");
    ItemEvent loopTrue = new ItemEvent(loopBox, 1, "Loop Box", ItemEvent.SELECTED);


    controller.itemStateChanged(loopTrue);
    assertTrue(controller.hybridView.isLooping);

    loopBox.setSelected(false);
    ItemEvent loopFalse = new ItemEvent(loopBox, 1, "Loop Box", ItemEvent.DESELECTED);

    controller.itemStateChanged(loopFalse);
    assertFalse(controller.hybridView.isLooping);


  }
}
