package cs3500.animator.model.concreteclasses.utilityclasses;

import java.util.Objects;

import cs3500.animator.model.interfaces.DurationInterface;

/**
 * Basic class that allows for the representation of a period of time. Start is the beginning of
 * the period and End is the end of the duration.
 * INVARIANTS:
 * 1.) Cannot have negative times.
 * 2.) End time cannot be fore start time.
 */
public class Duration implements DurationInterface{
  private int startTime;
  private int endTime;

  /**
   * Basic Duration constructor.
   *
   * @param startTime beginning of the duration period.
   * @param endTime   end of the duration period.
   * @throws IllegalArgumentException If either of the times are negative or if the end time is
   *                                  before the start time.
   */
  public Duration(int startTime, int endTime) throws IllegalArgumentException {
    if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("Cannot have negative times");
    }
    if (startTime > endTime) {
      throw new IllegalArgumentException("End time cannot be before start time");
    }
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Single parameter Duration constructor. Essentially represents one point in time (e.g t=5).
   *
   * @param time The point in time that the duration represents.
   * @throws IllegalArgumentException If the time is negative.
   */
  public Duration(int time) throws IllegalArgumentException {
    if (time < 0) {
      throw new IllegalArgumentException("Cannot have negative times");
    }
    this.startTime = time;
    this.endTime = time;
  }

  /**
   * Copy Duration constructor.
   *
   * @param copyDur Duration to be copied.
   */
  public Duration(DurationInterface copyDur) {
    this.startTime = copyDur.getStartTime();
    this.endTime = copyDur.getEndTime();
  }

  /**
   * Default Duration constructor. Sets the start and end time to 0.
   */
  public Duration() {
    this.startTime = 0;
    this.endTime = 0;
  }

  /**
   * Allows for the retrieval of the start time.
   *
   * @return the duration's start time.
   */
  public int getStartTime() {
    return startTime;
  }

  /**
   * Allows for the retrieval of the end time.
   *
   * @return the duration's end time.
   */
  public int getEndTime() {
    return endTime;
  }

  /**
   * Allows for the return of the length of time represented by the duration. This is inclusive (e.g
   * t=1 to t-5 has a duration of 5).
   *
   * @return The length of time represented by this duration.
   */
  public int getTotalDuration() {
    return endTime - startTime + 1;
  }


  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof DurationInterface)) {
      return false;
    }
    DurationInterface compareDur = (Duration) obj;

    if (startTime == compareDur.getStartTime()) {
      if (endTime == compareDur.getEndTime()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTime, endTime);
  }

}
