package cs3500.animator.converters.transforms;

import cs3500.animator.converters.shapes.ProviderAnimShape;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.provider.IAnimShape;
import cs3500.animator.provider.ITransformation;

public abstract class ProviderTransformAbstract implements ITransformation {

  protected IAnimShape shape;
  protected String[] transformInfo = new String[5];
  protected int span;


  private String shapeName;
  protected int startingTick;
  protected int endingTick;

  public ProviderTransformAbstract(AnimationComponentInterface amCom){
    this.shape = new ProviderAnimShape(amCom.getTarget());
    this.shapeName = amCom.getTargetName();
    this.startingTick = amCom.getStartTime();
    this.endingTick = amCom.getEndTime();
    this.span = endingTick - startingTick;

    this.transformInfo[0] = this.shapeName;

    //transform[1],[2], and [3] will be filled in by individual implementation
    this.transformInfo[4] = Integer.toString(startingTick);
    this.transformInfo[5] = Integer.toString(endingTick);
  }

  @Override
  public ITransformation makeCopy(){
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public boolean isActive(int tick){
    if (tick >= startingTick){
      if (tick <= endingTick){
        return true;
      }
    }
    return false;
  }

  @Override
  public String getShapeName(){
    return shapeName;
  }

  @Override
  public void setShape(IAnimShape shape){
    this.shape = shape;
  }

  @Override
  public int getStartTick(){
    return startingTick;
  }

  @Override
  public int getEndTick(){
    return endingTick;
  }

  @Override
  public boolean noConflict(ITransformation other){
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public String[] buildStrings(){
    throw new UnsupportedOperationException("Not needed for HW 8");
  }

  @Override
  public String[] getInfo(){
    return transformInfo;
  }

}
