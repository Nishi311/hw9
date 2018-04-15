package cs3500.animator.view.viewimplementations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import cs3500.animator.controller.interfaces.ModelInsulatorInterface;
import cs3500.animator.view.interfaces.ViewInterface;

/**
 * This class represents the text view of animation.
 */

public class TextView implements ViewInterface {
  private ModelInsulatorInterface model;
  private FileOutputStream output;
  private String outputString;

  private int ticksPerSecond;

  /**
   * TextView generates formatted textual view.
   *
   * @param model          The model to be used when building.
   * @param outFile        The name for the target output file. If left empty, the default is
   *                       "out.svg".
   * @param ticksPerSecond The number of ticksPerSecond for the animation.
   * @throws IOException Throws IOException if outFile name is not legal.
   */
  public TextView(ModelInsulatorInterface model, String outFile, int ticksPerSecond)
          throws IOException {
    this.model = model;
    this.ticksPerSecond = ticksPerSecond;
    this.outputString = outFile;
    if (!outFile.equals("out")) {
      output = new FileOutputStream(outFile);
    }
  }

  @Override
  public void run() {
    if (this.output == null || this.output.equals("")) {
      // Write to System.out
      System.out.println(viewText());
    } else {
      try (PrintWriter o = new PrintWriter(this.output + ".txt");) {
        o.print(viewText());
      } catch (FileNotFoundException e) {
        e.getMessage();
      }
    }
  }

  @Override
  public String viewText() {
    return model.getOverviewTick(ticksPerSecond);
  }

  @Override
  public String getViewType() {
    return "Text View";
  }

  @Override
  public int getSpeed() {
    return ticksPerSecond;
  }

  @Override
  public String getDestination() {
    return outputString;
  }

}