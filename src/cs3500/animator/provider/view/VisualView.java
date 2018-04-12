package cs3500.animator.provider.view;

import java.awt.Dimension;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This is the class for a Visual View object, which displays the shapes Java Swing application on
 * the screen, allowing the viewer to see when shapes exist and how they change.
 */
public class VisualView extends ARunnableView implements IRunnableView {

  private static final int WIDTH = 600;
  private static final int HEIGHT = 600;

  /**
   * Build a Visual View object, which displays the shapes in a Java Swing application,
   * allowing the viewer to see when shapes exist and how they change.
   */
  public VisualView(int speed) {
    super(speed);

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        //System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension size = new Dimension(WIDTH, HEIGHT);
        frame.setPreferredSize(size);
        frame.setSize(size);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();

        frame.add(mainPanel);

        frame.setVisible(true);

        mainPanel.paintComponent(img.getGraphics());
      }
    });
  }
}
