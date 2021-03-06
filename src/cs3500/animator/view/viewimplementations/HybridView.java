package cs3500.animator.view.viewimplementations;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;

import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.view.interfaces.HybridViewInterface;
import cs3500.animator.view.VisualViewTypeAbstract;
import javafx.scene.control.TextFormatter;


/**
 * The Hybrid View. Allows for UI interaction with the animation, supporting functions like
 * Shape choosing, playback controls and exporting.
 */
public class HybridView extends VisualViewTypeAbstract implements HybridViewInterface {

  //Visual implementation parameters
  private List<ShapeInterface> shapeBlackList = new ArrayList<>();
  private HashMap<String, ShapeInterface> shapeNameToObj = new HashMap<String, ShapeInterface>();
  private HybridView.DrawingPanel dPan = new DrawingPanel();
  private String outFile;

  //Visual Control parameters
  private boolean doesAnimationLoop = true;
  private int currentTick = 1;
  private JButton exportButton;
  private JTextField textField;
  private Map<Integer, List<JCheckBox>> shapeCheckBoxMap = new HashMap<>();

  //Buttons and stuff!
  JPanel controlPanel;

  private JButton resumeButton;
  private JButton pauseButton;
  private JButton restartButton;

  private JSpinner speedSpinner;
  private JCheckBox loopBox;
  private JLabel tickLabel;
  private JSlider slider;

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
    scrollPane.setPreferredSize(new Dimension(800, 800));

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
    tickLabel = new JLabel("<html>Speed Controller (Ticks / Second):" +
            " <br>Current Tick: N/A</html>");

    controlPanel.add(tickLabel);

    //add speed controls
    SpinnerModel sm = new SpinnerNumberModel(ticksPerSecond, 1, 1000, 1);
    speedSpinner = new JSpinner(sm);
    speedSpinner.setValue(ticksPerSecond);
    speedSpinner.setFocusable(false);
    controlPanel.add(speedSpinner);


    //add svg stuff
    JLabel svgInputLabel;
    svgInputLabel = new JLabel("Enter the name for SVG file here:");
    textField = new JTextField("Output.svg");
    controlPanel.add(svgInputLabel);
    controlPanel.add(textField);

    exportButton = new JButton("Export SVG");
    exportButton.setActionCommand("Export");
    controlPanel.add(exportButton);

    //add scrubbing slider

    final int FPS_MIN = 0;
    final int FPS_MAX = endTicks.get(endTicks.size() - 1);
    final int FPS_INIT = 0;

    JPanel sliderPanel = new JPanel();
    slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
    slider.setPreferredSize(new Dimension(900, 30));
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    sliderPanel.add(slider, BorderLayout.CENTER);


    //add shape selection stuff.
    JPanel checkBoxPanel;
    JLabel checkBoxDisplay;
    JScrollPane checkBoxScrollPane;

    //Create over-arching shape selection display panel.
    checkBoxPanel = new JPanel();
    checkBoxScrollPane = new JScrollPane(checkBoxPanel
            , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));
    checkBoxScrollPane.setPreferredSize(new Dimension(500, 300));

    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Shape Selection: Please choose the " +
            "shapes you want to see "));

    //Fill in the shapeCheckBoxMap. Each key is the number of the layer (key "1" is for layer
    //one) and each JCheckBox in an key's corresponding list represents one shape in the layer.
    for (Map.Entry<Integer, List<ShapeInterface>> entry : layerMap.entrySet()) {
      List<ShapeInterface> newList = entry.getValue();
      List<JCheckBox> checkBoxList = new ArrayList<>();
      for (ShapeInterface shape : newList) {
        JCheckBox temp = new JCheckBox(shape.getName());
        temp.setSelected(true);
        temp.setActionCommand(shape.getName());
        checkBoxList.add(temp);
        shapeNameToObj.put(shape.getName(), shape);
      }
      shapeCheckBoxMap.put(entry.getKey(), checkBoxList);
    }

    //reverse the order of the keys so top layer (highest number) is displayed first.
    List<Integer> reverseOrder = new ArrayList<>(shapeCheckBoxMap.keySet());
    Collections.reverse(reverseOrder);

    //Go through the reversed key order and add each sub-panel to the main checkbox panel.
    for (Integer i : reverseOrder) {
      JPanel layerPanel = new JPanel();
      layerPanel.setBorder(BorderFactory.createTitledBorder("Layer " + i));
      layerPanel.setLayout(new GridLayout(20, 5));
      List<JCheckBox> temp = shapeCheckBoxMap.get(i);
      Collections.reverse(temp);
      for (JCheckBox check : temp) {
        layerPanel.add(check);
      }

      JScrollPane layerPanelScroll = new JScrollPane(layerPanel);

      checkBoxPanel.add(layerPanelScroll);
    }

    //add the completed panel to the frame.
    this.add(checkBoxScrollPane, BorderLayout.EAST);

    //creates and adds the hyper control panel that contains both the slider and the controls
    JPanel hyperControlPanel = new JPanel();
    hyperControlPanel.setLayout(new BorderLayout());
    hyperControlPanel.add(controlPanel, BorderLayout.SOUTH);
    hyperControlPanel.add(sliderPanel, BorderLayout.NORTH);

    //add the completed control panel and pack the frame for viewing
    this.add(hyperControlPanel, BorderLayout.SOUTH);
    this.pack();
    this.setVisible(true);
  }

  @Override
  public void setListeners(ActionListener buttons, ChangeListener changes, ItemListener items) {
    this.resumeButton.addActionListener(buttons);
    this.pauseButton.addActionListener(buttons);
    this.restartButton.addActionListener(buttons);
    this.speedSpinner.addChangeListener(changes);
    this.exportButton.addActionListener(buttons);
    this.textField.addActionListener(buttons);
    this.loopBox.addItemListener(items);
    this.slider.addChangeListener(changes);

    for (Map.Entry<Integer, List<JCheckBox>> entry : shapeCheckBoxMap.entrySet()) {
      List<JCheckBox> temp = entry.getValue();
      for (JCheckBox check : temp) {
        check.addItemListener(items);
      }
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
  public void setSpeed(int newSpeed) {
    this.ticksPerSecond = newSpeed;
    this.milliPerTick = 1000 / ticksPerSecond;
    timer.setDelay((int) this.milliPerTick);
    timer.setInitialDelay((int) this.milliPerTick);
    if (currentTick == 1) {
      tickLabel.setText("<html>Speed Controller (Ticks / Second): <br>Current Tick: N/A</html>");
    } else {
      tickLabel.setText("<html>Speed Controller (Ticks / Second):" +
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
      tickLabel.setText("<html>Speed Controller (Ticks / Second):" +
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
    tickLabel.setText("<html>Speed Controller (Ticks / Second):" +
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

      //reverses the key order to ensure that larger key numbers and draw last.
      List<Integer> keyList = new ArrayList<>(layerMap.keySet());
      Collections.reverse(keyList);

      //draw all shapes.
      for (Map.Entry<Integer, List<ShapeInterface>> entry : layerMap.entrySet()) {
        List<ShapeInterface> newList = entry.getValue();
        for (ShapeInterface shape : newList) {
          if (!shapeBlackList.contains(shape)) {
            shape.draw(g2);
          }
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
          tickLabel.setText("<html>Speed Controller (Ticks / Second):" +
                  "<br>Current Tick: N/A</html>");
          timer.stop();
        }
        //otherwise, keep looping until a button command tells otherwise.
      } else {
        try {
          runForOneTick(currentTick, dPan);
          currentTick++;
          tickLabel.setText("<html>Speed Controller (Ticks / Second):" +
                  "<br>Current Tick: " + Integer.toString(currentTick) + "</html>");
        } catch (Exception ex) {
          throw new IllegalStateException("Run");
        }
      }
    }
  }

}




