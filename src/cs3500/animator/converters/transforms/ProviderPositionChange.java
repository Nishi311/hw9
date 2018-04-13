package cs3500.animator.converters.transforms;

import cs3500.animator.converters.ProviderLocation;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.interfaces.AnimationComponentInterface;

public class ProviderPositionChange extends ProviderTransformAbstract {

    private Position2D startingPos;
    private Position2D endingPos;

    private double xIncrement;
    private double yIncrement;

    public ProviderPositionChange(AnimationComponentInterface amCom){
      super(amCom);

      this.startingPos = (Position2D) amCom.getInitialParameters().get(0);
      this.endingPos = (Position2D) amCom.getFinalParameters().get(0);

      this.transformInfo[1] = "moves";
      transformInfo[2] = startingPos.toString();
      transformInfo[3] = endingPos.toString();

      xIncrement = (double) ((endingPos.getX()-startingPos.getX()) / span);
      yIncrement = (double) ((endingPos.getY()-startingPos.getY()) / span);
    }

    @Override
    public void transform (int tick){

      int incrementMultiplier = tick-startingTick;

      double targetX = (xIncrement * incrementMultiplier) + startingPos.getX();
      double targetY = (yIncrement * incrementMultiplier) + startingPos.getY();

      shape.moveTo(new ProviderLocation(targetX, targetY));
    }

    @Override
    public Double[] getStart(){
      return new Double[]{(double) startingPos.getX(),
              (double)startingPos.getY()};
    }

    @Override
    public Double[] getEnd(){
      return new Double[]{(double) endingPos.getX(),
              (double)endingPos.getY()};
    }

  }

