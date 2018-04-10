package cs3500.animator.controller.controllerimplementations;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.controller.interfaces.TweenModelBuilder;
import cs3500.animator.view.interfaces.ViewFactoryInterface;

import cs3500.animator.model.AnimationModelText;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.view.ViewTypes;

/**
 * Implementation of ControllerInterface. Nothing really fancy or worthy of explanation.
 */
public class ControllerBasic extends ControllerAbstract {
  /**
   * Basic constructor for the controller. Just calls the ControllerAbstract constructor.
   *
   * @param model The model that will be used during production.
   * @param vFac  The view Factory that the parseInput() command will use to create
   *              the desired view.
   */
  public ControllerBasic(AnimationModelInterface model, ViewFactoryInterface vFac) {
    super(model, vFac);
  }

  @Override
  public void run() throws IllegalStateException {
    if (view == null) {
      throw new IllegalStateException("View has not been created. MUST run parseInput() method "
              + "first.");
    }

    try {
      view.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String parseInput(String[] args) {
    String inFile = null;
    ViewTypes type = null;
    String outFile = "out";
    int ticksPerSecond = 1;

    util.AnimationFileReader reader = new util.AnimationFileReader();

    TweenModelBuilder<AnimationModelText> builder = new AnimationModelText.Builder();
    //make an array list to check for arguments
    ArrayList<String> argsArray = new ArrayList<>(Arrays.asList(args));

    //must have an in-file designation
    if (!argsArray.contains("-if")) {
      return ("Must have an in-file");
    }

    //must have a view-type designation
    if (!argsArray.contains("-iv")) {
      return ("Must have a view type");
    }

    //Works through the argument string and assigns values accordingly.
    for (int i = 0; i < args.length; i++) {
      String text = args[i];

      switch (text) {
        //in file can be anything
        case "-if":
          inFile = args[i + 1];
          i += 1;
          break;

        //view type can only be on of a few values.
        case "-iv":
          String temp = args[i + 1];
          try {
            type = viewTypeCheck(temp);
          } catch (IllegalArgumentException e) {
            return e.getMessage();
          }
          i += 1;
          break;

        //out file can be anything
        case "-o":
          outFile = args[i + 1];
          i += 1;
          break;

        //speed must be an integer >0;
        case "-speed":
          try {
            ticksPerSecond = Integer.parseInt(args[i + 1]);
          } catch (NumberFormatException e) {
            return ("Speed MUST be an integer");
          }
          if (ticksPerSecond <= 0) {
            return ("Speed MUST be GREATER than 0");
          }
          i += 1;
          break;

        default:
          return ("Unexpected input, please ensure that all arguments are "
                  + "preceded with \"-if\", \"-iv\", \"-o\", or \"-speed\"");
      }
    }

    //read information from inFile and use builder to fill in the model.
    try {
      model = reader.readFile(inFile, builder);
    } catch (Exception e) {
      return e.getMessage();
    }
    //Create the view based on  the given parameters.
    try {
      view = vFac.create(type, new ModelInsulator(model), outFile, ticksPerSecond);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "No Error";
  }

  //implementation of method inherited from ControllerAbstract. Helps to check given strings
  //against actual view types.
  protected ViewTypes viewTypeCheck(String temp) {
    ViewTypes type = ViewTypes.SVG;
    switch (temp) {
      case "text":
        type = ViewTypes.TEXT;
        break;
      case "visual":
        type = ViewTypes.VISUAL;
        break;
      case "svg":
        type = ViewTypes.SVG;
        break;
      default:
        throw new IllegalArgumentException("The view type MUST be either \"text\", \"visual\", or "
                + "\"svg\". These are case sensitive.");
    }
    return type;
  }

}
