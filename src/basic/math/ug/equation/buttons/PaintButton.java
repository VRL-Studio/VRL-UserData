/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.ug.equation.buttons;

import eu.mihosoft.vrl.types.VCanvas3D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import javax.vecmath.Point2f;

/**
 * PaintButton is used to select shapes during painting over them.
 * This means keep the mouse button pressed and move the mouse. All
 * shapes under the mouse would be select.
 *
 * @author night
 */
public class PaintButton extends ToolButton{

    public PaintButton(VCanvas3D canvas) {
        super(canvas);
    }

    /**
     *  Draws the characteristic symbol for the PaintButton
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

//      set the points of the symbol of the button
//      and draw the symbol by connecting the points

        Point2f p1 = new Point2f(w     /8.f     , h* 7.f/8.f);
        Point2f p2 = new Point2f(w* 3.f/16.f    , h* 11.f/16.f);
        Point2f p3 = new Point2f(w* 5.f/16.f    , h* 5.f/8.f);
        Point2f p4 = new Point2f(w* 3.f/8.f     , h* 11.f/16.f);
        Point2f p5 = new Point2f(w* 5.f/16.f    , h* 13.f/16.f);
        Point2f p6 = new Point2f(w* 3.f/4.f     , h /8.f);
        Point2f p7 = new Point2f(w* 7.f/8.f     , h /8.f);
        Point2f p8 = new Point2f(w* 7.f/8.f     , h /4.f);


        Polygon part1 = new Polygon();
//        part1.addPoint((int) (p1.getX())  ,(int) (p1.getY()) );
//        part1.addPoint((int) (p2.getX())  ,(int) (p2.getY()));
//        part1.addPoint((int) (p3.getX())  ,(int) (p3.getY()));
//        part1.addPoint((int) (p4.getX())  ,(int) (p4.getY()));
//        part1.addPoint((int) (p5.getX())  ,(int) (p5.getY()));
        
        part1.addPoint((int) (p1.x)  ,(int) (p1.y) );
        part1.addPoint((int) (p2.x)  ,(int) (p2.y) );
        part1.addPoint((int) (p3.x)  ,(int) (p3.y) );
        part1.addPoint((int) (p4.x)  ,(int) (p4.y) );
        part1.addPoint((int) (p5.x)  ,(int) (p5.y) );

        g2.setColor(Color.RED);
        g2.fill(part1);

        Polygon part2 = new Polygon();
//        part2.addPoint((int) (p3.getX())  ,(int) (p3.getY()) );
//        part2.addPoint((int) (p6.getX())  ,(int) (p6.getY()));
//        part2.addPoint((int) (p7.getX())  ,(int) (p7.getY()));
//        part2.addPoint((int) (p8.getX())  ,(int) (p8.getY()));
//        part2.addPoint((int) (p4.getX())  ,(int) (p4.getY()));
        
        part2.addPoint((int) (p3.x)  ,(int) (p3.y) );
        part2.addPoint((int) (p6.x)  ,(int) (p6.y) );
        part2.addPoint((int) (p7.x)  ,(int) (p7.y) );
        part2.addPoint((int) (p8.x)  ,(int) (p8.y) );
        part2.addPoint((int) (p4.x)  ,(int) (p4.y) );

        g2.setColor(Color.magenta);
        g2.fill(part2);
    }

}
