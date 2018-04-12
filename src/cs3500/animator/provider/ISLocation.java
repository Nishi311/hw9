package cs3500.animator.provider;

public interface ISLocation {
  /**
   * Return the current xloc value.
   *
   * @return xloc
   */
  double getX();

  /**
   * Return the current yloc value.
   *
   * @return yloc
   */
  double getY();

  /**
   * Overloaded moveTo method.
   *
   * @param s Target to move to
   */
  void moveTo(ISLocation s);
}
