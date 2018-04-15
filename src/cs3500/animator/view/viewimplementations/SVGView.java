package cs3500.animator.view.viewimplementations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.PrintStream;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.model.interfaces.AnimationComponentInterface;
import cs3500.animator.model.interfaces.ShapeInterface;
import cs3500.animator.view.interfaces.ViewInterface;

/**
 * This class represents the svg view of animation.
 */
public class SVGView implements ViewInterface {
  private ModelInsulatorInterface model;
  private FileOutputStream output;
  private String outputString;
  private int ticksPerSecond;
  private boolean isLoopback;

  List<ShapeInterface> shapes;
  //Map of shape names to animation components that use them.
  private Map<String, List<AnimationComponentInterface>> shapeNameToAnimationMap;
  private Map<Integer, List<AnimationComponentInterface>> endToAnimationMap;
  private List<AnimationComponentInterface> animationList;

  /**
   * SVGView generates formatted SVG text.
   *
   * @param model          The model to be used when building.
   * @param outFile        The name for the target output file. If left empty, the default is
   *                       "out.svg".
   * @param ticksPerSecond The number of ticksPerSecond for the animation.
   * @throws IOException Throws IOException if outFile name is not legal.
   */
  public SVGView(ModelInsulatorInterface model, String outFile, int ticksPerSecond)
          throws IOException {

    this.shapes = model.getShapeList();
    this.animationList = model.getAnimationList();
    this.shapeNameToAnimationMap = model.getShapeNameToAnimationMap();
    this.endToAnimationMap = model.getEndToAnimationMap();

    this.isLoopback = false;
    this.ticksPerSecond = ticksPerSecond;
    if (outFile == null) {
      this.outputString = "out.svg";
    } else {
      this.outputString = outFile;
    }

    if (!outFile.equals("out")) {
      output = new FileOutputStream(outFile);
    }
  }

  /**
   * SVGView generates formatted SVG text.
   *
   * @param ticksPerSecond          Ticks represented in one second.
   * @param outFile                 The name for the target output file. If left empty, the
   *                                default is "out.svg".
   * @param shapes                  List contains all the shapes.
   * @param animationList           List contains all the animations.
   * @param shapeNameToAnimationMap Maps that mapping name to animation.
   * @param endToAnimationMap       Map that maps ending time with animation.
   * @throws IOException Throws IOException if outFile name is not legal.
   */
  public SVGView(int ticksPerSecond,
                 String outFile,
                 List<ShapeInterface> shapes,
                 List<AnimationComponentInterface> animationList,
                 Map<String, List<AnimationComponentInterface>> shapeNameToAnimationMap,
                 Map<Integer, List<AnimationComponentInterface>> endToAnimationMap)
          throws IOException {
    this.ticksPerSecond = ticksPerSecond;
    this.shapes = shapes;
    this.animationList = animationList;
    this.shapeNameToAnimationMap = shapeNameToAnimationMap;
    this.endToAnimationMap = endToAnimationMap;

    if (outFile == null) {
      this.outputString = "out.svg";
    } else {
      this.outputString = outFile;
    }

    if (!outFile.equals("out")) {
      output = new FileOutputStream(outFile);
    }
  }

  /**
   * Sets the internal list of ShapeInterfaces with the provided list.
   *
   * @param newShapes Shapes to be used by the animation.
   */
  public void setShapeList(List<ShapeInterface> newShapes) {
    shapes = newShapes;
  }

  @Override
  public void run() {
    if (this.output == null || this.output.equals("")) {
      PrintStream console = System.out;
      System.setOut(console);
      System.out.println(viewText());
    } else {
      try {
        byte[] strToBytes = viewText().getBytes();
        this.output.write(strToBytes);
        this.output.flush();
        System.out.println("File Written Successfully");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean getIsLoopback() {
    return this.isLoopback;
  }

  public void setIsLoopback(boolean isLoopback) {
    this.isLoopback = isLoopback;
  }

  @Override
  public String viewText() {
    String svgText = "";
    svgText += svgCanvas() + "\n";
    svgText += svgShapeText(this.isLoopback);
    svgText += "</svg>";
    return svgText;
  }

  @Override
  public String getViewType() {
    return "SVG View";
  }

  @Override
  public int getSpeed() {
    return ticksPerSecond;
  }

  @Override
  public String getDestination() {
    return outputString;
  }

  /**
   * represent the canvas of an animation.
   *
   * @return the convas of an animation.
   */
  private String svgCanvas() {
    String svgCanvas = "<svg width=\"1000\" height=\"1000\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">";

    return svgCanvas;

  }

  /**
   * change the shapes and Animations into the svg.
   *
   * @return the text represent the svg format of animation.
   */
  private String svgShapeText(boolean isLoopback) {
    //string builder keeps track of string
    StringBuilder builder = new StringBuilder();
    //List of animations for current shape (used for shape section)
    List<AnimationComponentInterface> shapeAnimations;
    //current animation in the loop
    AnimationComponentInterface currentAnimation;
    double animationRunTime = 0;


    Iterator itr;

    //for all shapes
    for (ShapeInterface currentShape : shapes) {
      //print out basic shape information
      builder.append(currentShape.toSvgString());
      builder.append("\n");

      //get all animations for that shape
      if (shapeNameToAnimationMap.containsKey(currentShape.getName())) {
        shapeAnimations = shapeNameToAnimationMap.get(currentShape.getName());

        itr = shapeAnimations.iterator();
        if (isLoopback) {
          builder.append("\n"
                  + "<animate "
                  + "attributeType= \"xml\" "
                  + "begin=\"base.begin+" + 0 + "ms" + "\" "
                  + "dur=" + "\""
                  + ((double) shapeAnimations.get(0).getStartTime()
                  / (double) ticksPerSecond) * 1000f
                  + "ms" + "\" " + "attributeName="
                  + "\"" + "visibility" + "\""
                  + " from="
                  + "\"" + "hidden" + "\"" + " to=" + "\"" + "hidden" + "\""
                  + "/>");
          builder.append("\n"
                  + "<animate "
                  + "attributeType= \"xml\" "
                  + "begin=\"base.begin+"
                  + ((double) shapeAnimations.get(shapeAnimations.size() - 1).getEndTime()
                  / (double) ticksPerSecond) * 1000f + "ms" + "\" "
                  + "dur=" + "\""
                  + "1ms" + "\" " + "attributeName="
                  + "\"" + "visibility" + "\""
                  + " from="
                  + "\"" + "visible" + "\"" + " to=" + "\"" + "hidden" + "\""
                  + "/>\n");
        } else {
          builder.append("\n"
                  + "<animate "
                  + "attributeType= \"xml\" "
                  + "begin=\"" + 0 + "ms" + "\" "
                  + "dur=" + "\""
                  + ((double) shapeAnimations.get(0).getStartTime()
                  / (double) ticksPerSecond) * 1000f
                  + "ms" + "\" " + "attributeName="
                  + "\"" + "visibility" + "\""
                  + " from="
                  + "\"" + "hidden" + "\"" + " to=" + "\"" + "hidden" + "\""
                  + "/>");
          builder.append("\n"
                  + "<animate "
                  + "attributeType= \"xml\" "
                  + "begin=\""
                  + ((double) shapeAnimations.get(shapeAnimations.size() - 1).getEndTime()
                  / (double) ticksPerSecond) * 1000f + "ms" + "\" "
                  + "dur=" + "\""
                  + "1ms" + "\" " + "attributeName="
                  + "\"" + "visibility" + "\""
                  + " from="
                  + "\"" + "visible" + "\"" + " to=" + "\"" + "hidden" + "\""
                  + "/>\n");
        }

        while (itr.hasNext()) {
          currentAnimation = (AnimationComponentInterface) itr.next();

          animationRunTime = Math.max(animationRunTime, currentAnimation.getEndTime());
          if (currentAnimation.getAnimationType().equals("Visibility Change")) {
            continue;
          }

          builder.append(currentAnimation.getSvg(isLoopback, ticksPerSecond));
          builder.append("\n");
        }
      }

      builder.append("</" + currentShape.getSvgShape() + ">");
      builder.append("\n");
    }

    System.out.println("animationRunTime: " + animationRunTime);

    if (isLoopback) {
      builder.insert(0, "<rect><animate id=\"base\" begin=\"0;base.end\" dur=\"" +
              animationRunTime / (double) ticksPerSecond * 1000.0 +
              "ms\" attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n</rect>\n");
    }

    itr = animationList.iterator();

    while (itr.hasNext()) {
      currentAnimation = (AnimationComponentInterface) itr.next();
      if (!currentAnimation.getAnimationType().equals("Visibility Change")) {
        currentAnimation.executeFull();
      }
    }

    return builder.toString();
  }

}
