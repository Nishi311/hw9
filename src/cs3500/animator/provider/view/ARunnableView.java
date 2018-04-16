package cs3500.animator.provider.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import cs3500.animator.provider.IAnimShape;

/**
 * This is the abstract class for a runnable View object, which displays the shapes in a visual
 * form, allowing the viewer to see when shapes exist and how they change.
 */
public abstract class ARunnableView extends AView implements IRunnableView {

  protected static final int WIDTH = 600;
  protected static final int HEIGHT = 600;

  protected List<IAnimShape> shapes = new ArrayList<IAnimShape>();
  protected boolean running = false;
  protected BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
  protected JFrame frame = new JFrame("Easy Animator");
  protected AnimationPanel mainPanel = new AnimationPanel(shapes, img, WIDTH, HEIGHT);

  /**
   * Build a runnable View object, which displays the shapes and transformations visually,
   * allowing the viewer to see when shapes exist and how they change.
   * @param speed    The speed at which the animation will run, in frames per second
   */
  public ARunnableView(int speed) {
    super(speed);
    if (shapes == null) {
      throw new IllegalArgumentException("The list of shapes cannot be null.");
    }

  }

  /**
   * Update the view with new data for the current tick.
   *
   * @throws IOException if the output file cannot be printed to.
   */
  @Override
  public void updateView(List<IAnimShape> shapeList, String status) throws IOException {
    AnimationPanel.shapes = shapeList;
    mainPanel.paintComponent(img.getGraphics());
  }

  /**
   * End the view, however it needs to be done (i.e. close a window, end of file, close a stream,
   * etc.)
   */
  @Override
  public void closeView() throws IOException {
    frame.dispose();
    running = false;
  }

  /**
   * Set the list of shapes for the view to display.
   */
  @Override
  public void setShapes(List<IAnimShape> currentList) {
    if (currentList == null) {
      throw new IllegalStateException("The list of current shapes cannot be null.");
    }
    this.shapes = currentList;
  }
}


