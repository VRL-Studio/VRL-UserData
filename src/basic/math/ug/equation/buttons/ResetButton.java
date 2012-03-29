/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import eu.mihosoft.vrl.types.VCanvas3D;
import eu.mihosoft.vrl.v3d.AppearanceGenerator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point2f;

/**
 * The ResetButton is used for undo all changes on the loaded geometry like
 * rotation, zoom and selections.
 *
 * @author night
 */
public class ResetButton extends ToolButton {

    CustomCanvas canvas;

    public ResetButton(VCanvas3D canvas) {
        super(canvas);

        if (canvas instanceof CustomCanvas) {
            this.canvas = (CustomCanvas) canvas;
        }
    }

    /**
     *  Draws the characteristic symbol for the ResetButton
     * @param g2 the area where the symbol is drawn
     */
    @Override
    protected void drawIcon(Graphics2D g2) {

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        Color strokeColor = Color.white;
//
//        if (style != null) {
//            strokeColor = style.getMessageBoxIconColor();
//        }
//        g2.setColor(strokeColor);

        float w = buffer.getWidth();
        float h = buffer.getHeight();

        Polygon line01 = new Polygon();
        Polygon line02 = new Polygon();

        Point2f p1 = new Point2f(w / 8, h / 4);
        Point2f p2 = new Point2f(w / 4, h / 8);
        Point2f p3 = new Point2f(w * 7 / 8, h * 7 / 8);
        Point2f p4 = new Point2f(w * 3 / 4, h * 3 / 4);

        Point2f p5 = new Point2f(w / 8, h * 7 / 8);
        Point2f p6 = new Point2f(w / 8, h * 7 / 8);
        Point2f p7 = new Point2f(w * 3 / 4, h / 8);
        Point2f p8 = new Point2f(w * 7 / 8, h / 4);


//        line01.addPoint((int) p1.getX(), (int) p1.getY());
//        line01.addPoint((int) p2.getX(), (int) p2.getY());
//        line01.addPoint((int) p3.getX(), (int) p3.getY());
//        line01.addPoint((int) p4.getX(), (int) p4.getY());
//
//        line02.addPoint((int) p5.getX(), (int) p5.getY());
//        line02.addPoint((int) p6.getX(), (int) p6.getY());
//        line02.addPoint((int) p7.getX(), (int) p7.getY());
//        line02.addPoint((int) p8.getX(), (int) p8.getY());
        
        line01.addPoint((int) p1.x, (int) p1.y);
        line01.addPoint((int) p2.x, (int) p2.y);
        line01.addPoint((int) p3.x, (int) p3.y);
        line01.addPoint((int) p4.x, (int) p4.y);

        line02.addPoint((int) p5.x, (int) p5.y);
        line02.addPoint((int) p6.x, (int) p6.y);
        line02.addPoint((int) p7.x, (int) p7.y);
        line02.addPoint((int) p8.x, (int) p8.y);

        g2.setColor(Color.RED);

        g2.fill(line01);
        g2.fill(line02);
    }

    /**
     * Set removes all transformation and deletes the selected shapes from
     * collection of selected shapes.
     *
     * @param e MouseEvent unused
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        canvas.getUniverse().getRootGroup().setTransform(new Transform3D());
        canvas.getUniverse().getTransGroup().setTransform(new Transform3D());

        HashSet<Shape3D> shape3DSet = canvas.getUniverse().getKeyMouseListener().getShape3DSet();
        HashSet<Shape3D> delList = new HashSet<Shape3D>(shape3DSet);

        AppearanceGenerator generator = new AppearanceGenerator();

        for (Shape3D s : delList) {

            s.setAppearance(generator.getColoredAppearance(ColorButton.defaultColor, 0.f));
            shape3DSet.remove(s);
        }

    }

    /**
     * Calls select().
     *
     * @param e MouseEvent unused
     */
    @Override
    public void mousePressed(MouseEvent e) {
        select();
    }

    /**
     * Calls unselect().
     *
     * @param e MouseEvent unused
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        unselect();
    }
}
