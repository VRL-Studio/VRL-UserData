/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.equation.buttons;

import eu.mihosoft.vrl.types.VCanvas3D;
import eu.mihosoft.vrl.visual.MessageBox;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * A specialist ToolButton.
 * Used to change the mode in the PickPlotter.
 * If SelectionButton is active it is possible to pick/click some shapes.
 *
 * @author night
 */
public class SelectionButton extends ToolButton {

    public SelectionButton(VCanvas3D canvas) {
        super(canvas);
    }

    /**
     *  Draws the characteristic symbol for the SelectionButton
     * @param g2 the area where the symbol is drawn
     */
    @Override
    protected void drawIcon(Graphics2D g2) {
        float strokeWidth = 1.5f;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke stroke = new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 0, new float[]{1.5f}, 0);

        g2.setStroke(stroke);
        Color strokeColor = Color.white;

        if (style != null) {
//            strokeColor = style.getMessageBoxIconColor();
            
            strokeColor = style.getBaseValues().getColor(MessageBox.ICON_COLOR_KEY);
        }

        int margin = (int) (strokeWidth / 2);

        g2.setColor(strokeColor);
        g2.drawRect(margin, margin,
                buffer.getWidth() - margin * 2 - 1,
                buffer.getHeight() - margin * 2 - 1);
    }
}
