package cs3500.animator.model.interfaces;

public interface DurationInterface {
  /**
   * Allows for the retrieval of the start time.
   *
   * @return the duration's start time.
   */
  int getStartTime();

  /**
   * Allows for the retrieval of the end time.
   *
   * @return the duration's end time.
   */
  int getEndTime();

  /**
   * Allows for the return of the length of time represented by the duration. This is inclusive (e.g
   * t=1 to t-5 has a duration of 5).
   *
   * @return The length of time represented by this duration.
   */
  int getTotalDuration();

  /**
   * Standard equality override.
   *
   * @param obj The object to comapre to.
   * @return True if object is DurationInterface with identical start and end time. False otherwise.
   */
  boolean equals(Object obj);

  /**
   * Standard hashcode override.
   *
   * @return Hashcode of DurationInterface.
   */
  int hashCode();
}
