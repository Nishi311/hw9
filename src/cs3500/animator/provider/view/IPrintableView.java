package cs3500.animator.provider.view;

import java.io.IOException;
import java.util.List;

import cs3500.animator.provider.ITransformation;

/**
 * This is the interface for a printable View object, which displays the shapes and transformations
 * as text, allowing the viewer to read when shapes exist and how they change.
 */
public interface IPrintableView extends IViewable {

  /**
   * Print or export the view to a file.
   * @throws IOException if the file cannot be printed to.
   */
  void printView() throws IOException;

  /**
   * Set the transformations for the view to print out.
   * @param transformationList List of transformations to give to the view.
   */
  void setTransformations(List<ITransformation> transformationList);


  /**
   * Set the speed of the animation.
   */
  void setSpeed(int speed);

}
