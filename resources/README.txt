Changes from HW6 to HW7:

INTERFACES:

    AnimationComponentInterface: Added a copy method. Added an equals method. Added methods to get the
    initial and final values of the component.

    AnimationModelInterface: Added the ability to get the ShapeNameToAnimationMap. Removed the method to
    get the svg animation text from within the model (implementation moved to SVG view proper).

    ModelInsulatorInterface: Added new method to get internal model ShapeNameToAnimation map.

ABSTRACTS:
    ShapeAbstract: Fixed an issue where equality would not be based on initial parameters, allowing the
    same shape to be considered "different" if an animation had modified it. Fixed an issue where hashmap
    would not consider the initial parameter map.

CONCRETE CLASSES:

    All AnimationComponents classes: implemented the copy method. Implemented the equals / hash methods.
    Implemented new getter methods.

    AnimationModelText: fixed issue where get methods did not return full deep copy and new arrays instead
    contained the same references. Implemented getShapeNameToAnimationMap with the same new deep copy logic
    as the rest of the get methods.Added a function to calculate total running time for the animation.
     Made addAnimationComponent method public. Added new method to add shape by the shape object passed in.
     Removed implementation of getSvg to comply with new interface.

    ControllerBasic: Updated to use new ControllerAbstract.

    EasyAnimator: Updated to use new viewFactoryWithHybrid factory type.

    ModelInsulator: Implemented method to get internal model ShapeNameToAnimationMap.

    Oval: fixed Copy method not returning proper map of initial values

    Rectangle: fixed Copy method not returning proper map of initial values

    SVG View: Created new constructor to allow SVG view to work without a model (still needs maps and lists but those
    can now be provided through the constructor without the need for a full fledge model). Implementation for getting
    SVG text moved from model into the view itself to separate the two better.

    ViewFactory: Updated to work with new ViewFactoryInterface.

    ViewTypes: Added new enum, HYBRID.

    VisualView: Updated view to work with new VisualViewTypeAbstract class. Updated it to use a timer rather than
        a thread.sleep method.


NEW IN HOMEWORK 7:

ABSTRACT CLASSES:

    VisualViewTypeAbstract: This takes care of a lot of the common elements
    between both the standardVisualView and the new Hybrid visual view, including the drawing panel, the map
    and list re-organization necessitated by the model copies and some basic construction and variables.

    ControllerAbstract: This takes care of a lot of the common elements between the
    basic controller and the controller that can take a hybrid view.

INTERFACES:

    HybridViewInterface: An extended version of the ViewInterface with more functionality related to interactivity.

    ViewFactoryInterface: With the hybrid view came the need for a new ViewFactory so we made an interface that
        allows both to be used by controllers.

CONCRETE CLASSES:

    ControllerWithHybrid: New extension of ControllerAbstract, contains functionality that allows it to interact
        with a HybridView but still can use old view types.

    TestControllerHybrid: A stripped down version of ControllerWithHybrid for use in testing the action and
        item listeners.

    HybridView: The new View type. Provides buttons, checkboxes and so forth that allow the user to control
        the playback, speed and what shapes are displayed in the animation. Also allows for the animation to
        be outputted to a designated SVG file.

    TestHybridView: A stripped down version of the HybridView for use in testing whether the action and
         item listeners of the controller properly reference view functions.

    ViewFactoryWithHybrid: A new implementation of the ViewFactoryInterface that allows for the creation of Hybrid
        views as well as all previous view types.


BASIC DESIGN:

The new design is fairly straight forward: If the controller is dealing with a hybrid view, it allows access to
key, action and item listeners that control the view in an interactive way. If it is any other view, it simply calls
the standard view run() command.

The view itself is a conglomerate of buttons, checkboxes, textfields and the like that manipulate internal parameters
like ticks per second, output location, and an internal timer that controls the drawing of the animation. This timer
can be stopped, started, and restarted based on the playback buttons.

Shape selection is done based on a checkbox panel that while not terribly pretty, works. Unselecting a shape will add it
to a black list of shapes that will not be animated during each tick. Conversely, selecting a shape will remove it from
the black list. This can be done while the animation is playing.

Exporting an SVG will create a new, temporary SVG view within the hybrid view using the hybrid view's lists / maps of animations,
shapes, timing maps and so forth as well as the text currently in the export name field. Note that the actual list
of shapes handed to this internal view will not include any black listed shapes.


DESIGN DECISIONS:

The "Speed Up" and "Speed Down" buttons will only modify the speed by 5 ticks per second. One tick seemed a bit too
slow for change. There is a top and bottom limit to speed, 100 and 1 ticks per second respectively. If increasing
the speed will result in overstepping either one of these boundaries, it will be capped.

The "Restart" button will automatically play the animation once it's reset. This may take a while depending on the
size of the shape list so give it a bit (big bang specifically).

User can select or deselect shapes from the checkboxes on the Shape panel. When export SVG is called, the animation list and shape
list is passed into SVGView to create a modified copy of current model. Both selection of shapes and exporting can be
done while the animation is running and will changes will be immediate.

