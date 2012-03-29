/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import basic.math.ug.equation.helpers.PickUniverseCreator;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.types.VCanvas3D;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.MessageBox;
import eu.mihosoft.vrl.visual.Style;
import eu.mihosoft.vrl.visual.TransparentPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.vecmath.Point3d;

/**
 * CustomCanvas is used canvas in PickPlotter and contains buttons which
 * allows the user to change the mode in the PickPlotter, e.g. from rotation
 * mode to selection/picking mode and backwards.
 *
 * @author night
 */
public class CustomCanvas extends VCanvas3D {

    private PickUniverseCreator universe;
    private Dimension toolBtnSize = new Dimension(50, 50);
    private Dimension colorBtnSize = new Dimension(25, 25);
    private ToolButton selectionButton;
    private ToolButton transformationButton;
    private ToolButton paintButton;
//    private ToolButton eraseButton;
//    private ToolButton dirichletButton;
//    private ToolButton neumannButton;
//    private ToolButton colorButton;
    private ToolButton skriptButton;
    private ToolButton resetButton;
    private SelectionButtonGroup selectionButtonGroup;
    private ColorButtonGroup colorButtonGroup;
    private Rectangle2D selectionRectangle;
    private boolean paintSelectionRectangle = false;
    private Component leadingSpace;

    /**
     * Creates the complete scene in canvas.
     *
     * @param type the type representation that uses this canvas
     * @param universe the UniverseCreator which is used to created the scene
     */
    public CustomCanvas(TypeRepresentationBase type, PickUniverseCreator universe) {
        super(type);
        this.universe = universe;

        selectionButtonGroup = new SelectionButtonGroup(universe);

        leadingSpace = new TransparentPanel();

        leadingSpace.setPreferredSize(new Dimension(0, 5));
        leadingSpace.setMinimumSize(new Dimension(0, 5));
        leadingSpace.setMaximumSize(new Dimension(0, 5));
        leadingSpace.setSize(new Dimension(0, 5));

        //setze abstand zum oberen Rand
        getToolPane().add(leadingSpace);

        transformationButton = new TransformationButton(this);
        addButton(transformationButton, toolBtnSize);

        selectionButton = new SelectionButton(this);
        addButton(selectionButton, toolBtnSize);

        paintButton = new PaintButton(this);
        addButton(paintButton, toolBtnSize);

        skriptButton = new SkriptButton(this);
        addButton(skriptButton, toolBtnSize);

        resetButton = new ResetButton(this);
        addButton(resetButton, toolBtnSize);


        selectionButtonGroup.add(selectionButton, ToolButtonMode.SELECTION);
        selectionButtonGroup.add(transformationButton, ToolButtonMode.TRANSFORMATION);
        selectionButtonGroup.add(paintButton, ToolButtonMode.PAINT);
//        selectionButtonGroup.add(skriptButton, ToolButtonMode.SKRIPT);
//        selectionButtonGroup.add(resetButton, ToolButtonMode.RESET);

        selectionButtonGroup.setMode(ToolButtonMode.TRANSFORMATION);

        //-----------------------------------------

        colorButtonGroup = new ColorButtonGroup();
        //setze abstand zum oberen Rand
        getToolPane().add(Box.createVerticalStrut(5));

//        eraseButton = new EraseButton(this);
//        addButton(eraseButton);
//
//        dirichletButton = new DirichletButton(this);
//        addButton(dirichletButton);
//
//        neumannButton = new NeumannButton(this);
//        addButton(neumannButton);
//
//        appearanceButtonGroup.add(eraseButton);
//        appearanceButtonGroup.add(dirichletButton);
//        appearanceButtonGroup.add(neumannButton);

//        for (int i = 0; i < 5; i++) {
//            String mode = "color" + i;
//            Color c = new Color(i*30, i*30, i*30);
//            ColorButton colorButton = new ColorButton(this, mode);
//            addButton(colorButton, colorBtnSize);
//            colorButtonGroup.add(colorButton, colorButton.getMode());
//
//            colorButton.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if (e.getActionCommand().equals("clicked")) {
//                        System.out.println("Mode: " + colorButtonGroup.getMode());
//                    }
//                }
//            });
//        }
//
//        colorButtonGroup.setMode("color0");


        ColorButton deselectButton = new ColorButton(this, ColorButtonMode.DESELECT);
        addButton(deselectButton, colorBtnSize);

        ColorButton dirichletButton = new ColorButton(this, ColorButtonMode.DIRICHLET);
        addButton(dirichletButton, colorBtnSize);

        ColorButton neumannButton = new ColorButton(this, ColorButtonMode.NEUMANN);
        addButton(neumannButton, colorBtnSize);

        colorButtonGroup.add(neumannButton, neumannButton.getMode());
        colorButtonGroup.add(dirichletButton, dirichletButton.getMode());
        colorButtonGroup.add(deselectButton, deselectButton.getMode());

        colorButtonGroup.setMode(ColorButtonMode.DIRICHLET);

    }

    //erzeuge für jeden Button eine ButtonBox in der der Button und der Abstand
    //zur Seite enthalten sind
    /**
     * Adds a button to the canvas in a orgenized way,
     * that contains distance to the border of the canvas.
     * @param button that should be added
     * @param buttonSize the size of the button
     */
    public void addButton(ToolButton button, Dimension buttonSize) {
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setPreferredSize(buttonSize);

        Box btnBox = new Box(BoxLayout.X_AXIS);
        btnBox.add(Box.createHorizontalStrut(5));
        btnBox.add(button);
        btnBox.setMaximumSize(new Dimension(Short.MAX_VALUE, buttonSize.height));
        getToolPane().add(btnBox);

    }

    /**
     * In this methode is the selection rectangle drawn.
     * The rectangle is used for multiple selction of shapes in PickPlotter.
     *
     * @param g2 the area where the rectangle is drawn
     */

    @Override
    protected void postRender(Graphics2D g2) {
        super.postRender(g2);

        if (paintSelectionRectangle) {
            Style style = getTypeRepresentation().getMainCanvas().getStyle();

            //randdicke des rechtecks festlegen
            g2.setStroke(new BasicStroke(1.f));

            //die farbe des zu zeichnendes rechtecks von einer Componente geholt
            Color baseColor = style.getBaseValues().getColor(MessageBox.ICON_COLOR_KEY);
            //und eine kopie der Farbe mit transparents versehen
            Color color = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 90);

            g2.setPaint(color);
            g2.fill(getSelectionRectangle());

            g2.setColor(baseColor);
            g2.draw(getSelectionRectangle());
        }
    }

    /**
     * the "orientation" of the two point where checked
     * ( this means is p2 higher then p1 etc. )
     * and the position of the rectangle is set.
     * @param p1 the beginning point of the rectangle
     * @param p2 the end point of the rectangle
     */
    public void setRectangle(Point3d p1, Point3d p2) {

//        double w = p2.getX() - p1.getX();
//        double h = p2.getY() - p1.getY();
        
        double w = p2.x - p1.x;
        double h = p2.y - p1.y;

        double wAbs = Math.abs(w);
        double hAbs = Math.abs(h);
        /*
         *             |
         *           3 | 4
         *       -------------
         *           2 | 1
         *             |
         */
        if (w >= 0) {
            if (h >= 0) {
                //Quadrant 1
//                selectionRectangle = new Rectangle2D.Double(p1.getX(), p1.getY(), wAbs, hAbs);
                selectionRectangle = new Rectangle2D.Double(p1.x, p1.y, wAbs, hAbs);
                
            } else {
                //Quadrant 4
//                selectionRectangle = new Rectangle2D.Double(p1.getX(), p1.getY() - hAbs, wAbs, hAbs);
                selectionRectangle = new Rectangle2D.Double(p1.x, p1.y - hAbs, wAbs, hAbs);
                
            }

        } else {
            if (h >= 0) {
                //Quadrant 2
//                selectionRectangle = new Rectangle2D.Double(p1.getX() - wAbs, p1.getY(), wAbs, hAbs);
                selectionRectangle = new Rectangle2D.Double(p1.x - wAbs, p1.y, wAbs, hAbs);
                
            } else {
                //Quadrant 3
//                selectionRectangle = new Rectangle2D.Double(p1.getX() - wAbs, p1.getY() - hAbs, wAbs, hAbs);
                selectionRectangle = new Rectangle2D.Double(p1.x - wAbs, p1.y - hAbs, wAbs, hAbs);
            }
        }
    }

    /**
     * sets the paintSelectionRectangle to false,
     * so the retangle wouldn´t be drawn.
     */
    public void disableSelectionRectangle() {
        paintSelectionRectangle = false;
    }

    /**
     * allows the selction rectangle to be drawn.
     */
    public void enableSelectionRectangle() {
        paintSelectionRectangle = true;
    }

    public boolean isSelectionRectangleEnabled() {
        return paintSelectionRectangle;
    }

    /**
     * @return the selectionRectangle
     */
    public Rectangle2D getSelectionRectangle() {
        return selectionRectangle;
    }

    /**
     * @return the selectionButtonGroup
     */
    public SelectionButtonGroup getSelectionButtonGroup() {
        return selectionButtonGroup;
    }

    /**
     * @return the selectionButton
     */
    public ToolButton getSelectionButton() {
        return selectionButton;
    }

    /**
     * @return the rotationButton
     */
    public ToolButton getTransformationButton() {
        return transformationButton;
    }

    /**
     * @return the paintButton
     */
    public ToolButton getPaintButton() {
        return paintButton;
    }

    /**
     * @return the universe
     */
    public PickUniverseCreator getUniverse() {
        return universe;
    }

    /**
     * @return the colorButtonGroup
     */
    public ColorButtonGroup getColorButtonGroup() {
        return colorButtonGroup;
    }

    /**
     * @return the skriptButton
     */
    public SkriptButton getSkriptButton() {
        return (SkriptButton) skriptButton;
    }

    public void setFullScreenMode(Canvas mainCanvas, boolean state) {

        int space = 50;

        if (mainCanvas != null) {
            space = (int) mainCanvas.getEffectPane().getFullScreenCloseIcon().
                    getSize().getHeight();
        }

        if (state) {
            leadingSpace.setSize(0, space);
            leadingSpace.setPreferredSize(new Dimension(0, space));
            leadingSpace.setMinimumSize(new Dimension(0, space));
            leadingSpace.setMaximumSize(new Dimension(0, space));

            System.err.println("FULLSCREEN: " + space);
        } else {
            leadingSpace.setSize(0, 5);
            leadingSpace.setPreferredSize(new Dimension(0, 5));
            leadingSpace.setMinimumSize(new Dimension(0, 5));
            leadingSpace.setMaximumSize(new Dimension(0, 5));
        }
        
        revalidate();
    }
}
