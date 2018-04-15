package cs3500.animator.provider.view;

import java.io.IOException;
import java.util.List;

import cs3500.animator.provider.IAnimShape;

public interface IRunnableView extends IViewable {

  /**
   * Update the view with new data for the current tick.
   *
   * @throws IOException if the output file cannot be printed to.
   */
  void updateView(List<IAnimShape> shapeList, String status) throws IOException;

  /**
   * End the view, however it needs to be done (i.e. close a window, end of file,
   * close a stream, etc.)
   *
   * @throws IOException if the output file cannot be printed to.
   */
  void closeView() throws IOException;

}
