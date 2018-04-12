package cs3500.animator.provider.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cs3500.animator.animshape.IAnimShape;
import cs3500.animator.transformations.ITransformation;

/**
 * This class represents an SVG View object, which displays the shapes visibly in most internet
 * browsers, allowing the viewer to see when shapes exist and how they change.
 */
public class SVGView extends APrintableView implements IPrintableView {

  private boolean looping;
  private int endTick;

  /**
   * Build an SVG View object, which describes the shapes and transformations as text, allowing an
   * internet browser to display shapes as they change.
   *
   * @param output The appendable object to put the results in
   */
  public SVGView(int speed, Appendable output, boolean looping, int endTick) {
    super(speed, output);
    this.looping = looping;
    this.endTick = endTick;
  }

  /**
   * Print or export the view to a file.
   *
   * @throws IOException if the file cannot be printed to.
   */
  @Override
  public void printView() throws IOException {
    if (speed == 0) {
      throw new IllegalStateException("Speed cannot be 0.");
    }
    if (shapes == null) {
      throw new IllegalStateException("The list of shapes cannot be null.");
    }
    if (transformations == null) {
      throw new IllegalStateException("The list of transformations cannot be null.");
    }

    String begin;
    if (looping){
      begin = "base.begin+";
    } else {
      begin = "";
    }

    double milli = 1000d / speed;

    output.append("<svg width=\"800\" height=\"800\" version=\"1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n");

      output.append("<rect>\n<animate id=\"base\" begin=\"0;base.end\" dur=\"" +
              endTick * milli + "ms\" attributeName=\"visibility\" " +
              "from=\"hide\" to=\"hide\"/>\n" + "</rect>\n");

      for (IAnimShape shape : shapes) {
        if (shape.getShapeType().equals("rectangle")) {
          output.append(String.format("<rect id=\"%s\" x=\"%f\" y=\"%f\" width=\"%f\" " +
                          "height=\"%f\" " + "fill=\"rgb(%f%%,%f%%,%f%%)\" " +
                          "opacity=\"0\" >\n", shape.getName(), shape.getLocation().getX(),
                  shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
                  shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
                  shape.getColor().getBlue() * 100));
          if (shape.getVisible()) {
            output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\"" +
                            " dur=\"%fms\" attributeName=\"opacity\" from=\"%d\" to=\"%d\" " +
                            "fill=\"freeze\"/>\n", begin, (double) shape.getAppears() * milli,
                    1.0, 0, 1));
          }
          for (ITransformation transformation : transformations) {
            if (shape.getName().equals(transformation.getShapeName())) {
              if (transformation.getInfo()[1].equals("scales")) {
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                                "dur=\"%fms\" attributeName=\"width\" from=\"%s\" to=\"%s\" " +
                                "fill=\"freeze\"/>\n", begin,
                        (double) transformation.getStartTick() * milli,
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli, transformation.getStart()[0],
                        transformation.getEnd()[0]));
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                                "dur=\"%fms\" attributeName=\"height\" from=\"%s\" to=\"%s\" " +
                                "fill=\"freeze\"/>\n", begin,
                        (double) (transformation.getStartTick() * milli),
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli,
                        transformation.getStart()[1], transformation.getEnd()[1]));
              }
              if (transformation.getInfo()[1].equals("moves")) {
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                                "dur=\"%fms\" attributeName=\"x\" from=\"%s\" to=\"%s\" " +
                                "fill=\"freeze\"/>\n", begin,
                        (double) transformation.getStartTick() * milli,
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli,
                        transformation.getStart()[0], transformation.getEnd()[0]));
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                                "dur=\"%fms\" attributeName=\"y\" from=\"%s\" to=\"%s\" " +
                                "fill=\"freeze\"/>\n", begin,
                        (double) transformation.getStartTick() * milli,
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli,
                        transformation.getStart()[1], transformation.getEnd()[1]));
              }
              if (transformation.getInfo()[1].equals("changes color")) {
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\"" +
                                " dur=\"%fms\" attributeName=\"fill\" from=\"rgb(%s%%,%s%%,%s%%)\""
                                + " to=\"rgb(%s%%,%s%%,%s%%) \" fill=\"freeze\"/>\n", begin,
                        (double) transformation.getStartTick() * milli,
                        (double) (transformation.getEndTick() - transformation.getStartTick()) * 1000
                                / speed,
                        transformation.getStart()[0] * 100, transformation.getStart()[1] * 100,
                        transformation.getStart()[2] * 100, transformation.getEnd()[0] * 100,
                        transformation.getEnd()[1] * 100, transformation.getEnd()[2] * 100));
              }
            }
          }
          if (looping) {
            output.append(String.format("\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" " +
                            "attributeName=\"x\" to=\"%f\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"y\" " +
                            "to=\"%f\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"width\" " +
                            "to=\"%f\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"height\" " +
                            "to=\"%f\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"fill\" " +
                            "to=\"rgb(%f%%,%f%%,%f%%)\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"opacity\" " +
                            "to=\"0\" fill=\"freeze\" />", shape.getLocation().getX(),
                    shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
                    shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
                    shape.getColor().getBlue() * 100));
          }
          output.append("\n</rect>\n");

        }
        if (shape.getShapeType().equals("oval")) {
          output.append(String.format("<ellipse id=\"%s\" cx=\"%f\" cy=\"%f\" rx=\"%f\" " +
                          "ry=\"%f\" " + "fill=\"rgb(%f%%,%f%%,%f%%)\" " +
                          "opacity=\"0\" >\n", shape.getName(), shape.getLocation().getX(),
                  shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
                  shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
                  shape.getColor().getBlue() * 100));
          if (shape.getVisible()) {
            output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                            "dur=\"%fms\" attributeName=\"opacity\" from=\"%d\" to=\"%d\" " +
                            "fill=\"freeze\"/>\n", begin,
                    (double) shape.getAppears() * milli,
                    1.0, 0, 1));
          }
          for (ITransformation transformation : transformations) {
            if (shape.getName().equals(transformation.getShapeName())) {
              if (transformation.getInfo()[1].equals("scales")) {
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                                "dur=\"%fms\" attributeName=\"cx\" from=\"%s\" to=\"%s\" " +
                                "fill=\"freeze\"/>\n", begin,
                        (double) transformation.getStartTick() * milli,
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli,
                        transformation.getStart()[0], transformation.getEnd()[0]));
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                                "dur=\"%fms\" attributeName=\"cy\" from=\"%s\" to=\"%s\" " +
                                "fill=\"freeze\"/>\n", begin,
                        (double) (transformation.getStartTick() * milli),
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli,
                        transformation.getStart()[1], transformation.getEnd()[1]));
              }
              if (transformation.getInfo()[1].equals("moves")) {
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                                "dur=\"%fms\" attributeName=\"cx\" from=\"%s\" to=\"%s\" " +
                                "fill=\"freeze\"/>\n", begin,
                        (double) transformation.getStartTick() * milli,
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli,
                        transformation.getStart()[0], transformation.getEnd()[0]));
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                                "dur=\"%fms\" attributeName=\"cy\" from=\"%s\" to=\"%s\" " +
                                "fill=\"freeze\"/>\n", begin,
                        (double) transformation.getStartTick() * milli,
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli,
                        transformation.getStart()[1], transformation.getEnd()[1]));
              }
              if (transformation.getInfo()[1].equals("changes color")) {
                output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\"" +
                                " dur=\"%fms\" attributeName=\"fill\" from=\"rgb(%s%%,%s%%,%s%%)\"" +
                                " to=\"rgb(%s%%,%s%%,%s%%)\" fill=\"freeze\"/>\n", begin,
                        (double) transformation.getStartTick() * milli,
                        (double) (transformation.getEndTick() - transformation.getStartTick())
                                * milli,
                        transformation.getStart()[0] * 100, transformation.getStart()[1] * 100,
                        transformation.getStart()[2] * 100, transformation.getEnd()[0] * 100,
                        transformation.getEnd()[1] * 100, transformation.getEnd()[2] * 100));
              }
            }
          }
          if (looping) {
            output.append(String.format("\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" " +
                            "attributeName=\"cx\" to=\"%f\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"cy\" " +
                            "to=\"%f\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"rx\" " +
                            "to=\"%f\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"ry\" " +
                            "to=\"%f\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"fill\" " +
                            "to=\"rgb(%f%%,%f%%,%f%%)\" fill=\"freeze\" />" +
                            "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"opacity\" " +
                            "to=\"0\" fill=\"freeze\" />", shape.getLocation().getX(),
                    shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
                    shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
                    shape.getColor().getBlue() * 100));
          }

          output.append("\n</ellipse>\n");

      }
    }
    output.append("</svg>");
  }



  public void betterPrintView() throws IOException {

    String begin;
    if (looping) {
      begin = "base.begin+";
    } else {
      begin = "";
    }

    double milli = 1000d / speed;

    output.append("<svg width=\"800\" height=\"800\" version=\"1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n");

    output.append("<rect>\n<animate id=\"base\" begin=\"0;base.end\" dur=\"" +
            endTick * milli + "ms\" attributeName=\"visibility\" " +
            "from=\"hide\" to=\"hide\"/>\n" + "</rect>\n");

    Map<String, String> map = new LinkedHashMap<>();

    for (IAnimShape shape : shapes) {
      String svgCode = "";
      if (shape.getShapeType().equals("rectangle")) {
        svgCode += String.format("<rect id=\"%s\" x=\"%f\" y=\"%f\" width=\"%f\" " +
                        "height=\"%f\" " + "fill=\"rgb(%f%%,%f%%,%f%%)\" " +
                        "opacity=\"0\" >\n", shape.getName(), shape.getLocation().getX(),
                shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
                shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
                shape.getColor().getBlue() * 100);
        if (shape.getVisible()) {
          svgCode += String.format("<animate attributeType=\"xml\" begin=\"%s%fms\"" +
                          " dur=\"%fms\" attributeName=\"opacity\" from=\"%d\" to=\"%d\" " +
                          "fill=\"freeze\"/>\n", begin, (double) shape.getAppears() * milli,
                  1.0, 0, 1);
        }
      }
      else if (shape.getShapeType().equals("oval")) {
        svgCode += String.format("<ellipse id=\"%s\" cx=\"%f\" cy=\"%f\" rx=\"%f\" " +
                        "ry=\"%f\" " + "fill=\"rgb(%f%%,%f%%,%f%%)\" " +
                        "opacity=\"0\" >\n", shape.getName(), shape.getLocation().getX(),
                shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
                shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
                shape.getColor().getBlue() * 100);
        if (shape.getVisible()) {
          svgCode += String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                          "dur=\"%fms\" attributeName=\"opacity\" from=\"%d\" to=\"%d\" " +
                          "fill=\"freeze\"/>\n", begin, (double) shape.getAppears() * milli,
                  1.0, 0, 1);
        }
      }
      map.put(shape.getName(), svgCode);
    }

    for (ITransformation transformation : transformations) {
      String svgCode = map.get(transformation.getShapeName());
      if (transformation.getInfo()[1].equals("scales")) {
              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                              "dur=\"%fms\" attributeName=\"width\" from=\"%s\" to=\"%s\" " +
                              "fill=\"freeze\"/>\n", begin,
                      (double) transformation.getStartTick() * milli,
                      (double) (transformation.getEndTick() - transformation.getStartTick())
                              * milli, transformation.getStart()[0],
                      transformation.getEnd()[0]));
              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                              "dur=\"%fms\" attributeName=\"height\" from=\"%s\" to=\"%s\" " +
                              "fill=\"freeze\"/>\n", begin,
                      (double) (transformation.getStartTick() * milli),
                      (double) (transformation.getEndTick() - transformation.getStartTick())
                              * milli,
                      transformation.getStart()[1], transformation.getEnd()[1]));
            }
            if (transformation.getInfo()[1].equals("moves")) {
              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                              "dur=\"%fms\" attributeName=\"x\" from=\"%s\" to=\"%s\" " +
                              "fill=\"freeze\"/>\n", begin,
                      (double) transformation.getStartTick() * milli,
                      (double) (transformation.getEndTick() - transformation.getStartTick())
                              * milli,
                      transformation.getStart()[0], transformation.getEnd()[0]));
              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
                              "dur=\"%fms\" attributeName=\"y\" from=\"%s\" to=\"%s\" " +
                              "fill=\"freeze\"/>\n", begin,
                      (double) transformation.getStartTick() * milli,
                      (double) (transformation.getEndTick() - transformation.getStartTick())
                              * milli,
                      transformation.getStart()[1], transformation.getEnd()[1]));
            }
            if (transformation.getInfo()[1].equals("changes color")) {
              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\"" +
                              " dur=\"%fms\" attributeName=\"fill\" from=\"rgb(%s%%,%s%%,%s%%)\""
                              + " to=\"rgb(%s%%,%s%%,%s%%) \" fill=\"freeze\"/>\n", begin,
                      (double) transformation.getStartTick() * milli,
                      (double) (transformation.getEndTick() - transformation.getStartTick()) * 1000
                              / speed,
                      transformation.getStart()[0] * 100, transformation.getStart()[1] * 100,
                      transformation.getStart()[2] * 100, transformation.getEnd()[0] * 100,
                      transformation.getEnd()[1] * 100, transformation.getEnd()[2] * 100));
            }

      map.put(transformation.getShapeName(), svgCode);
    }

//      //If rectangle
//      if (shape.getShapeType().equals("rectangle")) {
//        output.append(String.format("<rect id=\"%s\" x=\"%f\" y=\"%f\" width=\"%f\" " +
//                        "height=\"%f\" " + "fill=\"rgb(%f%%,%f%%,%f%%)\" " +
//                        "opacity=\"0\" >\n", shape.getName(), shape.getLocation().getX(),
//                shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
//                shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
//                shape.getColor().getBlue() * 100));
//        if (shape.getVisible()) {
//          output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\"" +
//                          " dur=\"%fms\" attributeName=\"opacity\" from=\"%d\" to=\"%d\" " +
//                          "fill=\"freeze\"/>\n", begin, (double) shape.getAppears() * milli,
//                  1.0, 0, 1));
//        }
//        for (ITransformation transformation : transformations) {
//          if (shape.getName().equals(transformation.getShapeName())) {
//            if (transformation.getInfo()[1].equals("scales")) {
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                              "dur=\"%fms\" attributeName=\"width\" from=\"%s\" to=\"%s\" " +
//                              "fill=\"freeze\"/>\n", begin,
//                      (double) transformation.getStartTick() * milli,
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli, transformation.getStart()[0],
//                      transformation.getEnd()[0]));
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                              "dur=\"%fms\" attributeName=\"height\" from=\"%s\" to=\"%s\" " +
//                              "fill=\"freeze\"/>\n", begin,
//                      (double) (transformation.getStartTick() * milli),
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli,
//                      transformation.getStart()[1], transformation.getEnd()[1]));
//            }
//            if (transformation.getInfo()[1].equals("moves")) {
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                              "dur=\"%fms\" attributeName=\"x\" from=\"%s\" to=\"%s\" " +
//                              "fill=\"freeze\"/>\n", begin,
//                      (double) transformation.getStartTick() * milli,
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli,
//                      transformation.getStart()[0], transformation.getEnd()[0]));
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                              "dur=\"%fms\" attributeName=\"y\" from=\"%s\" to=\"%s\" " +
//                              "fill=\"freeze\"/>\n", begin,
//                      (double) transformation.getStartTick() * milli,
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli,
//                      transformation.getStart()[1], transformation.getEnd()[1]));
//            }
//            if (transformation.getInfo()[1].equals("changes color")) {
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\"" +
//                              " dur=\"%fms\" attributeName=\"fill\" from=\"rgb(%s%%,%s%%,%s%%)\""
//                              + " to=\"rgb(%s%%,%s%%,%s%%) \" fill=\"freeze\"/>\n", begin,
//                      (double) transformation.getStartTick() * milli,
//                      (double) (transformation.getEndTick() - transformation.getStartTick()) * 1000
//                              / speed,
//                      transformation.getStart()[0] * 100, transformation.getStart()[1] * 100,
//                      transformation.getStart()[2] * 100, transformation.getEnd()[0] * 100,
//                      transformation.getEnd()[1] * 100, transformation.getEnd()[2] * 100));
//            }
//          }
//        }
//        if (looping) {
//          output.append(String.format("\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" " +
//                          "attributeName=\"x\" to=\"%f\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"y\" " +
//                          "to=\"%f\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"width\" " +
//                          "to=\"%f\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"height\" " +
//                          "to=\"%f\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"fill\" " +
//                          "to=\"rgb(%f%%,%f%%,%f%%)\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"opacity\" " +
//                          "to=\"0\" fill=\"freeze\" />", shape.getLocation().getX(),
//                  shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
//                  shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
//                  shape.getColor().getBlue() * 100));
//        }
//        output.append("\n</rect>\n");
//
//      }
//      //If oval
//      if (shape.getShapeType().equals("oval")) {
//        output.append(String.format("<ellipse id=\"%s\" cx=\"%f\" cy=\"%f\" rx=\"%f\" " +
//                        "ry=\"%f\" " + "fill=\"rgb(%f%%,%f%%,%f%%)\" " +
//                        "opacity=\"0\" >\n", shape.getName(), shape.getLocation().getX(),
//                shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
//                shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
//                shape.getColor().getBlue() * 100));
//        if (shape.getVisible()) {
//          output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                          "dur=\"%fms\" attributeName=\"opacity\" from=\"%d\" to=\"%d\" " +
//                          "fill=\"freeze\"/>\n", begin,
//                  (double) shape.getAppears() * milli,
//                  1.0, 0, 1));
//        }
//        for (ITransformation transformation : transformations) {
//          if (shape.getName().equals(transformation.getShapeName())) {
//            if (transformation.getInfo()[1].equals("scales")) {
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                              "dur=\"%fms\" attributeName=\"cx\" from=\"%s\" to=\"%s\" " +
//                              "fill=\"freeze\"/>\n", begin,
//                      (double) transformation.getStartTick() * milli,
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli,
//                      transformation.getStart()[0], transformation.getEnd()[0]));
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                              "dur=\"%fms\" attributeName=\"cy\" from=\"%s\" to=\"%s\" " +
//                              "fill=\"freeze\"/>\n", begin,
//                      (double) (transformation.getStartTick() * milli),
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli,
//                      transformation.getStart()[1], transformation.getEnd()[1]));
//            }
//            if (transformation.getInfo()[1].equals("moves")) {
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                              "dur=\"%fms\" attributeName=\"cx\" from=\"%s\" to=\"%s\" " +
//                              "fill=\"freeze\"/>\n", begin,
//                      (double) transformation.getStartTick() * milli,
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli,
//                      transformation.getStart()[0], transformation.getEnd()[0]));
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\" " +
//                              "dur=\"%fms\" attributeName=\"cy\" from=\"%s\" to=\"%s\" " +
//                              "fill=\"freeze\"/>\n", begin,
//                      (double) transformation.getStartTick() * milli,
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli,
//                      transformation.getStart()[1], transformation.getEnd()[1]));
//            }
//            if (transformation.getInfo()[1].equals("changes color")) {
//              output.append(String.format("<animate attributeType=\"xml\" begin=\"%s%fms\"" +
//                              " dur=\"%fms\" attributeName=\"fill\" from=\"rgb(%s%%,%s%%,%s%%)\"" +
//                              " to=\"rgb(%s%%,%s%%,%s%%)\" fill=\"freeze\"/>\n", begin,
//                      (double) transformation.getStartTick() * milli,
//                      (double) (transformation.getEndTick() - transformation.getStartTick())
//                              * milli,
//                      transformation.getStart()[0] * 100, transformation.getStart()[1] * 100,
//                      transformation.getStart()[2] * 100, transformation.getEnd()[0] * 100,
//                      transformation.getEnd()[1] * 100, transformation.getEnd()[2] * 100));
//            }
//          }
//        }
//        if (looping) {
//          output.append(String.format("\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" " +
//                          "attributeName=\"cx\" to=\"%f\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"cy\" " +
//                          "to=\"%f\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"rx\" " +
//                          "to=\"%f\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"ry\" " +
//                          "to=\"%f\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"fill\" " +
//                          "to=\"rgb(%f%%,%f%%,%f%%)\" fill=\"freeze\" />" +
//                          "\n<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"1ms\" attributeName=\"opacity\" " +
//                          "to=\"0\" fill=\"freeze\" />", shape.getLocation().getX(),
//                  shape.getLocation().getY(), shape.getPValues()[0], shape.getPValues()[1],
//                  shape.getColor().getRed() * 100, shape.getColor().getGreen() * 100,
//                  shape.getColor().getBlue() * 100));
//        }
//
//        output.append("\n</ellipse>\n");
//
//      }
//    }
    output.append("</svg>");
  }



}


