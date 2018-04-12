package cs3500.animator.provider.view;

import java.util.List;
import cs3500.animator.animshape.IAnimShape;
import cs3500.animator.transformations.ITransformation;

/**
 * This is the interface for a View object, which displays the shapes in some form, allowing
 * the viewer to see when shapes exist and how they change.
 */
public interface IViewable {

  /**
   * Set the shapes for the view to print out.
   * @param shapes  Shapes to be given to the view
   */
  void setShapes(List<IAnimShape> shapes);
}