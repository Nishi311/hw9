package cs3500.animator.view.viewimplementations;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.view.interfaces.HybridViewInterface;
import cs3500.animator.view.VisualViewTypeAbstract;


/**
 * The Hybrid View. Allows for UI interaction with the animation, supporting functions like
 * Shape choosing, playback controls and exporting.
 */
public class HybridView extends VisualViewTypeAbstract implements HybridViewInterface {

  //Visual implementation parameters
  private List<ShapeInterface> shapeBlackList = new ArrayList<>();
  private HashMap<String, ShapeInterface> shapeNameToObj = new HashMap<String, ShapeInterface>();
  private List<AnimationComponentInterface> animationComponentBlackList = new ArrayList<>();
  private HybridView.DrawingPanel dPan = new DrawingPanel();
  private String outFile;

  //Visual Control parameters
  private boolean doesAnimationLoop = true;
  private int currentTick = 1;
  private JButton exportButton;
  private JTextField textField;
  private JCheckBox[] checkBox;

  //Buttons and stuff!
  JPanel controlPanel;

  private JButton resumeButton;
  private JButton pauseButton;
  private JButton restartButton;
  private JButton speedUpButton;
  private JButton speedDownButton;
  private JCheckBox loopBox;
  private JLabel tickLabel;

  //speed related stuff;
  Timer timer;
  int maxSpeed = 100;
  int minSpeed = 0;
  int tickIncrement = 5;

  /**
   * Basic constructor for the Hybrid View. Sets up all the buttons, labels, checkboxes and
   * so forth with appropriate titles and establishes the frame for viewing.
   *
   * @param model          The read-only model from which all data will be pulled.
   * @param outFile        The destination of the SVG file as needed.
   * @param ticksPerSecond The initial tick-per second rate of the animation.
   */
  public HybridView(ModelInsulatorInterface model, String outFile, int ticksPerSecond) {
    super(model, ticksPerSecond);

    this.outFile = outFile;

    JScrollPane scrollPane = new JScrollPane(dPan);
    scrollPane.setPreferredSize(new Dimension(700, 500));

    this.add(scrollPane, BorderLayout.CENTER);
    controlPanel = new JPanel();

    //Add all the buttons!
    resumeButton = new JButton("Play");
    resumeButton.setActionCommand("Play");
    controlPanel.add(resumeButton);

    pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("Pause");
    controlPanel.add(pauseButton);

    restartButton = new JButton("Restart");
    restartButton.setActionCommand("Restart");
    controlPanel.add(restartButton);

    //Add the loop checkbox
    loopBox = new JCheckBox("Loop on Complete");
    loopBox.setSelected(true);
    loopBox.setActionCommand("Loop Box");
    controlPanel.add(loopBox);

    //add the tick rate and current tick label
    tickLabel = new JLabel("<html>Ticks Per Second: " + this.ticksPerSecond +
            "<br>Current Tick: N/A</html>");

    controlPanel.add(tickLabel);

    //add speed controls
    speedUpButton = new JButton("Speed Up");
    speedUpButton.setActionCommand("Speed Up");
    controlPanel.add(speedUpButton);

    speedDownButton = new JButton("Speed Down");
    speedDownButton.setActionCommand("Speed Down");
    controlPanel.add(speedDownButton);

    JPanel checkBoxPanel;
    JLabel checkBoxDisplay;
    JLabel svgInputLabel;
    JScrollPane checkBoxScrollPane;

    //add svg stuff
    svgInputLabel = new JLabel("Enter the name for SVG file here:");
    textField = new JTextField("Output.svg");
    controlPanel.add(svgInputLabel);
    controlPanel.add(textField);

    exportButton = new JButton("Export SVG");
    exportButton.setActionCommand("Export");
    controlPanel.add(exportButton);

    //add shape selection widgets
    checkBox = new JCheckBox[shapeList.size()];
    checkBoxPanel = new JPanel();
    checkBoxScrollPane = new JScrollPane(checkBoxPanel
            , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    checkBoxPanel.setLayout(new GridLayout(100, 1));
    checkBoxScrollPane.setPreferredSize(new Dimension(300, 300));

    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Shape Selection"));
    checkBoxDisplay = new JLabel("Please choose the shapes you want to see");
    checkBoxPanel.add(checkBoxDisplay);


    for (int i = 0; i < checkBox.length; i++) {
      checkBox[i] = new JCheckBox(shapeList.get(i).getName());
      checkBox[i].setSelected(true);
      checkBox[i].setActionCommand(shapeList.get(i).getName());
      shapeNameToObj.put(shapeList.get(i).getName(), shapeList.get(i));
      checkBoxPanel.add(checkBox[i]);
    }

    this.add(checkBoxScrollPane, BorderLayout.EAST);

    //add the completed control panel and pack the frame for viewing
    this.add(controlPanel, BorderLayout.SOUTH);
    this.pack();
    this.setVisible(true);
  }

  @Override
  public void setListeners(ActionListener buttons, KeyListener keys, ItemListener items) {
    this.addKeyListener(keys);
    this.resumeButton.addActionListener(buttons);
    this.pauseButton.addActionListener(buttons);
    this.restartButton.addActionListener(buttons);
    this.speedUpButton.addActionListener(buttons);
    this.speedDownButton.addActionListener(buttons);
    this.exportButton.addActionListener(buttons);
    this.textField.addActionListener(buttons);
    this.loopBox.addItemListener(items);

    for (int i = 0; i < checkBox.length; i++) {
      this.checkBox[i].addItemListener(items);
    }

  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void selectOrUnseletShapes(String shapeName) {
    ShapeInterface shape = shapeNameToObj.get(shapeName);
    if (this.shapeBlackList.contains(shape)) {
      this.shapeBlackList.remove(shape);
    } else {
      this.shapeBlackList.add(shape);
    }
  }

  @Override
  public void speedUp() {
    if (this.ticksPerSecond + tickIncrement < maxSpeed) {
      this.ticksPerSecond += tickIncrement;
      this.milliPerTick = 1000 / ticksPerSecond;
      timer.setDelay((int) this.milliPerTick);

    } else {
      this.ticksPerSecond = 100;
      this.milliPerTick = 1000 / ticksPerSecond;
      timer.setDelay((int) this.milliPerTick);
    }

    if (currentTick == 1) {
      tickLabel.setText("<html>Ticks Per Second: " + this.ticksPerSecond +
              "<br>Current Tick: N/A</html>");
    } else {
      tickLabel.setText("<html>Ticks Per Second: " + this.ticksPerSecond +
              "<br>Current Tick: " + Integer.toString(currentTick) + "</html>");
    }

  }

  @Override
  public void speedDown() {
    if (this.ticksPerSecond - tickIncrement > minSpeed) {
      this.ticksPerSecond -= tickIncrement;
      this.milliPerTick = 1000 / ticksPerSecond;
      timer.setDelay((int) this.milliPerTick);
    } else {
      this.ticksPerSecond = 1;
      this.milliPerTick = 1000 / 1;
      timer.setDelay((int) this.milliPerTick);
    }

    if (currentTick == 1) {
      tickLabel.setText("<html>Ticks Per Second: " + this.ticksPerSecond +
              "<br>Current Tick: N/A</html>");
    } else {
      tickLabel.setText("<html>Ticks Per Second: " + this.ticksPerSecond +
              "<br>Current Tick: " + Integer.toString(currentTick) + "</html>");
    }
  }

  @Override
  public void setLooping(boolean loops) {
    this.doesAnimationLoop = loops;
  }

  @Override
  public void resume() {
    //create a new time and begin.
    if (!timer.isRunning()) {
      this.timer = new Timer((int) this.milliPerTick, new DrawListener());
      timer.start();
      tickLabel.setText("<html>Ticks Per Second: " + this.ticksPerSecond +
              "<br>Current Tick: " + Integer.toString(currentTick) + "</html>");
    }
  }

  @Override
  public void pause() {
    //kill the current timer.
    this.timer.stop();
  }

  @Override
  public void restart() {

    //clear all the lists for a fresh start
    runningAnimationList.clear();
    beginningAnimationList.clear();
    endingAnimationList.clear();

    //reset all shapes back to original parameters
    for (ShapeInterface s : shapeList) {
      s.resetShape();
    }

    //reset the tick label
    tickLabel.setText("<html>Ticks Per Second: " + this.ticksPerSecond +
            "<br>Current Tick: " + Integer.toString(currentTick) + "</html>");

    //reset the current tick.
    this.currentTick = 1;

    //restart the clock
    timer.setDelay((int) this.milliPerTick);
    this.timer.start();
  }

  @Override
  public void exportSVG() {
    try {
      List<ShapeInterface> tempShapes = new ArrayList<>();
      List<ShapeInterface> shapes = model.getShapeList();

      Map<String, List<AnimationComponentInterface>> shapeNameMap =
              model.getShapeNameToAnimationMap();
      Map<Integer, List<AnimationComponentInterface>> endMap =
              model.getEndToAnimationMap();
      List<AnimationComponentInterface> anList = model.getAnimationList();


      for (ShapeInterface shape : shapes) {
        if (!shapeBlackList.contains(shape)) {
          tempShapes.add(shape);
        }
      }

      this.outFile = textField.getText();

      SVGView svgV = new SVGView(ticksPerSecond, outFile, tempShapes, anList,
              shapeNameMap, endMap);

      if (this.doesAnimationLoop) {
        svgV.setIsLoopback(this.doesAnimationLoop);
      }

      svgV.run();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    //create and start the timer.
    this.timer = new Timer((int) this.milliPerTick, new DrawListener());
  }

  @Override
  public String viewText() {
    throw new UnsupportedOperationException("Hybrid view does not support this");
  }

  @Override
  public String getViewType() {
    return "Hybrid View";
  }

  @Override
  public int getSpeed() {
    return ticksPerSecond;
  }

  @Override
  public String getDestination() {
    throw new UnsupportedOperationException("Hybrid view does not support this");
  }

  //Custom panel class that allows for only those shapes not blacklisted to be drawn.
  //Also set to a size such that the scroll bars will show up.
  protected class DrawingPanel extends JPanel {
    public DrawingPanel() {
      this.setPreferredSize(new Dimension(1000, 1000));
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      super.paintComponent(g2);
      for (ShapeInterface shape : shapeList) {
        if (!shapeBlackList.contains(shape)) {
          shape.draw(g2);
        }
      }
    }
  }

  //The basic action that the timer will loop through.
  private class DrawListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      //if the animation is over, either reset and and start again or terminate draws
      //depending on looping behavior

      if (currentTick > endTicks.get(endTicks.size() - 1)) {
        currentTick--;
        if (doesAnimationLoop) {
          restart();
          timer.restart();
        } else {
          tickLabel.setText("<html>Ticks Per Second: " + ticksPerSecond +
                  "<br>Current Tick: N/A</html>");
          timer.stop();
        }
        //otherwise, keep looping until a button command tells otherwise.
      } else {
        try {
          runForOneTick(currentTick, dPan);
          currentTick++;
          tickLabel.setText("<html>Ticks Per Second: " + ticksPerSecond +
                  "<br>Current Tick: " + Integer.toString(currentTick) + "</html>");
        } catch (Exception ex) {
          throw new IllegalStateException("Run");
        }
      }
    }
  }

}




