package cs3500.animator.view.viewimplementations;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JTextArea;


import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.view.VisualViewTypeAbstract;

/**
 * This class represents the visual of animation. Similar to HybridView but does not have the
 * same control functions. Will simply run the animation from start to finish. Does not loop
 * animation.
 */
public class VisualView extends VisualViewTypeAbstract {
  private VisualView.DrawingPanel dPan = new DrawingPanel();
  private int currentTick = 1;
  private Timer timer;
  private JTextArea status;

  /**
   * Basic constructor for the VisualView. Takes model interface and grabs everything necessary.
   * Also sets up JFrame and JPanel extending DrawingPanel.
   *
   * @param model          The model to be used when building.
   * @param ticksPerSecond The number of ticksPerSecond for the animation.
   */
  public VisualView(ModelInsulatorInterface model, int ticksPerSecond) {
    super(model, ticksPerSecond);
    this.status = new JTextArea("Starting");
    this.add(status, BorderLayout.SOUTH);

    dPan.setPreferredSize(new Dimension(2000, 2000));

    JScrollPane scrollPane = new JScrollPane(dPan);
    scrollPane.setPreferredSize(new Dimension(2000, 2000));
    this.add(scrollPane, BorderLayout.CENTER);
    this.setVisible(true);
  }

  @Override
  public String viewText() {
    return null;
  }

  @Override
  public String getViewType() {
    return "Visual View";
  }

  @Override
  public int getSpeed() {
    return ticksPerSecond;
  }

  @Override
  public String getDestination() {
    return "screen";
  }

  @Override
  public void run() {
    //set up the timer to run the DrawWaiter execution as often as our speed designates.
    //Then start the timer. The DrawWaiter will kill the timer by itself.
    timer = new Timer((int) milliPerTick, new DrawWaiter());
    timer.start();
  }

  //custom panel that will have all shapes drawn on it automatically.
  protected class DrawingPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      super.paintComponent(g2);
      //draw all shapes.
      for (Map.Entry<Integer, List<ShapeInterface>> entry : layerMap.entrySet()) {
        List<ShapeInterface> newList = entry.getValue();
        for (ShapeInterface shape : newList) {
          shape.draw(g2);
        }
      }
    }
  }

  //The basic action that the timer will loop through.
  private class DrawWaiter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      //if we haven't reached the final tick, keep drawing and updating.
      if (currentTick <= endTicks.get(endTicks.size() - 1)) {
        status.setText("Tick: " + currentTick);
        runForOneTick(currentTick, dPan);
        currentTick++;
        //otherwise, we've finished. Set status bar accordingly and kill the timer.
      } else {
        status.setText("Finished");
        timer.stop();
      }
    }
  }
}
