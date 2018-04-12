package cs3500.animator.provider.view;

import java.io.IOException;
import java.util.List;

import cs3500.animator.provider.ITransformation;


public interface IPrintableView extends IViewable {

  /**
   * Print or export the view to a file.
   * @throws IOException    if the file cannot be printed to.
   */
  void printView() throws IOException;

  /**
   * Set the transformations for the view to print out.
   * @param transformations
   */
  void setTransformations(List<ITransformation> transformations);


  /**
   * Set the speed of the animation.
   */
  void setSpeed(int speed);

}
