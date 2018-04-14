package cs3500.animator.converters;

import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.provider.ISLocation;

public class ProviderLocation implements ISLocation {
  public Position2D pos;

  public ProviderLocation(Position2D pos){
    this.pos = pos;
  }

  public ProviderLocation (ISLocation toCopy){
    this.pos = new Position2D((float)toCopy.getX(), (float)toCopy.getY());
  }

  public ProviderLocation (double x, double y){
    this.pos = new Position2D((float) x, (float) y);
  }

  @Override
  public double getX() {
    return this.pos.getX();
  }

  @Override
  public double getY() {
    return this.pos.getY();
  }

  @Override
  public void moveTo(ISLocation s) {
    this.pos.setX((float)s.getX());
    this.pos.setY((float)s.getY());
  }

  @Override
  public String toString() {
    return String.format("(%.1f,%.1f)", this.getX(), this.getY());
  }
}
