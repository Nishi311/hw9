package cs3500.animator.provider.view;

import java.io.IOException;

import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ITransformation;


/**
 * This class represents a Text View object, which displays the shapes in the form of words either
 * to the console or a file, so that a viewer may read about when and how the shapes exist.
 */
public class TextView extends APrintableView implements IPrintableView {

  /**
   * Build a Text View object, which describes the shapes and transformations as text,
   * allowing the viewer to see when shapes exist and how they change.
   * @param output    The appendable object to put the results in
   */
  public TextView(int speed, Appendable output) {
    super(speed, output);
  }

  /**
   * Print or export the view to a file.
   *
   * @throws IOException if the file cannot be printed to.
   */
  @Override
  public void printView() throws IOException {
    if(speed == 0) {
      throw new IllegalStateException("Speed cannot be 0.");
    }
    if(shapes == null) {
      throw new IllegalStateException("The list of shapes cannot be null.");
    }
    if(transformations == null) {
      throw new IllegalStateException("The list of transformations cannot be null.");
    }

    for (IAnimShape shape : shapes) {
      String test = String.format("Name: %s\n"
                      + "Type: %s\n"
                      + "%s: %s, %s, Color: %s\n"
                      + "Appears at t=%.1fs\n"
                      + "Disappears at t=%.1fs\n\n",
              shape.getName(), shape.getShapeType(), shape.getShape().getReference(),
              shape.getLocation(), shape.getShape().toString() , shape.getColor(),
              (double)shape.getAppears() / speed, (double)shape.getDisappears() / speed);

      output.append(test);
    }
    for (ITransformation transformation : transformations) {
      output.append(String.format("Shape %s %s from %s to %s from t=%.1fs to t=%.1fs\n",
              transformation.getShapeName(), transformation.getInfo()[1],
              transformation.getInfo()[2], transformation.getInfo()[3],
              (double)transformation.getStartTick() / speed, (double)transformation.getEndTick()));
    }
  }
}


