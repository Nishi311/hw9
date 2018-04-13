package cs3500.animator.provider.view;

import java.util.List;
import cs3500.animator.provider.IAnimShape;

/**
 * This is the interface for a View object, which displays the shapes in some form, allowing
 * the viewer to see when shapes exist and how they change.
 */
public interface IViewable {

  /**
   * Set the shapes for the view to print out.
   * @param shapes  shapes to be given to the view
   */
  void setShapes(List<IAnimShape> shapes);
}
