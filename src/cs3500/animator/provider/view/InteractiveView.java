package cs3500.animator.provider.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;



/**
 * Class representing an Interactive View, which displays the shapes as they appear through time,
 * allowing a user to interact with the shapes by turning any of them on or off, restarting the
 * animation, changing the speed of the animation, and exporting the animation in its current state
 * as an svg file.
 */
public class InteractiveView extends ARunnableView implements IRunnableView, IPrintableView {

  private static final int WIDTH = 600;
  private static final int HEIGHT = 600;

  private JPanel sidePanel = new JPanel();
  private JPanel statusPanel = new JPanel();

  private Appendable output;
  private IController controller;

  private int endTick;

  private KeyListener kl;

  private JLabel statusLabel;

  private JButton restart;
  private JButton loop;
  private JButton pause;
  private JButton export;

  private JSpinner speedSpinner;

  private ActionListener actionListener = new ButtonActions();

  /**
   * Build an Interactive View, which displays the shapes as they appear through time, allowing a
   * user to interact with the shapes by turning any of them on or off, restarting the animation,
   * changing the speed of the animation, and exporting the animation in its current state as an svg
   * file.
   *
   * @param speed   The speed of the animation in frames per second
   * @param output  The appendable object to put the results in
   * @param endTick Tick at which the animation will end
   * @throws InvocationTargetException If an underlying method throws an error
   * @throws InterruptedException      If the initial thread is interrupted before complete
   */
  public InteractiveView(int speed, Appendable output, int endTick,
                         IController controller) throws InvocationTargetException, InterruptedException {
    super(speed);
    this.output = output;
    this.endTick = endTick;

    this.controller = controller;

    this.kl = new KeyListenerInteractions(controller);
//    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//    manager.addKeyEventDispatcher(new KeyDispatcher());

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      //Continue program as normal.
    } catch (InstantiationException e) {
      //Continue program as normal.
    } catch (IllegalAccessException e) {
      //Continue program as normal.
    } catch (UnsupportedLookAndFeelException e) {
      //Continue program as normal.
    }

    SwingUtilities.invokeAndWait(new Runnable() {
      public void run() {
        //System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension size = new Dimension(WIDTH + 200, HEIGHT + 50);
        frame.setPreferredSize(size);
        frame.setSize(size);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        Dimension mainPanelSize = new Dimension(WIDTH, HEIGHT);
        mainPanel.setSize(mainPanelSize);
        mainPanel.addKeyListener(kl);
        mainPanel.paintComponent(img.getGraphics());

        JScrollPane scrollPane = new JScrollPane(sidePanel);
        sidePanel.setLayout(new GridLayout(0, 1));
        Dimension scrollPanelSize = new Dimension(200, HEIGHT);
        scrollPane.setFocusable(false);
        scrollPane.setSize(scrollPanelSize);
        scrollPane.setPreferredSize(scrollPanelSize);


        for (IAnimShape shape : controller.getAllShapes()) {
          JCheckBox cb = new JCheckBox(shape.getName(), shape.getVisible());
          cb.addActionListener(actionListener);
          cb.setActionCommand(shape.getName());
          cb.setFocusable(false);
          sidePanel.add(cb);
        }

        Dimension statusPanelSize = new Dimension(WIDTH, 50);
        statusPanel.setSize(statusPanelSize);
        statusPanel.setLayout(new FlowLayout());
        statusPanel.setFocusable(false);
        statusLabel = new JLabel(controller.getStatus());
        statusPanel.add(statusLabel);

        restart = new JButton("Restart (R)");
        restart.addActionListener(actionListener);
        restart.setFocusable(false);
        statusPanel.add(restart);

        pause = new JButton("Pause (Space bar)");
        pause.addActionListener(actionListener);
        pause.setFocusable(false);
        statusPanel.add(pause);

        loop = new JButton("Loop (L)");
        loop.addActionListener(actionListener);
        loop.setFocusable(false);
        statusPanel.add(loop);

        SpinnerModel sm = new SpinnerNumberModel(speed, 1, 1000, 1);
        speedSpinner = new JSpinner(sm);
        speedSpinner.setValue(speed);
        speedSpinner.addChangeListener(new ChangeListener() {
          @Override
          public void stateChanged(ChangeEvent changeEvent) {
            try {
              speedSpinner.commitEdit();
            } catch (java.text.ParseException e) {
              throw new IllegalArgumentException("Speed must be an integer.");
            }
            controller.changeSpeed((Integer) speedSpinner.getValue());
            setSpeed((Integer) speedSpinner.getValue());
          }
        });

        statusPanel.add(speedSpinner);
        speedSpinner.setFocusable(false);
//        speedSpinner.setEditor(new JSpinner.DefaultEditor(speedSpinner));
//        ((JSpinner.DefaultEditor) speedSpinner.getEditor()).getTextField().setEditable(false);
        statusPanel.add(new JLabel("FPS"));

        export = new JButton("Export to SVG");
        export.addActionListener(actionListener);
        export.setFocusable(false);
        statusPanel.add(export);

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.EAST);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.pack();

        mainPanel.requestFocusInWindow();
      }
    });
  }

  /**
   * Print or export the view to a file.
   *
   * @throws IOException if the file cannot be printed to
   */
  @Override
  public void printView() throws IOException {

    final JFileChooser fileChooser = new JFileChooser(".");
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.svg", "svg"));
    int returnValue = fileChooser.showSaveDialog(statusPanel);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      this.output = new PrintStream(fileChooser.getSelectedFile());
    }

    SVGView svg = new SVGView(speed, output, controller.isLooping(), endTick);
    svg.setSpeed(speed);
    svg.setShapes(controller.getAllShapes());
    svg.setTransformations(controller.getAllTransformations());

    svg.printView();
  }

  /**
   * Update the view with new data for the current tick.
   *
   * @throws IOException if the output file cannot be printed to.
   */
  @Override
  public void updateView(List<IAnimShape> shapeList, String status) throws IOException {
    super.updateView(shapeList, status);
    statusLabel.setText(status);
  }

  /**
   * Set the transformations for the view to print out.
   */
  @Override
  public void setTransformations(List<ITransformation> transformations) {
    //Unused method from interface
  }

  /**
   * Set the speed of the animation.
   */
  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
    speedSpinner.setValue(speed);
  }

  private class ButtonActions implements ActionListener {

    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource().getClass().equals(JCheckBox.class)) {
        controller.toggleVisible(e.getActionCommand());
      } else if (e.getSource().equals(restart)) {
        controller.restart();
        controller.changeStatus("Restarting");

      } else if (e.getSource().equals(loop)) {
        controller.toggleLoopBack();
        if (controller.isLooping()) {
          controller.changeStatus("Enabled Looping");
        } else {
          controller.changeStatus("Disabled Looping");
        }
      } else if (e.getSource().equals(pause)) {
        controller.pauseAnimation();
        if (controller.getPaused()) {
          controller.changeStatus("Paused");
        } else {
          controller.changeStatus("Playing");
        }
      } else if (e.getSource().equals(export)) {
        controller.changeStatus("Exporting Animation...");
        statusLabel.setText(controller.getStatus());
        try {
          printView();
        } catch (IOException ioe) {
          throw new IllegalStateException("Cannot write to file.");
        }
        controller.changeStatus("Exported Animation");
      }
      statusLabel.setText(controller.getStatus());
    }
  }
}
