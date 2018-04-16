package cs3500.animator.provider.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ISColor;


/**
 * AnimationPanel extends JPanel to add the functionality of painting frames as desired.
 */
public class AnimationPanel extends JPanel {

  protected final double xScale;
  protected final double yScale;

  static List<IAnimShape> shapes;

  AnimationPanel(List<IAnimShape> s, BufferedImage buf, int width, int height) {
    shapes = s;
    //BufferedImage img = buf;
    xScale = width / 800d;
    yScale = height / 800d;
  }

  /**
   * Draw one shape, adding it onto the current picture.
   */
  private void drawShape(Graphics g, IAnimShape shape) {
    switch (shape.getShapeType()) {
      case "rectangle":
        g.setColor(getColorFromSColor(shape.getColor()));
        int rx = (int) (shape.getLocation().getX() * xScale);
        int ry = (int) (shape.getLocation().getY() * yScale);
        int rw = (int) (shape.getPValues()[0] * xScale);
        int rh = (int) (shape.getPValues()[1] * yScale);
        g.drawRect(rx, ry, rw, rh);
        g.fillRect(rx, ry, rw, rh);
        break;
      case "oval":
        g.setColor(getColorFromSColor(shape.getColor()));
        int ox = (int) (shape.getLocation().getX() * xScale);
        int oy = (int) (shape.getLocation().getY() * yScale);
        int ow = (int) (shape.getPValues()[0] * 2 * xScale);
        int oh = (int) (shape.getPValues()[1] * 2 * yScale);
        g.drawOval(ox - (ow / 2), oy - (oh / 2), ow, oh);
        g.fillOval(ox - (ow / 2), oy - (oh / 2), ow, oh);
        break;
      default:
        throw new IllegalStateException("This type of shape, \"" + shape.getShapeType()
                + "\", does not exist.");
    }
  }

  /**
   * Override the paintComponent method of JPanel to add functionality for our animation.
   * @param g   Graphics object
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (IAnimShape shape : shapes) {
      drawShape(g, shape);
    }

    repaint();
    g.dispose();
  }

  /**
   * Static method bridging SColor to java.awt.Color. This method is not in SColor so that one
   * wishing not to use this view does not have to import java.awt.Color.
   * @param color   SColor to be turned into java.awt.Color
   * @return        java.awt.Color representing the same color as the given SColor
   */
  private static Color getColorFromSColor(ISColor color) {
    return new Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue());
  }
}
