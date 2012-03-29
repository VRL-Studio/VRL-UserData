/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import basic.math.ug.equation.*;
import eu.mihosoft.vrl.types.VCanvas3D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import javax.swing.JToolTip;

/**
 * This is button class is created to have unique colored buttons in the
 * PickPlotter tool.
 *
 * @author night
 */
public class ColorButton extends ToolButton {

    public static final Color defaultColor = Color.BLUE;
    private Color color;
    private String mode;
//    private JToolTip toolTip;

    public ColorButton(VCanvas3D canvas, String mode) {
        super(canvas);
        this.mode = mode;
//        toolTip = new JToolTip();
//        color = Color.WHITE;//default value/color
        whichMode(mode);
    }

    /**
     *  Draws the characteristic symbol for the PaintButton
     * @param g2 the area where the symbol is drawn
     */
    @Override
    protected void drawIcon(Graphics2D g2) {

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = buffer.getWidth();
        int h = buffer.getHeight();

        Polygon all = new Polygon();
        all.addPoint(0, 0);
        all.addPoint(0, h);
        all.addPoint(w, h);
        all.addPoint(w, 0);

        g2.setColor(getColor());
        g2.fill(all);
    }

    private void whichMode(String mode) {

//        toolTip.setComponent(this);

        if (mode.equals(ColorButtonMode.DESELECT)) {
            color = ColorButton.defaultColor;
//            toolTip.setTipText("Select this button for deselecting.");
            setToolTipText("Select this button for deselecting.");

        } else if (mode.equals(ColorButtonMode.DIRICHLET)) {
            color = Color.GREEN;
//            toolTip.setTipText("For marking now with dirichlet mark.");
            setToolTipText("For marking now with dirichlet mark.");

        } else if (mode.equals(ColorButtonMode.NEUMANN)) {
            color = Color.YELLOW;
//            toolTip.setTipText("For marking now with neumann mark.");
            setToolTipText("For marking now with neumann mark.");

        } else {
            throw new IllegalArgumentException("You used an unsupported mode as Parameter." +
                    "Please use one mode from the class ColorButtonMode.");
        }
    }

     /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }
}
