package cs3500.animator.controller.controllerimplementations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JCheckBox;

import cs3500.animator.controller.ModelInsulator;
import cs3500.animator.controller.interfaces.TweenModelBuilder;
import cs3500.animator.provider.view.IViewable;
import cs3500.animator.view.interfaces.ViewFactoryInterface;
import cs3500.animator.model.AnimationModelText;
import cs3500.animator.model.interfaces.AnimationModelInterface;
import cs3500.animator.view.interfaces.HybridViewInterface;
import cs3500.animator.view.ViewTypes;

/**
 * The controller that can deal with the hybrid view type. Because the HybridViewInterface has more
 * functionality than the standard view, the new parameter hybridView has been added in. The run
 * command will determine what kind of view it has (either hybrid or not) and execute the run()
 * method on the appropriate view. The class itself contains methods for dealing with
 * ActionListeners, ItemListeners and KeyListeners
 */
public class ControllerWithHybrid extends ControllerAbstract implements KeyListener, ActionListener,
        ItemListener {

  protected HybridViewInterface hybridView;
  protected IViewable providerView;


  protected ViewTypes type = null;
  protected String outFile = "out";
  int ticksPerSecond = 1;

  /**
   * Basic constructor for the controller. Just calls the ControllerAbstract constructor.
   *
   * @param model The model that will be filled out with the parseInput() command native to
   *              each implementation of ControllerInterface.
   * @param vFac  The viewFactory that each parseInput() method will use to build the internal view.
   */
  public ControllerWithHybrid(AnimationModelInterface model, ViewFactoryInterface vFac) {
    super(model, vFac);
  }

  @Override
  public void run() throws IllegalStateException {
    if (view == null) {
      throw new IllegalStateException("View has not been created. MUST run parseInput() method "
              + "first.");
    }
    //if the view is a hybrid, run the hybrid version of run, otherwise run the standard version.
    if (view.getViewType().equals("Hybrid View")) {
      hybridView.run();
    } else {
      view.run();
    }
  }

  @Override
  public void keyTyped(KeyEvent k) {
    checkIfHybridExists();

    System.out.println("Key pressed: " + k.getKeyCode());

  }

  @Override
  public void keyPressed(KeyEvent k) {
    checkIfHybridExists();

    System.out.println("Key pressed: " + k.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent k) {
    checkIfHybridExists();
    System.out.println("Key pressed: " + k.getKeyCode());
  }

  @Override
  public String parseInput(String[] args) {
    String inFile = null;


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
    try {
      createView();
    } catch (Exception e) {
      return e.getMessage();
    }
    //Create the view based on  the given parameters.
    return "No Error";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    checkIfHybridExists();
    switch (e.getActionCommand()) {
      case "Play":
        hybridView.resume();
        hybridView.resetFocus();
        break;
      case "Pause":
        hybridView.pause();
        hybridView.resetFocus();
        break;
      case "Restart":
        hybridView.restart();
        hybridView.resetFocus();
        break;
      case "Speed Up":
        hybridView.speedUp();
        hybridView.resetFocus();
        break;
      case "Speed Down":
        hybridView.speedDown();
        hybridView.resetFocus();
        break;
      case "Export":
        hybridView.exportSVG();
        hybridView.resetFocus();
        break;
      default:
        throw new IllegalArgumentException("Button option not valid");
    }
  }

  @Override
  public void itemStateChanged(ItemEvent i) {
    checkIfHybridExists();
    String which = ((JCheckBox) i.getItemSelectable()).getActionCommand();

    if (which.equals("Loop Box")) {
      if (i.getStateChange() == ItemEvent.SELECTED) {
        hybridView.setLooping(true);
      } else {
        hybridView.setLooping(false);
      }
    } else {
      hybridView.selectOrUnseletShapes(which);
    }
  }

  @Override
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
      case "interactive":
        type = ViewTypes.HYBRID;
        break;
      default:
        throw new IllegalArgumentException("The view type MUST be either \"text\", \"visual\", "
                + "\"svg\" or \"interactive\". These are case sensitive.");
    }
    return type;
  }

  //simple check to see if the hybrid view has been created.
  private void checkIfHybridExists() {
    if (hybridView == null) {
      throw new IllegalStateException("Hybrid view not present");
    }
  }


  private void createView() throws IllegalArgumentException {
    try {
      view = vFac.create(type, new ModelInsulator(model), outFile, ticksPerSecond);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    //if the view is a hybrid, create the hybridView and also assign proper listeners.
    if (view.getViewType().equals("Hybrid View")) {
      hybridView = (HybridViewInterface) view;
      hybridView.setListeners(this, this, this);
    }

  }

}
