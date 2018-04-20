package cs3500.animator.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cs3500.animator.controller.interfaces.TweenModelBuilder;
import cs3500.animator.model.concreteclasses.shapes.Rectangle;
import cs3500.animator.model.concreteclasses.utilityclasses.ColorClass;
import cs3500.animator.model.concreteclasses.utilityclasses.Duration;
import cs3500.animator.model.concreteclasses.utilityclasses.Position2D;
import cs3500.animator.model.factories.AnimationComponentFactoryBasic;
import cs3500.animator.model.factories.ShapeFactoryBasic;
import cs3500.animator.model.interfaces.AnimationComponentFactoryInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.model.interfaces.ColorClassInterface;
import cs3500.animator.model.interfaces.DurationInterface;
import cs3500.animator.model.interfaces.Position2DInterface;
import cs3500.animator.model.interfaces.ShapeFactoryInterface;
import cs3500.animator.model.interfaces.ShapeInterface;

/**
 * The implementation of the AnimationModelInterface. Uses maps and lists to keep track of shapes,
 * animations, times and associations between the three.
 * INVARIANTS:
 * 1.) AnimationComponents cannot affect the same shape at the same time in the same way.
 * 2.) AnimationComponents must reference a shape in the model.
 * 3.) AnimationList is sorted by ascending AnimationComponent start times (earliest = index 0)
 * 4.) All shapes are stored in shapeList, initial states of those shapes are stored in
 * shapeNameToInitialShapeObjectMap, working states are stored in shapeNameToWorkingShapeObjectMap.
 * 5.) All Animations are stored in animationList and mapped to shapes and times in
 * shapeNameToAnimationMap, startToAnimationMap, endToAnimationMap.
 */
public class AnimationModelText implements AnimationModelInterface {

  //List of all animation components present in the model
  private List<AnimationComponentInterface> animationList = new ArrayList<>();
  //List of all shapes present in the model. Objects stored in this list are identical to
  //those stored in shapeNameToWorkingShapeObjectMap. Modifying ShapeInterfaces here will
  //affect that map as well as animations that rely on that shape.
  private List<ShapeInterface> shapeList = new ArrayList<>();
  //Map of shape names to a copy of the  initial state of the shape interface (as it was created).
  //Will not be used for anything but resetting shapes to their original state.
  private Map<String, ShapeInterface> shapeNameToInitialShapeObjectMap = new HashMap<>();
  //Map of shape names to the current state of the object (at any point in execution). Animation
  //Components directly reference and modify the ShapeInterface objects stored in this map.
  private Map<String, ShapeInterface> shapeNameToWorkingShapeObjectMap = new HashMap<>();
  //Map of shape names to animation components that use them.
  private Map<String, List<AnimationComponentInterface>> shapeNameToAnimationMap = new HashMap<>();
  //Map of times to animation components that begin at that time.
  private Map<Integer, List<AnimationComponentInterface>> startToAnimationMap = new HashMap<>();
  //Map of times to animation components that end at that time.
  private Map<Integer, List<AnimationComponentInterface>> endToAnimationMap = new HashMap<>();

  private ShapeFactoryInterface shapeFactory;
  private AnimationComponentFactoryInterface animationFactory;

  /**
   * Basic AnimationModelText constructor. Sets the shape and animation factories which are used
   * in the creation and editing of shapes and animations.
   *
   * @param sFac The ShapeFactoryInterface that will be used.
   * @param aFac The AnimationFactory that will be used.
   */
  public AnimationModelText(ShapeFactoryInterface sFac, AnimationComponentFactoryInterface aFac) {
    shapeFactory = sFac;
    animationFactory = aFac;
  }

  @Override
  public void addAnimation(String shapeName,
                           String animationType,
                           DurationInterface animationDuration,
                           Object... parameters)
          throws IllegalArgumentException {
    validateComponentShapeExists(shapeName);
    ShapeInterface targetShape = shapeNameToWorkingShapeObjectMap.get(shapeName);

    AnimationComponentInterface newAnimation = animationFactory.create(targetShape, animationType,
            animationDuration, parameters);

    addAnimationComponent(newAnimation);
    sortAmComs(animationList);
  }

  @Override
  public void editAnimation(int listIndex, String shapeName, String animationType,
                            DurationInterface animationDuration, Object... parameters)
          throws IllegalArgumentException {
    validateListIndex(listIndex);
    validateComponentShapeExists(shapeName);
    ShapeInterface targetShape = shapeNameToWorkingShapeObjectMap.get(shapeName);

    AnimationComponentInterface editAnimation = animationFactory.create(targetShape, animationType,
            animationDuration, parameters);

    AnimationComponentInterface temp = animationList.get(listIndex - 1);

    removeAnimationComponent(temp);
    try {
      validateComponentOverlap(editAnimation);
    } catch (IllegalArgumentException e) {
      removeAnimationComponent(editAnimation);
      addAnimationComponent(temp);
      throw new IllegalArgumentException("This edit would cause conflict with other animations");
    }

    addAnimationComponent(editAnimation);
    sortAmComs(animationList);
  }

  @Override
  public void addShape(String shapeName, String shapeType, ColorClassInterface color,
                       Position2DInterface pos, float orient, Object... parameters)
          throws IllegalArgumentException {

    if (shapeNameToWorkingShapeObjectMap.containsKey(shapeName)) {
      throw new IllegalArgumentException("There is already a shape with this name");
    }

    ShapeInterface newShape = shapeFactory.create(shapeName, shapeType, color, pos,
            orient, parameters);

    shapeNameToInitialShapeObjectMap.put(shapeName, newShape.copy());
    shapeNameToWorkingShapeObjectMap.put(shapeName, newShape);
    shapeList.add(newShape);
  }

  @Override
  public void editShape(String shapeName, String parameterName, Object parameterValue)
          throws IllegalArgumentException {
    //not required for HW 5
  }

  @Override
  public void removeShape(String shapeName) throws IllegalArgumentException {
    if (!shapeNameToInitialShapeObjectMap.containsKey(shapeName)) {
      throw new IllegalArgumentException("There is no shape with name: \"" + shapeName + "\"");
    }

    ShapeInterface shapeObject = shapeNameToInitialShapeObjectMap.get(shapeName);
    shapeList.remove(shapeObject);
    shapeNameToInitialShapeObjectMap.remove(shapeName);
    shapeNameToWorkingShapeObjectMap.remove(shapeName);

    List<AnimationComponentInterface> removeList = new ArrayList<>(shapeNameToAnimationMap.get(
            shapeName));
    Iterator listItr = removeList.iterator();
    AnimationComponentInterface temp;

    while (listItr.hasNext()) {
      temp = (AnimationComponentInterface) listItr.next();
      removeAnimationComponent(temp);
    }
  }

  @Override
  public void removeAnimation(int listIndex) throws IllegalArgumentException {
    validateListIndex(listIndex);

    AnimationComponentInterface removeAmCom = animationList.get(listIndex - 1);
    removeAnimationComponent(removeAmCom);
  }

  @Override
  public List<AnimationComponentInterface> getAnimationList() {
    List<AnimationComponentInterface> newList = new ArrayList<>();
    for (AnimationComponentInterface a : animationList) {
      newList.add(a.copy());
    }
    return newList;
  }

  @Override
  public List<ShapeInterface> getShapeList() {
    List<ShapeInterface> newList = new ArrayList<>();
    for (ShapeInterface s : shapeList) {
      newList.add(s.copy());
    }
    return newList;
  }

  @Override
  public Map<Integer, List<AnimationComponentInterface>> getStartToAnimationMap() {
    //new hashmap to return
    Map<Integer, List<AnimationComponentInterface>> newMap = new HashMap<>();
    //check each entry in the map
    for (Map.Entry<Integer, List<AnimationComponentInterface>> entry :
            startToAnimationMap.entrySet()) {
      //create a new array per start time with copies of all components
      List<AnimationComponentInterface> newList = new ArrayList<>();
      for (AnimationComponentInterface a : entry.getValue()) {
        newList.add(a.copy());
      }
      //same key, new copied list.
      newMap.put(entry.getKey(), newList);
    }
    return newMap;
  }

  @Override
  public Map<Integer, List<AnimationComponentInterface>> getEndToAnimationMap() {
    //new hashmap to return
    Map<Integer, List<AnimationComponentInterface>> newMap = new HashMap<>();
    //check each entry in the map
    for (Map.Entry<Integer, List<AnimationComponentInterface>> entry :
            endToAnimationMap.entrySet()) {
      //create a new array per end time with copies of all components
      List<AnimationComponentInterface> newList = new ArrayList<>();
      for (AnimationComponentInterface a : entry.getValue()) {
        newList.add(a.copy());
      }
      //same key, new copied list.
      newMap.put(entry.getKey(), newList);
    }
    return newMap;
  }

  @Override
  public Map<String, List<AnimationComponentInterface>> getShapeNameToAnimationMap() {
    //new hashmap to return
    Map<String, List<AnimationComponentInterface>> newMap = new HashMap<>();
    //check each entry in the map
    for (Map.Entry<String, List<AnimationComponentInterface>> entry :
            shapeNameToAnimationMap.entrySet()) {
      //create a new array per end time with copies of all components
      List<AnimationComponentInterface> newList = new ArrayList<>();
      for (AnimationComponentInterface a : entry.getValue()) {
        newList.add(a.copy());
      }
      //same key, new copied list.
      newMap.put(entry.getKey(), newList);
    }
    return newMap;
  }

  @Override
  public void clearAnimationList() {
    animationList.clear();
    shapeNameToAnimationMap.clear();
    startToAnimationMap.clear();
    endToAnimationMap.clear();
  }

  @Override
  public void clearShapeList() {
    shapeList.clear();
    shapeNameToInitialShapeObjectMap.clear();
    shapeNameToWorkingShapeObjectMap.clear();

    clearAnimationList();
  }

  @Override
  public String getOverview() {
    //string builder keeps track of string
    StringBuilder builder = new StringBuilder();
    //List of animations for current shape (used for shape section)
    List<AnimationComponentInterface> shapeAnimations;
    //current animation in the loop
    AnimationComponentInterface currentAnimation;

    Iterator itr;

    builder.append("shapes:\n");

    //for all shapes
    for (ShapeInterface currentShape : shapeList) {
      //print out basic shape information
      builder.append(currentShape.toString());
      //get all animations for that shape
      if (shapeNameToAnimationMap.containsKey(currentShape.getName())) {
        shapeAnimations = shapeNameToAnimationMap.get(currentShape.getName());
        itr = shapeAnimations.iterator();
        while (itr.hasNext()) {
          currentAnimation = (AnimationComponentInterface) itr.next();

          if (currentAnimation.getAnimationType().equals("Visibility Change")) {
            builder.append(currentAnimation.toString());
          }
        }
      }
      builder.append("\n");
    }
    itr = animationList.iterator();

    while (itr.hasNext()) {
      currentAnimation = (AnimationComponentInterface) itr.next();
      if (!currentAnimation.getAnimationType().equals("Visibility Change")) {
        builder.append(currentAnimation.toString());
        currentAnimation.executeFull();
      }
    }
    restartAnimation();

    return builder.toString();
  }

  @Override
  public String getOverviewTick(int ticksPerSecond) {
    //string builder keeps track of string
    StringBuilder builder = new StringBuilder();
    //List of animations for current shape (used for shape section)
    List<AnimationComponentInterface> shapeAnimations;
    //current animation in the loop
    AnimationComponentInterface currentAnimation;

    Iterator itr;

    builder.append("shapes:\n");

    //for all shapes
    for (ShapeInterface currentShape : shapeList) {
      //print out basic shape information
      builder.append(currentShape.toString());
      //get all animations for that shape
      if (shapeNameToAnimationMap.containsKey(currentShape.getName())) {
        shapeAnimations = shapeNameToAnimationMap.get(currentShape.getName());
        itr = shapeAnimations.iterator();
        while (itr.hasNext()) {
          currentAnimation = (AnimationComponentInterface) itr.next();

          if (currentAnimation.getAnimationType().equals("Visibility Change")) {
            builder.append(currentAnimation.toStringTick(ticksPerSecond));
          }
        }
      }
      builder.append("\n");
    }
    itr = animationList.iterator();

    while (itr.hasNext()) {
      currentAnimation = (AnimationComponentInterface) itr.next();
      if (!currentAnimation.getAnimationType().equals("Visibility Change")) {
        builder.append(currentAnimation.toStringTick(ticksPerSecond));
        currentAnimation.executeFull();
      }
    }
    restartAnimation();

    return builder.toString();
  }

  @Override
  public String getCurrentState() {
    return "This function has not yet been implemented";
    //Not required for HW 5
  }

  @Override
  public void runFull() {
    for (AnimationComponentInterface anim : animationList) {
      anim.executeFull();
    }
  }

  @Override
  public void runFrom(int startTime, int endTime) {
    //Not required for HW 5
  }


  @Override
  public void restartAnimation() {
    Iterator itr = shapeList.iterator();

    ShapeInterface test;
    while (itr.hasNext()) {
      test = (ShapeInterface) itr.next();
      test.resetShape();
    }
  }

  /**
   * Ensures that the given shape does exist in the model.
   *
   * @param shapeName the name of the shape to be checked for.
   * @throws IllegalArgumentException If the shape does not exist in the model.
   */
  private void validateComponentShapeExists(String shapeName)
          throws IllegalArgumentException {
    if (!shapeNameToWorkingShapeObjectMap.containsKey(shapeName)) {
      throw new IllegalArgumentException("There is no shape with name: \""
              + shapeName + "\"");
    }
  }

  /**
   * Ensures that the given Animation component will not result in a conflict with another,
   * pre-existing animation.
   *
   * @param checkAmCom The AnimationComponent to check.
   * @throws IllegalArgumentException If the given AnimationComponent tries to modify the same
   *                                  shape, in the same way,at the same time as another
   *                                  AnimationComponent.
   */
  private void validateComponentOverlap(AnimationComponentInterface checkAmCom)
          throws IllegalArgumentException {
    //will be used to grab a list of all animations that are active when the checking animation
    //is active
    List<AnimationComponentInterface> overlappingAnimations = new ArrayList<>();

    //for
    for (int i = checkAmCom.getStartTime(); i <= checkAmCom.getEndTime(); i++) {
      if (startToAnimationMap.containsKey(i)) {
        List<AnimationComponentInterface> tempListStart = startToAnimationMap.get(i);
        overlappingAnimations.addAll(tempListStart);
      }
      if (endToAnimationMap.containsKey(i)) {
        List<AnimationComponentInterface> tempListEnd = endToAnimationMap.get(i);
        overlappingAnimations.addAll(tempListEnd);
      }
    }

    Iterator overlapItr = overlappingAnimations.iterator();
    //sortAmComs(overlappingAnimations);

    while (overlapItr.hasNext()) {
      AnimationComponentInterface currentOverlapAmCom =
              (AnimationComponentInterface) overlapItr.next();

      if (checkAmCom.getTargetName().equals(currentOverlapAmCom.getTargetName())) {
        if (checkAmCom.getAnimationType().equals(currentOverlapAmCom.getAnimationType())) {
          throw new IllegalArgumentException("At least two animation components attempt to "
                  + "perform the same act on the same object during the same period");
        }
      }
    }
  }

  /**
   * Ensures that the listIndex is valid for the animationList.
   *
   * @param listIndex the index to be checked.
   * @throws IllegalArgumentException if the listIndex is either negative or exceeds the size of
   *                                  animationList.
   */
  private void validateListIndex(int listIndex) throws IllegalArgumentException {
    if (listIndex > animationList.size()) {
      throw new IllegalArgumentException("Cannot have an index that exceeds number of animations");
    }
    if (listIndex < 0) {
      throw new IllegalArgumentException("Cannot have a negative index");
    }
  }

  /**
   * Allows for the addition of an animation as a whole AnimationComponent not just its
   * constituent parts. Updates all relevant maps and lists.
   *
   * @param addition the animationComponent to add.
   * @throws IllegalArgumentException If adding the component would cause overlap or if the
   *                                  component's target shape does not exist.
   */
  private void addAnimationComponent(AnimationComponentInterface addition)
          throws IllegalArgumentException {

    validateComponentOverlap(addition);
    validateComponentShapeExists(addition.getTargetName());

    animationList.add(addition);
    sortAmComs(animationList);

    if (startToAnimationMap.containsKey(addition.getStartTime())) {
      List<AnimationComponentInterface> amComsWithSameStartTime = startToAnimationMap.get(
              addition.getStartTime());
      amComsWithSameStartTime.add(addition);
      sortAmComs(amComsWithSameStartTime);

      startToAnimationMap.replace(addition.getStartTime(), amComsWithSameStartTime);
    } else {
      List<AnimationComponentInterface> newStartList = new ArrayList<>();
      newStartList.add(addition);
      startToAnimationMap.put(addition.getStartTime(), newStartList);
    }

    if (endToAnimationMap.containsKey(addition.getEndTime())) {
      List<AnimationComponentInterface> amComsWithSameEndTime = endToAnimationMap.get(
              addition.getEndTime());
      amComsWithSameEndTime.add(addition);
      sortAmComs(amComsWithSameEndTime);

      endToAnimationMap.replace(addition.getEndTime(), amComsWithSameEndTime);
    } else {
      List<AnimationComponentInterface> newEndList = new ArrayList<>();
      newEndList.add(addition);
      endToAnimationMap.put(addition.getEndTime(), newEndList);
    }

    if (shapeNameToAnimationMap.containsKey(addition.getTargetName())) {
      List<AnimationComponentInterface> amComsWithSameShape = shapeNameToAnimationMap.get(
              addition.getTargetName());

      sortAmComs(animationList);
      amComsWithSameShape.add(addition);
      sortAmComs(amComsWithSameShape);

      shapeNameToAnimationMap.replace(addition.getTargetName(), amComsWithSameShape);
    } else {
      List<AnimationComponentInterface> newShapeList = new ArrayList<>();
      newShapeList.add(addition);
      shapeNameToAnimationMap.put(addition.getTargetName(), newShapeList);
    }
  }

  /**
   * Allows for the removal of an animationComponent from all relevant maps and lists.
   *
   * @param removeAmCom The animationComponent to remove.
   */
  private void removeAnimationComponent(AnimationComponentInterface removeAmCom) {
    List<AnimationComponentInterface> nameList = shapeNameToAnimationMap.get(
            removeAmCom.getTargetName());
    List<AnimationComponentInterface> startList = startToAnimationMap.get(
            removeAmCom.getStartTime());
    List<AnimationComponentInterface> endList = endToAnimationMap.get(removeAmCom.getEndTime());

    nameList.remove(removeAmCom);
    startList.remove(removeAmCom);
    endList.remove(removeAmCom);

    shapeNameToAnimationMap.put(removeAmCom.getTargetName(), nameList);
    startToAnimationMap.put(removeAmCom.getStartTime(), startList);
    endToAnimationMap.put(removeAmCom.getEndTime(), endList);

    animationList.remove(removeAmCom);
  }

  private void sortAmComs(List<AnimationComponentInterface> listOfAnimations) {
    Collections.sort(listOfAnimations, new Comparator<AnimationComponentInterface>() {
      @Override
      public int compare(AnimationComponentInterface o1, AnimationComponentInterface o2) {
        return o1.compareTo(o2);
      }
    });
  }


  /**
   * Builder class for model. Provides compatibility between the TweenBuilderModel and
   * AnimationFileReader classes.
   */
  public static final class Builder implements TweenModelBuilder<AnimationModelText> {
    AnimationModelText model = new AnimationModelText(new ShapeFactoryBasic(),
            new AnimationComponentFactoryBasic());

    @Override
    public TweenModelBuilder<AnimationModelText> addOval(String name,
                                                         float cx, float cy,
                                                         float xRadius, float yRadius,
                                                         float red, float green, float blue,
                                                         int startOfLife, int endOfLife) {
      Position2DInterface pos = new Position2D(cx, cy);
      ColorClassInterface color = new ColorClass(red, green, blue);
      DurationInterface durStart = new Duration(startOfLife);
      DurationInterface durEnd = new Duration(endOfLife);

      this.model.addShape(name, "Oval", color, pos, 0f, xRadius, yRadius);
      this.model.addAnimation(name, "Visibility Change", durStart, true);
      this.model.addAnimation(name, "Visibility Change", durEnd, false);

      return this;
    }

    @Override
    public TweenModelBuilder<AnimationModelText> addRectangle(String name,
                                                              float lx, float ly,
                                                              float width, float height,
                                                              float red, float green, float blue,
                                                              int startOfLife, int endOfLife) {
      Position2DInterface pos = new Position2D(lx, ly);
      ColorClassInterface color = new ColorClass(red, green, blue);
      DurationInterface durStart = new Duration(startOfLife);
      DurationInterface durEnd = new Duration(endOfLife);

      this.model.addShape(name, "Rectangle", color, pos, 0f, width, height);
      this.model.addAnimation(name, "Visibility Change", durStart, true);
      this.model.addAnimation(name, "Visibility Change", durEnd, false);

      return this;
    }

    @Override
    public TweenModelBuilder<AnimationModelText> addMove(
            String name,
            float moveFromX, float moveFromY, float moveToX, float moveToY,
            int startTime, int endTime) {
      Position2DInterface initialPos = new Position2D(moveFromX, moveFromY);
      Position2DInterface finalPos = new Position2D(moveToX, moveToY);
      DurationInterface dur = new Duration(startTime, endTime);

      this.model.addAnimation(name, "Position Change", dur, initialPos, finalPos);
      return this;

    }

    @Override
    public TweenModelBuilder<AnimationModelText> addColorChange(
            String name,
            float oldR, float oldG, float oldB, float newR, float newG, float newB,
            int startTime, int endTime) {
      ColorClassInterface initialColor = new ColorClass(oldR, oldG, oldB);
      ColorClassInterface finalColor = new ColorClass(newR, newG, newB);
      DurationInterface dur = new Duration(startTime, endTime);

      this.model.addAnimation(name, "Color Change", dur, initialColor, finalColor);
      return this;
    }

    @Override
    public TweenModelBuilder<AnimationModelText> addScaleToChange(String name, float fromSx, float
            fromSy, float toSx, float toSy, int startTime, int endTime) {
      ShapeInterface targetShape = new Rectangle();

      DurationInterface dur = new Duration(startTime, endTime);

      List<ShapeInterface> listOfShapes = this.model.getShapeList();
      for (ShapeInterface shape : listOfShapes) {
        if (shape.getName().equals(name)) {
          targetShape = shape;
          break;
        }
      }

      if (targetShape.getShapeType().equals("Rectangle")) {
        model.addAnimation(name, "Scale Change WH", dur, fromSx, fromSy, toSx, toSy);
      }
      if (targetShape.getShapeType().equals("Oval")) {
        model.addAnimation(name, "Scale Change RR", dur, fromSx, fromSy, toSx, toSy);
      }

      return this;
    }

    @Override
    public TweenModelBuilder<AnimationModelText> addRotationChange(String name, float fromOrient,
                                                                   float toOrient, int startTime,
                                                                   int endTime){
      DurationInterface dur = new Duration(startTime, endTime);

      this.model.addAnimation(name, "Rotation Change", dur, fromOrient, toOrient);
      return this;
    }

    @Override
    public AnimationModelText build() {
      return this.model;
    }
  }

}
